package frc.robot.framework.robot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.server.PathPlannerServer;

import frc.robot.framework.controller.*;
import frc.robot.framework.util.Reflection;
import frc.robot.framework.util.ShuffleboardFramework;
import frc.robot.framework.util.XMLUtil;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * [In] is a class containing static methods for interfacing with all inputs to
 * the robot. This includes (but is not limited to) controllers and sensors.
 * 
 * @note Currently encoders are paired with motors in [Out] instead of being
 *       handled by [In] like they intuitively should be. May want to move
 *       encoder handling to [In] and have the motors reference using getters.
 */

public class RobotInit {
    private static Map<String, ControllerWrapper> controllers = new HashMap<>();
    private static Map<String, SubsystemBase> subsystems = new HashMap<>();
    private static ShuffleboardFramework shuffleboard;
    private static SendableChooser<CommandBase> autonChooser = new SendableChooser<>();

    public static void Init() {
        File[] allConfigFiles = XMLUtil.listOfFiles(Filesystem.getDeployDirectory()).toArray(File[]::new);

        // Document doc = XMLUtil.Parse(XMLUtil.merger("controller",allConfigFiles));
        Document doc = XMLUtil.mergeNew(allConfigFiles);
        Element root = doc.getDocumentElement();
        if (root == null) {
            System.out.println("Could not find any controller!");
        }

        // this has to be first
        shuffleboard = new ShuffleboardFramework(root);

        NodeList subsystemNodeList = root.getElementsByTagName("subsystem");
        initSubsystems(subsystemNodeList);

        NodeList controllerNodeList = root.getElementsByTagName("controller");
        initControllers(controllerNodeList);

        NodeList autNodeList = root.getElementsByTagName("auton");
        initAutons(autNodeList);

        PathPlannerServer.startServer(5811);
    }

