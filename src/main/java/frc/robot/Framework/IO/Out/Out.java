package frc.robot.Framework.IO.Out;

import frc.robot.Framework.IO.Out.Motors.MotorWrapper;
import frc.robot.Framework.IO.Out.Solenoids.SolenoidWrapper;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;
import frc.robot.Framework.Util.CommandMode;

import java.util.Map;

import java.util.HashMap;

import org.w3c.dom.*;

import edu.wpi.first.wpilibj.Compressor;

public class Out {
    private static XMLParser parser;
    private static Map<String, SubsystemCollection> subsystemCollections = new HashMap<>();
    private static Compressor compressor;

    private static class SubsystemCollection {
        public Map<String, MotorWrapper> motors = new HashMap<>();
        public Map<String, SolenoidWrapper> solenoids = new HashMap<>();
        private Element systemElement;

        public SubsystemCollection(Element system) {
            systemElement = system;
            NodeList children = system.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node currentChild = children.item(i);
                if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                    Element childElement = (Element) currentChild;
                    if (childElement.getTagName().equals("motor")) {
                        String id = childElement.getAttribute("id");
                        motors.put(id, new MotorWrapper(childElement));
                    } else if (childElement.getTagName().equals("group")) {
                        String id = childElement.getAttribute("id");
                        motors.put(id, new MotorWrapper(childElement, true));
                    } else if (childElement.getTagName().equals("solenoid")) {
                        String id = childElement.getAttribute("id");
                        solenoids.put(id, new SolenoidWrapper(childElement));
                    }else if(childElement.getTagName().equals("compressor")){
                        //needs to be wored
                        compressor = new Compressor(null);
                    }else{
                        System.out.println("Output type: "+childElement.getTagName()+" on subsystem: "+system.getTagName()+" doesn't exist.");
                    }
                }
            }
        }

        public String getAttribute(String attribute){
            return systemElement.getAttribute(attribute);
        }
    }
 
    public static void Init(String xmlPath){
        parser = new XMLParser("/home/lvuser/deploy/"+xmlPath);
        Element root = parser.getRootElement();
        NodeList subsystemList = root.getChildNodes();
        for(int i = 0; i < subsystemList.getLength(); i++){
            Node currentSubsystem = subsystemList.item(i);
            if(currentSubsystem.getNodeType() == Node.ELEMENT_NODE){
                Element systemElement = (Element)currentSubsystem;
                subsystemCollections.put(systemElement.getTagName(), new SubsystemCollection(systemElement));
            }
        }
    }

    private SubsystemID id;

    public Out(SubsystemID systemID){
        id = systemID;
    };

    public void setMotor(String name, double setpoint){
        MotorWrapper requestedMotor = getMotor(name);
        requestedMotor.set(setpoint);
    }

    public void setMotor(String name, double setpoint, CommandMode mode){
        MotorWrapper requestedMotor = getMotor(name);
        requestedMotor.set(setpoint, mode);
    }

    public void setPID(String name, double kP, double kI, double kD, double kF){
        MotorWrapper requestedMotor = getMotor(name);
        requestedMotor.setPID(kP, kI, kD, kF);
    }

    public double getVelocity(String name){
        MotorWrapper requestedMotor = getMotor(name);
        return requestedMotor.getVelocity();
    }

    public double getPosition(String name){
        MotorWrapper requestedMotor = getMotor(name);
        return requestedMotor.getPosition();
    }

    public void resetEncoder(String name){
        MotorWrapper requestedMotor = getMotor(name);
        requestedMotor.resetEncoder();
    }

    private MotorWrapper getMotor(String name){
        SubsystemCollection requestedSystem = subsystemCollections.get(id.name());
        if(requestedSystem == null){
            System.out.println("Motor not found. Subsystem: "+id.name()+" not registered for output.");
            return null;
        }
        MotorWrapper requestedMotor = requestedSystem.motors.get(name);
        if(requestedMotor == null){
            System.out.println("Motor not found. Subsystem: "+id.name()+" not registered for output.");
            return null;
        }

        return requestedMotor;
    }

    public void setSolenoid(String name, boolean extended){
        SubsystemCollection requestedSystem = subsystemCollections.get(id.name());
        if(requestedSystem == null){
            System.out.println("Solenoid not found. Subsystem: "+id.name()+" not registered for output.");
            return;
        }
        SolenoidWrapper requestedSolenoid = requestedSystem.solenoids.get(name);
        if(requestedSolenoid == null){
            System.out.println("Motor not found. Subsystem: "+id.name()+" not registered for output.");
            return;
        }
        requestedSolenoid.set(extended);
    }

    public String getAttribute(String attribute){
        SubsystemCollection currentSystem = subsystemCollections.get(id.name());
        return currentSystem.getAttribute(attribute);
    }
}