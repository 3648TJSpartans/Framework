package frc.robot.framework.robot;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import frc.robot.framework.encoder.EncoderWrapper;
import frc.robot.framework.encoder.Encoders;
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


/**
 * [Out] is a class containing static methods for controlling all outputs from
 * the robot. This includes, but is not limited to, motors and solenoids.
 */

public class SubsystemCollection implements RobotXML {
    public final String subsystemName;
    public Motors motors;
    public Servos servos;
    public Solenoids solenoids;
    public Accelerometers accelerometers;
    public Gyroscopes gyroscopes;
    public DigitalInputs digitalInputs;
    public Potentiometers potentiometers;
    public Encoders encoders;
    public Ultrasonics ultrasonics;
    private Element systemElement;

    public SubsystemCollection(Element system) {
        this.subsystemName = system.getAttribute("id");
        ReadXML(system);
    }

    public String getAttribute(String attribute) {
        return systemElement.getAttribute(attribute);
    }

    public void ReadXML(Element system) {
        systemElement = system;
        motors = new Motors(subsystemName);
        servos = new Servos(subsystemName);
        solenoids = new Solenoids(subsystemName);
        accelerometers = new Accelerometers(subsystemName);
        gyroscopes = new Gyroscopes(subsystemName);
        potentiometers = new Potentiometers(subsystemName);
        encoders = new Encoders(subsystemName);
        ultrasonics = new Ultrasonics(subsystemName);
        // ShuffleboardHandler tab =
        // ShuffleboardCollections.get(systemElement.getTagName());
        NodeList children = system.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node currentChild = children.item(i);
            if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) currentChild;
                String id = childElement.getAttribute("id");
                if (childElement.getTagName().equals("motor")) {
                    motors.put(id, new MotorWrapper(childElement, false, this));
                } else if (childElement.getTagName().equals("motorgroup")) {
                    motors.put(id, new MotorWrapper(childElement, true, this));
                } else if (childElement.getTagName().equals("servo")) {
                    servos.put(id, new ServoWrapper(childElement));
                } else if (childElement.getTagName().equals("encoder")) {
                    encoders.put(id, new EncoderWrapper(childElement));
                } else if (childElement.getTagName().equals("solenoid")) {
                    solenoids.put(id, new SolenoidWrapper(childElement));
                } else if (childElement.getTagName().equals("acl") || childElement.getTagName().equals("accelerometer")) {
                    accelerometers.put(id, new ACLWrapper(childElement));
                } else if (childElement.getTagName().equals("dio") || childElement.getTagName().equals("limitswitch")) {
                    digitalInputs.put(id, new DigitalInWrapper(childElement));
                } else if (childElement.getTagName().equals("gyro") || childElement.getTagName().equals("gyroscopes")) {
                    gyroscopes.put(id, new GyroWrapper(childElement));
                } else if (childElement.getTagName().equals("pot") || childElement.getTagName().equals("potientiometers")) {
                    potentiometers.put(id, new PotentiometerWrapper(childElement));
                } else if (childElement.getTagName().equals("ut") || childElement.getTagName().equals("ultrasonic")) {
                    ultrasonics.put(id, new UltrasonicWrapper(childElement));
                } else {
                    System.out.println("Unknown XML element: " + childElement.getTagName() + " on subsystem: "+ system.getTagName());
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
 * 
 * 
 *                public static void Init(String... strings) {
 *                XMLMerger merger = new XMLMerger();
 *                String XMLPath = merger.merger("subsystem", strings);
 *                parser = new XMLParser(XMLPath);
 *                Element root = parser.getRootElement();
 *                //NodeList subsystemList = root.getChildNodes();
 *                ShuffleboardHandler tab = new ShuffleboardHandler(root);
 *                NodeList systemList = root.getElementsByTagName("subsystem");
 *                for (int i = 0; i < systemList.getLength(); i++) {
 *                Node currentSystem = systemList.item(i);
 *                if (currentSystem.getNodeType() == Node.ELEMENT_NODE) {
 *                Element systemElement = (Element) currentSystem;
 *                NodeList subsystemList = systemElement.getChildNodes();
 *                for (int j = 0; j < subsystemList.getLength(); j++) {
 *                Node currentSubsystem = subsystemList.item(j);
 *                if (currentSubsystem.getNodeType() == Node.ELEMENT_NODE) {
 *                Element subsystemElement = (Element) currentSubsystem;
 *                subsystemCollections.put(subsystemElement.getTagName(), new
 *                SubsystemCollection(subsystemElement));
 *                }
 *                else {System.out.println("Isn't this always true? "+
 *                currentSubsystem);}
 *                }
 *                }
 *                else {System.out.println("Isn't this always true? "+
 *                currentSystem);}
 *                }
 * 
 *                }
 */