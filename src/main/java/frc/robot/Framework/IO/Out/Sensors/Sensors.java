package frc.robot.Framework.IO.Out.Sensors;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.*;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Framework.IO.Out.Out.SubsystemCollection;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.DigitalIn.DigitalInWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Gyroscopes.GyroWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Potentiometers.PotentiometerWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Ultrasonic.UltrasonicWrapper;
import frc.robot.Framework.Util.ShuffleboardHandler;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;


public class Sensors{
    private static XMLParser parser;
    private static Map<String, SubsystemCollection> m_subsystemCollections = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public Sensors(Map subsystemCollections, SubsystemID subsystemID){
        m_subsystemCollections = subsystemCollections;
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }
    private DigitalInWrapper getDio(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }
        DigitalInWrapper requestedsensor = requestedSystem.limits.get(id);
        if (requestedsensor == null) {
            System.out.println("Dio not found in Subsystem: " + m_subsystemID.name());
            return null;
        }

        return requestedsensor;
    } 
    private ACLWrapper getAccelerometer(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }
        ACLWrapper requestedsensor = requestedSystem.ACL.get(id);
        if (requestedsensor == null) {
            System.out.println("Accelerometer not found in Subsystem: " + m_subsystemID.name());
            return null;
        }

        return requestedsensor;
    } 
    private PotentiometerWrapper getPotentiometer(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }
        PotentiometerWrapper requestedsensor = requestedSystem.potentiometers.get(id);
        if (requestedsensor == null) {
            System.out.println("Potentiometer not found in Subsystem: " + m_subsystemID.name());
            return null;
        }

        return requestedsensor;
    } 
    private UltrasonicWrapper getUltrasonic(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }
        UltrasonicWrapper requestedsensor = requestedSystem.ultrasonics.get(id);
        if (requestedsensor == null) {
            System.out.println("Ultrasonic not found in Subsystem: " + m_subsystemID.name());
            return null;
        }

        return requestedsensor;
    } 
    private GyroWrapper getGyroscope(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }
        GyroWrapper requestedsensor = requestedSystem.gyros.get(id);
        if (requestedsensor == null) {
            System.out.println("Gyro not found in Subsystem: " + m_subsystemID.name());
            return null;
        }

        return requestedsensor;
    } 
    //limit switches or DIOs
    public Boolean getDIO(String id) {
        DigitalInWrapper requestedDio = getDio(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedDio.getDigitalIn() : false;
    }
    //acclerometers
    public Double getACL(String id) {
        ACLWrapper requestedACL = getAccelerometer(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedACL.getAcceleration() : 0.0;
    }
    public Double getACLAxis(String id, String axis) {
        ACLWrapper requestedACL = getAccelerometer(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedACL.getAccelerometerAxis(axis) : 0.0;
    }
    public void setACLRange(String id, String range){
        ACLWrapper requestedACL = getAccelerometer(id);
        if(tab.getEnabled(id, m_subsystemID.toString())) requestedACL.setAccelerometerRange(range) ;
    }
    public void setAClSensitivity(String id, double sensitivity) {
        ACLWrapper requestedACL = getAccelerometer(id);
        if(tab.getEnabled(id, m_subsystemID.toString())) requestedACL.setAccelerometerSensitivity(sensitivity);
    }
    public void setACLZero(String id, Double zero) {
        ACLWrapper requestedACL = getAccelerometer(id);
        if(tab.getEnabled(id, m_subsystemID.toString())) requestedACL.setAccelerometerZero(zero);
        requestedACL.setAccelerometerZero(zero);
    }
    //potetiometers
    public Double getPOT(String id) {
        PotentiometerWrapper requestedPOT = getPotentiometer(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedPOT.getPotentiometer() : 0.0;
    }
    //ultrasonic
    public double getUTRangeInches(String id) {
        UltrasonicWrapper requestedUT = getUltrasonic(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedUT.getRangeInches() : 0.0;
    }
    public double getUTRangeMM(String id) {
        UltrasonicWrapper requestedUT = getUltrasonic(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedUT.getRangeMM() : 0.0;
    }
    public double getUTEchoChannel(String id) {
        UltrasonicWrapper requestedUT = getUltrasonic(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedUT.getEchoChannel() : 0.0;
    }
    //Gyroscopes
    public double getGYROAccel(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedGYRO.getGyroAccel(axis) : 0.0;
    }
    public double getGYROAngle(String id) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedGYRO.getGyroAngle() : 0.0;
    }
    public double getGYRORate(String id) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedGYRO.getGyroRate() : 0.0;
    }
    public double getGYRORate(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedGYRO.getGyroRate(axis) : 0.0;
    }
    public double getGYROMagneticField(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedGYRO.getMagneticField(axis) : 0.0;
    }
    
}
