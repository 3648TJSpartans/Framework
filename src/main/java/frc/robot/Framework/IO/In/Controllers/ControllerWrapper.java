package frc.robot.Framework.IO.In.Controllers;

import java.util.Map;
import java.util.HashMap;

import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;

import org.w3c.dom.*;

public class ControllerWrapper{

    private ControllerBase controller;
    private Map<String, SubsystemCollection> subsystemCollections = new HashMap<>();
    //private XMLParser parser = new XMLParser();
    private class SubsystemCollection{
        public Map<String, String> buttons = new HashMap<>();
        public Map<String, String> axes = new HashMap<>();
        private Element subsystemElement;

        public SubsystemCollection(Element system){
            subsystemElement = system;
            NodeList buttonNodes = system.getElementsByTagName("button");
            for(int i = 0; i < buttonNodes.getLength(); i++){
                Node currentButton = buttonNodes.item(i);
                if(currentButton.getNodeType() == Node.ELEMENT_NODE){
                    Element buttonElement = (Element)currentButton;
                    buttons.put(buttonElement.getAttribute("function"), buttonElement.getAttribute("button"));
                }
            }

            NodeList axisNodes = system.getElementsByTagName("axis");
            for(int i = 0; i < axisNodes.getLength(); i++){
                Node currentAxis = axisNodes.item(i);
                if(currentAxis.getNodeType() == Node.ELEMENT_NODE){
                    Element axisElement = (Element)currentAxis;
                    axes.put(axisElement.getAttribute("function"), axisElement.getAttribute("axis"));
                }
            }
        }

        public String getAttribute(String attribute){
            return subsystemElement.getAttribute(attribute);
        }
    }

    public ControllerWrapper(ControllerBase controllerType, Element controllerXML){
        controller = controllerType;
        NodeList subsystems = controllerXML.getChildNodes();
        for(int i = 0; i < subsystems.getLength(); i++){
            Node currentSubsystem = subsystems.item(i);
            if(currentSubsystem.getNodeType() == Node.ELEMENT_NODE){
                Element systemElement = (Element)currentSubsystem;
                subsystemCollections.put(systemElement.getTagName(), new SubsystemCollection(systemElement));
            }
        }
    }
    
    public boolean getButton(String buttonName, SubsystemID subsystemID){
        SubsystemCollection requestedSystem = subsystemCollections.get(subsystemID.name());
        if(requestedSystem == null){
            System.out.println("Button not found. Subsystem :"+subsystemID.name()+" not registered on requested controller.");
            return false;
        }
        String requestedButton = requestedSystem.buttons.get(buttonName);
        if(requestedButton == null){
            System.out.println("Button not found. Subsystem :"+subsystemID.name()+" not registered on requested controller.");
            return false;
        }
        return controller.getButton(requestedButton);
    }

    public double getAxis(String axisName, SubsystemID subsystemID){
        SubsystemCollection requestedSystem = subsystemCollections.get(subsystemID.name());
        if(requestedSystem == null){
            System.out.println("Axis not found. Subsystem :"+subsystemID.name()+" not registered on requested controller.");
            return 0.0;
        }
        String requestedAxis = requestedSystem.axes.get(axisName);
        if(requestedAxis == null){
            System.out.println("Axis not found. Subsystem :"+subsystemID.name()+" not registered on requested controller.");
            return 0.0;
        }
        return controller.getAxis(requestedAxis);
    }

    public String getAttribute(String attribute, SubsystemID subsystemID){
        SubsystemCollection requestedSystem = subsystemCollections.get(subsystemID.name());
        if(requestedSystem == null){
            System.out.println("Attribute not found.");
        }
        return requestedSystem.getAttribute(attribute);
    }
}