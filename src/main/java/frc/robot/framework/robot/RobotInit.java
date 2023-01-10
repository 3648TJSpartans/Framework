package frc.robot.framework.robot;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import frc.robot.commands.TestCommand2;
import frc.robot.framework.controller.*;
import frc.robot.framework.util.Reflection;
import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.framework.util.XMLUtil;
import frc.robot.framework.util.ShuffleboardHandler.ShuffleboardBase;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    private static Map<String, CommandBase> autons = new HashMap<>();
    private static Map<String, SubsystemBase> subsystems = new HashMap<>();
    private static ShuffleboardHandler shuffleboard;
    //private static Map<String, AutonWrapper> autons = new HashMap<>();
    /**
     * [Init] initializes [In] using an XML file detailing the control scheme.
     * 
     * @param thing path to the controls file (relative to the deploy
     *                    directory).
     * 
     * @note the current control file setup requires operator and driver controls to
     *       be specified in the same file. This is not ideal for mixing / matching
     *       driver + operator pairs. Could add an additional path to specify
     *       operator vs driver controls.
     * 
     * @note sensor input is not currently implemented. I was conflicted on how to
     *       implement this, so i'll detail the options as I see them and let you
     *       pick:
     *       1) Make a separate file that only includes sensors, and parse that with
     *       [In]. This is the simplest option, but loses the benefits associated
     *       with using XML in the first place (allowing text based representation
     *       of the robot structure).
     *       2) Parse the full configuration file. I think this is the better
     *       option, but comes with additional problems. First, it clutters
     *       the configuration file (do we care?). Second, how do we deal with
     *       encapsulation? Do we associate sensors to certain subsystems (allows
     *       for name overlaps + how controls and outputs are currently done) or do
     *       we make all sensors global scope (easier for auto)?
     */

    public static void Init() {
        // TODO find all xml files in deploy recurisvely and merge them into big xml.
        File[] allConfigFiles= XMLUtil.listOfFiles(Filesystem.getDeployDirectory()).toArray(File[]::new); 
        
        //Document doc = XMLUtil.Parse(XMLUtil.merger("controller",allConfigFiles));
        Document doc = XMLUtil.mergeNew(allConfigFiles);
        Element root = doc.getDocumentElement();
        if (root==null){
            System.out.println("Could not find any controller!");
        }

        shuffleboard = new ShuffleboardHandler(root);

        NodeList subsystemNodeList = root.getElementsByTagName("subsystem");
        initSubsystems(subsystemNodeList);
        
        NodeList controllerNodeList = root.getElementsByTagName("controller");
        initControllers(controllerNodeList);

        NodeList autNodeList = root.getElementsByTagName("auton");
        initAutons(autNodeList);
    }


    private static void initAutons(NodeList autonList){
        ArrayList<String> autonNames = new ArrayList<>();

        //for each auton
        for (int i = 0; i < autonList.getLength(); i++) {
            Node autonNode = autonList.item(i);
            if (autonNode.getNodeType() != Node.ELEMENT_NODE)  { continue;}

            Element autonElement = (Element) autonNode;
            String autonName=autonElement.getAttribute("id");
            autonNames.add(autonName);

            //find the start
            NodeList autonSubNodeList = autonElement.getChildNodes();
            Node autonSubNode=null;
            Element autonSubElement=null;

            for (int j=0; j<autonSubNodeList.getLength(); j++){
                autonSubNode = autonSubNodeList.item(j);
                if (autonSubNode.getNodeType() != Node.ELEMENT_NODE)  { continue;}
                autonSubElement = (Element)autonSubNode;
                if ( autonElement.getAttribute("nextstep").equals(autonSubElement.getAttribute("id"))){
                    break;
                }
                autonSubElement=null;
            }
            
            if (autonSubElement == null){
                System.out.println("RobotInit:initAutons - could not find the start of auton for "+autonName+" linking to "+autonElement.getAttribute("nextstep"));
            }

            CommandBase tempAutonCommand=buildCommandNodeListHelper(autonSubElement);
            autons.put(autonName, tempAutonCommand);            
        }

        //TODO put auton selector on shuffleboard
        // shuffleboard.Handlers.put("Robot", new ShuffleboardBase("Robot"));
        // var tab = Shuffleboard.getTab("Robot");
        // SimpleWidget sysWidget = tab.add("Auton", "").withWidget(WidgetType)
        //SmartDashboard.putStringArray("Auton", autonNames.toArray(String[]::new));
    }

    public static CommandBase buildCommandNodeListHelper(Element element){
        HashMap<String, CommandBase> commandMap = new HashMap<>();
        HashMap<String, Element> elementMap = new HashMap<>();

        NodeList nodeList = element.getChildNodes();

        //go through children, build them into commands, unordered
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node stepNode = nodeList.item(i);
            if (stepNode.getNodeType() != Node.ELEMENT_NODE)  { continue;}
            Element stepElement = (Element) stepNode;
            String stepId=stepElement.getAttribute("id");
            elementMap.put(stepId, stepElement);

            CommandBase subCommand=buildCommandNodeListHelper(stepElement);
            commandMap.put(stepId, subCommand);
        }

        CommandBase commandToReturn = buildCommandNodeHelper(element, commandMap, elementMap);

        return commandToReturn;
    }

    //This turns an element and a processed list of subcommands into a command. Does not traverse anything
    public static CommandBase buildCommandNodeHelper(Element element, HashMap<String, CommandBase> commandMap, HashMap<String, Element> elementMap){
        CommandBase[] commandArray =commandMap.values().toArray(new CommandBase[commandMap.size()]);

        String stepType=element.getTagName().toLowerCase();
        CommandBase myCommand;
        switch (stepType) {
            case "sequentialcommandgroup":
                 //sort the child nodes
                CommandBase[] sortedArray =new CommandBase[commandMap.size()];
                String nextElementId = element.getAttribute("firststep");;
                Element currentElement = elementMap.get(nextElementId);
                int index=0;
                while (currentElement != null){
                    CommandBase commandToAdd = commandMap.get(nextElementId);
                    sortedArray[index]=commandToAdd;
                    
                    nextElementId = elementMap.get(nextElementId).getAttribute("nextstep");
                    currentElement = elementMap.get(nextElementId);
                    index++;
                }
                if (index != commandMap.size()){
                    System.out.println("Error in auton sequentialcommandgroup - items do not form complete list");
                    System.out.println("Could not find "+ nextElementId);
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
                CommandBase deadlineCommand = (CommandBase) Reflection.CreateObjectFromXML(deadlineCommandClass,element);
                myCommand = new ParallelDeadlineGroup(deadlineCommand, commandArray);
                break;
            case "command":
                String myCommandName = element.getAttribute("type");
                Class<?> myCommandClass = Reflection.GetAllCommands().get(myCommandName);
                myCommand = (CommandBase) Reflection.CreateObjectFromXML(myCommandClass,element);
                //myCommand = new TestCommand2(element);
                break;
            default:
                System.out.println("Robotinit.buildCommandHelper: unknown command type: "+stepType);
                return null;
        }
        return myCommand;
    }

    private static void initSubsystems(NodeList subsystemNodeList){
        subsystems = new HashMap<>();
        HashMap<String,Class<?>> subsystemClasses = Reflection.GetAllSubSystems();
        for (int i = 0; i < subsystemNodeList.getLength(); i++) {
            Node currentChild = subsystemNodeList.item(i);
            if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) currentChild;
                if (childElement.getTagName().equals("subsystem")) {
                String subsystemType=currentChild.getAttributes().getNamedItem("type").getNodeValue();
                if (subsystemClasses.containsKey(subsystemType)){
                    subsystems.put(childElement.getAttribute("id"),((SubsystemBase)frc.robot.framework.util.Reflection.CreateObjectFromXML(subsystemClasses.get(subsystemType),childElement)));
                }
                else{
                    System.out.println("Could not find java subsystem for "+subsystemType);
                }
            }
        }
    }
}

    private static void initControllers(NodeList controllerList) {
        for (int i = 0; i < controllerList.getLength(); i++) {
            Node controllerNode = controllerList.item(i);
            if (controllerNode.getNodeType() == Node.ELEMENT_NODE) {
                Element controllerElement = (Element) controllerNode;
                if (controllerElement.getAttribute("type").equals("LOGITECH")) {
                    Integer port = Integer.parseInt(controllerElement.getAttribute("port"));
                    String id = controllerElement.getAttribute("id");
                    controllers.put(id, new ControllerWrapper(new LogitechGamepad(port), controllerElement));
                }
                else if (controllerElement.getAttribute("type").equals("XBOX")) {
                    Integer port = Integer.parseInt(controllerElement.getAttribute("port"));
                    String id = controllerElement.getAttribute("id");
                    controllers.put(id, new ControllerWrapper(new XboxGamepad(port), controllerElement));
                }
                else if (controllerElement.getAttribute("type").equals("PS")) {
                    Integer port = Integer.parseInt(controllerElement.getAttribute("port"));
                    String id = controllerElement.getAttribute("id");
                    controllers.put(id, new ControllerWrapper(new PSGamepad(port), controllerElement));
                }
                else {
                    System.out.println("Unknown controller type: "+ controllerElement.getAttribute("type"));
                    continue;
                }
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
     * @param function   the name of the function (ie "A" or "B")
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
     * @param function   the name of the function (ie "LEFT_JOYSTICK_X"")
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
     * @param function   the name of the function (ie "TURN" or "EXTEND_ARM")
     * @param controllerID the name of the controller (ie "OPERATOR")
     * @return information about the requested control interface.
     */
    public static String getAttribute(String name, String controllerID) {
        ControllerWrapper requestedController = controllers.get(controllerID);
        return requestedController.getAttribute(name);
    }

    public static SubsystemBase GetSubsystem(String subsystemID){
        return subsystems.get(subsystemID);
    }

    public static CommandBase GetAuton(String autonID){
        return autons.get(autonID);
    }

    
}