    private static void initAutons(NodeList autonList) {
        ArrayList<String> autonNames = new ArrayList<>();

        // for each auton
        boolean onFirstAuton = true;
        for (int i = 0; i < autonList.getLength(); i++) {
            Node autonNode = autonList.item(i);
            if (autonNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            Element autonElement = (Element) autonNode;
            String autonName = autonElement.getAttribute("id");
            autonNames.add(autonName);

            // find the start
            NodeList autonSubNodeList = autonElement.getChildNodes();
            Node autonSubNode = null;
            Element autonSubElement = null;

            // find linked item
            for (int j = 0; j < autonSubNodeList.getLength(); j++) {
                autonSubNode = autonSubNodeList.item(j);
                if (autonSubNode.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                autonSubElement = (Element) autonSubNode;
                if (autonElement.getAttribute("nextstep").equals(autonSubElement.getAttribute("id"))) {
                    break;
                }
                autonSubElement = null;
            }

            if (autonSubElement == null) {
                System.out.println("RobotInit:initAutons - could not find the start of auton for " + autonName
                        + " linking to " + autonElement.getAttribute("nextstep"));
                continue;
            }

            CommandBase tempAutonCommand = buildCommandNodeListHelper(autonSubElement);
            if (tempAutonCommand == null) {
                System.out.println("RobotInit:initAutons - could not parse auton '" + autonName + "'");
            } else if (onFirstAuton) {
                autonChooser.setDefaultOption(autonName, tempAutonCommand);
                onFirstAuton = false;
            } else {
                autonChooser.addOption(autonName, tempAutonCommand);
            }
        }

        ShuffleboardFramework.addSendableToMainWindow("AutonCommand", autonChooser, BuiltInWidgets.kComboBoxChooser);

    }

    private static CommandBase buildCommandNodeListHelper(Element element) {
        HashMap<String, CommandBase> commandMap = new HashMap<>();
        HashMap<String, Element> elementMap = new HashMap<>();

        NodeList nodeList = element.getChildNodes();

        // go through children, build them into commands, unordered
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node stepNode = nodeList.item(i);
            if (stepNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element stepElement = (Element) stepNode;
            String stepId = stepElement.getAttribute("id");
            elementMap.put(stepId, stepElement);

            CommandBase subCommand = buildCommandNodeListHelper(stepElement);
            commandMap.put(stepId, subCommand);
        }

        CommandBase commandToReturn = buildCommandNodeHelper(element, commandMap, elementMap);

        return commandToReturn;
    }

    // This turns an element and a processed list of subcommands into a command.
    // Does not traverse anything
    private static CommandBase buildCommandNodeHelper(Element element, HashMap<String, CommandBase> commandMap,
            HashMap<String, Element> elementMap) {
        CommandBase[] commandArray = commandMap.values().toArray(new CommandBase[commandMap.size()]);

        String stepType = element.getTagName().toLowerCase();
        CommandBase myCommand = null;
        switch (stepType) {
            case "sequentialcommandgroup":
                // sort the child nodes
                CommandBase[] sortedArray = new CommandBase[commandMap.size()];
                String nextElementId = element.getAttribute("firststep");
                ;
                Element currentElement = elementMap.get(nextElementId);
                int index = 0;
                while (currentElement != null) {
                    CommandBase commandToAdd = commandMap.get(nextElementId);
                    try {
                        sortedArray[index] = commandToAdd;
                    } catch (Exception e) {
                        throw new UnsupportedOperationException(
                                "Auton - SequentialCommandGroup - id:" + element.getAttribute("id") + " - Expecting "
                                        + commandMap.size() + " elements, found at least " + (commandMap.size() + 1));
                    }
                    nextElementId = elementMap.get(nextElementId).getAttribute("nextstep");
                    currentElement = elementMap.get(nextElementId);
                    index++;
                }
                if (index != commandMap.size()) {
                    System.out.println(
                            "RobotInit:buildCommandNodeHelper - auton sequentialcommandgroup - items do not form complete list. Could not find element #"
                                    + index + " - " + nextElementId);
                    return null;
                }

                myCommand = new SequentialCommandGroup(sortedArray);
                break;
            case "parallelcommandgroup":
                myCommand = new ParallelCommandGroup(commandArray);
                break;
            case "parallelracegroup":
                myCommand = new ParallelRaceGroup(commandArray);
                break;
            case "paralleldeadlinegroup":
                String deadlineCommandName = element.getAttribute("deadlineCommand");
                Class<?> deadlineCommandClass = Reflection.GetAllCommands().get(deadlineCommandName);
                CommandBase deadlineCommand = (CommandBase) Reflection.CreateObjectFromXML(deadlineCommandClass,
                        element);
                myCommand = new ParallelDeadlineGroup(deadlineCommand, commandArray);
                break;
            case "command":
                String myCommandName = element.getAttribute("type");
                Class<?> myCommandClass = Reflection.GetAllCommands().get(myCommandName);
                if (myCommandClass == null) {
                    System.out.println("Can not find command: " + myCommandName);
                }
                myCommand = (CommandBase) Reflection.CreateObjectFromXML(myCommandClass, element);
                // myCommand = new TestCommand2(element);
                break;
            default:
                System.out.println("Robotinit:buildCommandNodeHelper: unknown command type: " + stepType);
                return null;
        }
        return myCommand;
    }

    private static void initSubsystems(NodeList subsystemNodeList) {
        subsystems = new HashMap<>();
        HashMap<String, Class<?>> subsystemClasses = Reflection.GetAllSubSystems();
        for (int i = 0; i < subsystemNodeList.getLength(); i++) {
            Node currentChild = subsystemNodeList.item(i);
            if (currentChild.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element childElement = (Element) currentChild;
            if (childElement.getTagName().equals("subsystem")) {
                String subsystemType = currentChild.getAttributes().getNamedItem("type").getNodeValue();
                if (subsystemClasses.containsKey(subsystemType)) {
                    SubsystemBase temp = (SubsystemBase) frc.robot.framework.util.Reflection
                            .CreateObjectFromXML(subsystemClasses.get(subsystemType), childElement);
                    subsystems.put(childElement.getAttribute("id"), temp);
                    ShuffleboardFramework.addSendableToMainWindow(childElement.getAttribute("id"), (Sendable) temp);
                } else {
                    System.out.println(
                            "RobotInit:initSubsystems - could not find java subsystem for " + subsystemType);
                }
            }
        }
    }

    private static void initControllers(NodeList controllerList) {
        for (int i = 0; i < controllerList.getLength(); i++) {
            Node controllerNode = controllerList.item(i);
            if (controllerNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element controllerElement = (Element) controllerNode;
            if (controllerElement.getAttribute("type").equals("LOGITECH")) {
                Integer port = Integer.parseInt(controllerElement.getAttribute("port"));
                String id = controllerElement.getAttribute("id");
                controllers.put(id, new ControllerWrapper(new LogitechGamepad(port), controllerElement));
            } else if (controllerElement.getAttribute("type").equals("XBOX")) {
                Integer port = Integer.parseInt(controllerElement.getAttribute("port"));
                String id = controllerElement.getAttribute("id");
                controllers.put(id, new ControllerWrapper(new XboxGamepad(port), controllerElement));
            } else if (controllerElement.getAttribute("type").equals("PS")) {
                Integer port = Integer.parseInt(controllerElement.getAttribute("port"));
                String id = controllerElement.getAttribute("id");
                controllers.put(id, new ControllerWrapper(new PSGamepad(port), controllerElement));
            } else {
                System.out.println("Unknown controller type: " + controllerElement.getAttribute("type"));
                continue;
            }
        }
    }

    /**
     * [getButton], [getAxis], [getAttribute] return information about the given
     * control interface.
     */

    /**
     * [getButton] returns the value of requested button
     * 
     * @param function     the name of the function (ie "A" or "B")
     * @param controllerID the name of the controller (ie "OPERATOR")
     * @return information about the requested control interface.
     */

    public static boolean getButton(String function, String controllerID) {
        ControllerWrapper requestedController = controllers.get(controllerID);
        return requestedController.getButton(function);
    }

    /**
     * [getAxis] returns the value of the requested axis
     * 
     * @param function     the name of the function (ie "LEFT_JOYSTICK_X"")
     * @param controllerID the name of the controller (ie "OPERATOR")
     * @return information about the requested control interface.
     */
    public static double getAxis(String function, String controllerID) {
        ControllerWrapper requestedController = controllers.get(controllerID);
        return requestedController.getAxis(function);
    }

    /**
     * [getAttribute] returns the value of the XML attributed named [name]
     * 
     * @param function     the name of the function (ie "TURN" or "EXTEND_ARM")
     * @param controllerID the name of the controller (ie "OPERATOR")
     * @return information about the requested control interface.
     */
    public static String getAttribute(String name, String controllerID) {
        ControllerWrapper requestedController = controllers.get(controllerID);
        return requestedController.getAttribute(name);
    }

    public static SubsystemBase GetSubsystem(String subsystemID) {
        return subsystems.get(subsystemID);
    }

    public static CommandBase GetAuton() {
        return autonChooser.getSelected();
    }

}