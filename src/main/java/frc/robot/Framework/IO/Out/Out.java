package frc.robot.Framework.IO.Out;

import frc.robot.Framework.IO.Out.Motors.MotorWrapper;
import frc.robot.Framework.IO.Out.Sensors.Sensors;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.DigitalIn.Digital_In;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.DigitalIn.DigitalInWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Gyroscopes.GyroWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Potentiometers.Analog_Potentiometer;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Potentiometers.PotentiometerWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Ultrasonic.Ping_Ultrasonic;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Ultrasonic.UltrasonicWrapper;
import frc.robot.Framework.IO.Out.Solenoids.SolenoidWrapper;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;
import frc.robot.Framework.Util.CommandMode;
import frc.robot.Framework.Util.XMLMerger;

import java.util.Map;

import java.util.HashMap;

import org.w3c.dom.*;

import edu.wpi.first.wpilibj.Compressor;

/**
 * [Out] is a class containing static methods for controlling all outputs from
 * the robot. This includes, but is not limited to, motors and solenoids.
 */

public class Out {
    private static XMLParser parser;
    private static Map<String, SubsystemCollection> subsystemCollections = new HashMap<>();
    private static Compressor compressor;
    public Sensors sensors;

    /**
     * [SubsystemCollection] represents all of the outputs from a specific
     * subsystem.
     */

    public static class SubsystemCollection {
        public Map<String, MotorWrapper> motors = new HashMap<>();
        public Map<String, SolenoidWrapper> solenoids = new HashMap<>();
        public Map<String, ACLWrapper> ACL = new HashMap<>();
        public Map<String, DigitalInWrapper> limits = new HashMap<>();
        public Map<String, GyroWrapper> gyros = new HashMap<>();
        public Map<String,PotentiometerWrapper> potentiometers = new HashMap<>();
        public Map<String, UltrasonicWrapper> ultrasonics = new HashMap<>();
        private Element systemElement;

