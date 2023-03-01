package frc.robot.framework.controller;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.framework.util.Reflection;

public class ControllerWrapper{

    private ControllerBase controller;
    //private XMLParser parser = new XMLParser();
    public Map<String, Trigger> buttons = new HashMap<>();
    public Map<String, CommandBase> axis = new HashMap<>();
    private Element subsystemElement;

    public ControllerWrapper(ControllerBase controller, Element system){
        this.controller=controller;
        subsystemElement = system;
        NodeList buttonNodes = system.getElementsByTagName("button");
        for(int i = 0; i < buttonNodes.getLength(); i++){
            Node currentButton = buttonNodes.item(i);
            if(currentButton.getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            Element buttonElement = (Element)currentButton;
            
            try{
                String buttonInput=buttonElement.getAttribute("button");
                Trigger tempTrigger;
                if (tryParseInt(buttonInput)==null){ //String - A B X Y etc.
                    tempTrigger = new JoystickButton((GenericHID) controller,controller.GetButtonMap().get(buttonElement.getAttribute("button")));
                }
                else { //Number - POV. 0-360
                    tempTrigger = new POVButton((GenericHID) controller, Integer.parseInt(buttonElement.getAttribute("button")));
                }
                String command =buttonElement.getAttribute("command");
                Class<?> clazz= Reflection.GetAllCommands().get(command);
                if (clazz==null){
                    System.out.println("ButtonTrigger: Could not find command class for "+command);
                    continue;
                }
                CommandBase base = (CommandBase)Reflection.CreateObjectFromXML(clazz, buttonElement);
                if (base==null){
                    System.out.println("Could not create Command Object with XML");
                    continue;
                }
                switch (buttonElement.getAttribute("trigger").toLowerCase()) {
                    case "pressed":
                        tempTrigger.onTrue(base);
                        break;
                    case "released":
                        tempTrigger.onFalse(base);
                        break;
                    case "held":
                        tempTrigger.whileTrue(base);
                        break;
                    case "whenheld":
                        tempTrigger.whileTrue(base.repeatedly());
                        break;
                    case "toggle":
                        tempTrigger.toggleOnTrue(base);
                        break;
                    default:
                        System.out.println("Could not find action:"+buttonElement.getAttribute("trigger"));
                        break;
                }
                base.schedule();
                buttons.put(buttonElement.getAttribute("button"),tempTrigger);
            }catch (Exception e){
                System.out.println(e);
            }
        }

        NodeList axisNodes = system.getElementsByTagName("defaultCommand");
        for(int i = 0; i < axisNodes.getLength(); i++){
            Node currentAxis = axisNodes.item(i);
            if(currentAxis.getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            Element axisElement = (Element)currentAxis;
            //<axis axis="RIGHT_JOYSTICK_X" subsystem="TankDrive" mapto="setInputTurn" scale="2"></axis>
            try{
                String command =axisElement.getAttribute("command");
                Class<?> clazz= Reflection.GetAllCommands().get(command);
                if (clazz==null){
                    System.out.println("Axis/DefaultCommand: Could not find command class for "+command);
                    continue;
                }
                CommandBase base = (CommandBase)Reflection.CreateObject(clazz, 
                    new Class<?>[]{
                        Class.forName("org.w3c.dom.Element"),
                        Class.forName("frc.robot.framework.controller.ControllerBase")},
                    new Object[]{axisElement, controller});
                if (base==null){
                    System.out.println("Could not create Command Object with XML");
                    continue;
                }
                //base.perpetually();
                //base.schedule();
                //CommandScheduler.getInstance().schedule(false, base);
                axis.put(axisElement.getAttribute("axis")+"_"+command,base);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public String getAttribute(String attribute){
        return subsystemElement.getAttribute(attribute);
    }
    
    public boolean getButton(String buttonName){
        // SubsystemCollection requestedSystem = subsystemCollections.get(subsystemName);
        return controller.getButton(buttonName);
    }

    public double getAxis(String axisName){
        return controller.getAxis(axisName);
    }

    private static Integer tryParseInt(String someText) {
        try {
           return Integer.parseInt(someText);
        } catch (NumberFormatException ex) {
           return null;
        }
     }
    
}