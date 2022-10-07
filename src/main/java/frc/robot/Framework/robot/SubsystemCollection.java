package frc.robot.framework.robot;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import frc.robot.framework.motor.MotorWrapper;
import frc.robot.framework.motor.Motors;
import frc.robot.framework.sensor.accelerometer.ACLWrapper;
import frc.robot.framework.sensor.accelerometer.Accelerometers;
import frc.robot.framework.sensor.digitalinput.DigitalInWrapper;
import frc.robot.framework.sensor.digitalinput.DigitalInputs;
import frc.robot.framework.sensor.gyroscope.GyroWrapper;
import frc.robot.framework.sensor.gyroscope.Gyroscopes;
import frc.robot.framework.sensor.potentiometer.PotentiometerWrapper;
import frc.robot.framework.sensor.potentiometer.Potentiometers;
import frc.robot.framework.sensor.ultrasonic.UltrasonicWrapper;
import frc.robot.framework.sensor.ultrasonic.Ultrasonics;
import frc.robot.framework.servo.ServoWrapper;
import frc.robot.framework.servo.Servos;
import frc.robot.framework.solenoid.SolenoidWrapper;
import frc.robot.framework.solenoid.Solenoids;
import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.framework.util.XMLMerger;
import frc.robot.framework.util.XMLParser;
import frc.robot.subsystem.SubsystemID;

/**
 * [Out] is a class containing static methods for controlling all outputs from
 * the robot. This includes, but is not limited to, motors and solenoids.
 */

public class SubsystemCollection implements RobotXML {
    private SubsystemID subsystemID;
    public Motors motors;
    public Servos servos;
    public Solenoids solenoids;
    public Accelerometers accelerometers;
    public Gyroscopes gyroscopes;
    public DigitalInputs digitalInputs;
    public Potentiometers potentiometers;
    public Ultrasonics ultrasonics;
    private Element systemElement;

    public SubsystemCollection(Element system) {
        ReadXML(system);
    }
    
   public String getAttribute(String attribute) {
       return systemElement.getAttribute(attribute);
    }
   

    public void ReadXML(Element system){
        systemElement = system;
        //ShuffleboardHandler tab = ShuffleboardCollections.get(systemElement.getTagName());
        NodeList children = system.getChildNodes();
        System.out.println(systemElement.getNodeName());
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
                }else if (childElement.getTagName().equals("servo")) {
                    String id = childElement.getAttribute("id");
                    servos.put(id, new ServoWrapper(childElement));
                } else if (childElement.getTagName().equals("group")) {
                    String id = childElement.getAttribute("id");
                    servos.put(id, new ServoWrapper(childElement, true));
                } else if (childElement.getTagName().equals("solenoid")) {
                    String id = childElement.getAttribute("id");                       
                    solenoids.put(id, new SolenoidWrapper(childElement));
                } else if (childElement.getTagName().equals("compressor")) {
                    //needs fixing
                    //compressor = new Compressor(null);
                } else if (childElement.getTagName().equals("acl") || childElement.getTagName().equals("accelerometer")) {
                    String id = childElement.getAttribute("id");
                    accelerometers.put(id, new ACLWrapper(childElement));
                } else if (childElement.getTagName().equals("dio")||childElement.getTagName().equals("limitswitch")) {
                    String id = childElement.getAttribute("id");
                    digitalInputs.put(id, new DigitalInWrapper(childElement));
                } else if(childElement.getTagName().equals("gyro")||childElement.getTagName().equals("gyroscopes")){
                    String id = childElement.getAttribute("id");
                    gyroscopes.put(id, new GyroWrapper(childElement));
                }else if (childElement.getTagName().equals("pot") || childElement.getTagName().equals("potientiometers")) {
                    String id = childElement.getAttribute("id");
                    potentiometers.put(id, new PotentiometerWrapper(childElement));
                } else if(childElement.getTagName().equals("ut") || childElement.getTagName().equals("ultrasonic")){
                    String id = childElement.getAttribute("id");
                    ultrasonics.put(id, new UltrasonicWrapper(childElement));
                }else {
                    System.out.println("Output type: " + childElement.getTagName() + " on subsystem: "+ system.getTagName() + " doesn't exist.");
                }
            }
        }
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }

}









/* */


    /**
     * [Init] initializes [Out], registering all the outputs from the XML robot
     * configuration file with [Out].
     * 
     * @param xmlPath path to the configuration file relative to /deploy
     

    public static void Init(String... strings) {
        XMLMerger merger = new XMLMerger();
        String XMLPath = merger.merger("subsystem", strings);
        parser = new XMLParser(XMLPath);
        Element root = parser.getRootElement();
        //NodeList subsystemList = root.getChildNodes();
        ShuffleboardHandler tab = new ShuffleboardHandler(root);
        NodeList systemList = root.getElementsByTagName("subsystem");
        for (int i = 0; i < systemList.getLength(); i++) {
            Node currentSystem = systemList.item(i);
            if (currentSystem.getNodeType() == Node.ELEMENT_NODE) {
                Element systemElement = (Element) currentSystem;
                NodeList subsystemList = systemElement.getChildNodes();
                for (int j = 0; j < subsystemList.getLength(); j++) {
                    Node currentSubsystem = subsystemList.item(j);
                    if (currentSubsystem.getNodeType() == Node.ELEMENT_NODE) {
                        Element subsystemElement = (Element) currentSubsystem;
                        subsystemCollections.put(subsystemElement.getTagName(), new SubsystemCollection(subsystemElement));
                    }
                else {System.out.println("Isn't this always true? "+ currentSubsystem);}
                }
            }
            else {System.out.println("Isn't this always true? "+ currentSystem);}
        }
        
    }
   */