        /**
         * Constructor for [SubsystemCollection]. Takes an XML element and parses all
         * children to add them to the collection.
         * 
         * @param system XML element representing subsystem
         */

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
                        NodeList sensorList = childElement.getChildNodes();
                        for(int j = 0; j < sensorList.getLength(); j++){
                            Node currentSensor = sensorList.item(j);
                            Element childSensor = (Element) currentSensor;
                            if (childSensor.getTagName().equals("sensor")) {
                                //needs sensor wrapper
                                //String sensorID = childSensor.getAttribute("id");
                                //sensors.put(sensorID, new SensorWrapper(childSensor));
                            }
                        }
                    } else if (childElement.getTagName().equals("group")) {
                        String id = childElement.getAttribute("id");
                        motors.put(id, new MotorWrapper(childElement, true));
                    } else if (childElement.getTagName().equals("solenoid")) {
                        String id = childElement.getAttribute("id");
                        solenoids.put(id, new SolenoidWrapper(childElement));
                    } else if (childElement.getTagName().equals("compressor")) {
                        //needs fixing
                        compressor = new Compressor(null);
                    } else if (childElement.getTagName().equals("acl") || childElement.getTagName().equals("accelerometer")) {
                        String id = childElement.getAttribute("id");
                        ACL.put(id, new ACLWrapper(childElement));
                    } else if (childElement.getTagName().equals("dio")||childElement.getTagName().equals("limitswitch")) {
                        String id = childElement.getAttribute("id");
                        limits.put(id, new DigitalInWrapper(childElement));
                    } else if(childElement.getTagName().equals("gyro")||childElement.getTagName().equals("gyroscopes")){
                        String id = childElement.getAttribute("id");
                        gyros.put(id, new GyroWrapper(childElement));
                    }else if (childElement.getTagName().equals("pot") || childElement.getTagName().equals("potientiometers")) {
                        String id = childElement.getAttribute("id");
                        potentiometers.put(id, new PotentiometerWrapper(childElement));
                    } else if(childElement.getTagName().equals("sonic") || childElement.getTagName().equals("ultrasonic")){
                        String id = childElement.getAttribute("id");
                        ultrasonics.put(id, new UltrasonicWrapper(childElement));
                    }else {
                        System.out.println("Output type: " + childElement.getTagName() + " on subsystem: "
                                + system.getTagName() + " doesn't exist.");
                    }
                }
            }
        }

        /**
         * [getAttribute] returns the XML attribute associated with the given tag
         */

        public String getAttribute(String attribute) {
            return systemElement.getAttribute(attribute);
        }
    }

    /**
     * [Init] initializes [Out], registering all the outputs from the XML robot
     * configuration file with [Out].
     * 
     * @param xmlPath path to the configuration file relative to /deploy
     */

    public static void Init(String... strings) {
        XMLMerger merger = new XMLMerger();
        String XMLPath = merger.merger("subsystem", strings);
        parser = new XMLParser(XMLPath);
        Element root = parser.getRootElement();
        //NodeList subsystemList = root.getChildNodes();
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
                        System.out.print(subsystemElement.getTagName());
                        subsystemCollections.put(subsystemElement.getTagName(), new SubsystemCollection(subsystemElement));
                    }
                }
            }
        }
        
    }

    private SubsystemID subsystemID;

    /**
     * Constructor for [Out]. Sets which subsystem this instance of [Out] is for.
     * That system will only have access to its designated outputs
     * 
     * @param systemID
     */

    public Out(SubsystemID systemID) {
        subsystemID = systemID;
        sensors = new Sensors(subsystemCollections, subsystemID);

    };
    /** 
     * [getMotor] returns the motor associated with the id
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    private MotorWrapper getMotor(String id) {
        SubsystemCollection requestedSystem = subsystemCollections.get(subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Motor not found. Subsystem: " + subsystemID.name() + " not registered for output.");
            return null;
        }
        MotorWrapper requestedMotor = requestedSystem.motors.get(id);
        if (requestedMotor == null) {
            System.out.println("Motor not found. Subsystem: " + subsystemID.name() + " not registered for output.");
            return null;
        }

        return requestedMotor;
    }
    /** 
     * [setSolenoid] returns the value of requested button
     * 
     * @param id the id of the Solenoid (ie "HOOD_ADJUST")
     * @param extended whether or not the solenoid is extended or not
     */
    public void setSolenoid(String id, boolean extended) {
        SubsystemCollection requestedSystem = subsystemCollections.get(subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Solenoid not found. Subsystem: " + subsystemID.name() + " not registered for output.");
            return;
        }
        SolenoidWrapper requestedSolenoid = requestedSystem.solenoids.get(id);
        if (requestedSolenoid == null) {
            System.out.println("Solenoid not found. Subsystem: " + subsystemID.name() + " not registered for output.");
            return;
        }
        requestedSolenoid.set(extended);
    }
    

    /** 
     * [setMotor] sets the speed of the requested motor or motor group
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     * @param speed the speed of the motor
     */
    public void setMotor(String id, double speed) {
        MotorWrapper requestedMotor = getMotor(id);
        requestedMotor.set(speed);
    }
    /** 
     * [setMotor] returns the value of requested button
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     * @param setpoint 
     * @param mode 
     */
    public void setMotor(String id, double setpoint, CommandMode mode) {
        MotorWrapper requestedMotor = getMotor(id);
        requestedMotor.set(setpoint, mode);
    }
    /** 
     * [setPID] returns the value of requested button
     * 
     *  @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     * @param kP 
     * @param kI
     * @param kD
     * @param kF
     */
    public void setPID(String id, double kP, double kI, double kD, double kF) {
        MotorWrapper requestedMotor = getMotor(id);
        requestedMotor.setPID(kP, kI, kD, kF);
    }
    /** 
     * [getVelocity] returns the speed of requested motor
     * 
     *  @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    public double getVelocity(String id) {
        MotorWrapper requestedMotor = getMotor(id);
        return requestedMotor.getVelocity();
    }
    /** 
     * [getPosition] returns the postion of requested motor
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE"))
     */
    public double getPosition(String id) {
        MotorWrapper requestedMotor = getMotor(id);
        return requestedMotor.getPosition();
    }
    /** 
     * [resetEncoder] returns the value of requested button
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    public void resetEncoder(String id) {
        MotorWrapper requestedMotor = getMotor(id);
        requestedMotor.resetEncoder();
    }
    
    /** 
     * [getAttribute] returns the value associated with the requested attribute
     * 
     * @param attribute the name of the attribute (ie "default_speed="3750"" or turret_lower="-220")
     * 
     */
    public String getAttribute(String attribute) {
        SubsystemCollection currentSystem = subsystemCollections.get(subsystemID.name());
        return currentSystem.getAttribute(attribute);
    }
    
}