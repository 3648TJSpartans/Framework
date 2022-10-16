package frc.robot.framework.controller;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.framework.util.Reflection;

public class ControllerWrapper{

    private ControllerBase controller;
    //private XMLParser parser = new XMLParser();
    public Map<String, JoystickButton> buttons = new HashMap<>();
    public Map<String, CommandBase> axis = new HashMap<>();
    private Element subsystemElement;

    public ControllerWrapper(ControllerBase controller, Element system){
        this.controller=controller;
        subsystemElement = system;
        NodeList buttonNodes = system.getElementsByTagName("button");
        for(int i = 0; i < buttonNodes.getLength(); i++){
            Node currentButton = buttonNodes.item(i);
            if(currentButton.getNodeType() == Node.ELEMENT_NODE){
                Element buttonElement = (Element)currentButton;
                
                try{
                JoystickButton tempButton = new JoystickButton((GenericHID) controller,controller.GetButtonMap().get(buttonElement.getAttribute("button")));
                String command =buttonElement.getAttribute("command");
                Class<?> clazz= Reflection.GetAllCommands().get(command);
                CommandBase base = (CommandBase)Reflection.CreateObjectFromXML(clazz, buttonElement);
                tempButton.whenPressed(base);
                buttons.put(buttonElement.getAttribute("button"),tempButton);
                }catch (Exception e){
                    System.out.println(e);
                }
                
            }
        }

        NodeList axisNodes = system.getElementsByTagName("axis");
        for(int i = 0; i < axisNodes.getLength(); i++){
            Node currentAxis = axisNodes.item(i);
            if(currentAxis.getNodeType() == Node.ELEMENT_NODE){
                Element axisElement = (Element)currentAxis;

                try{
                    String command =axisElement.getAttribute("command");
                    Class<?> clazz= Reflection.GetAllCommands().get(command);
                    CommandBase base = (CommandBase)Reflection.CreateObjectFromXML(clazz, axisElement);
                    base.schedule();
                    axis.put(axisElement.getAttribute("button"),base);
                }catch (Exception e){
                    System.out.println(e);
                }
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
    
    private void controllerError(String type, String id, String subsystemName){
        System.out.println(type +  ":" + id + " not found. Subsystem:"+subsystemName +" not registered on requested controller.");
    }
}