package frc.robot.Framework.IO.Out.Sensors;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Framework.IO.Out.Out.SubsystemCollection;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.DigitalIn.DigitalInWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Gyroscopes.GyroWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Potentiometers.PotentiometerWrapper;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Ultrasonic.UltrasonicWrapper;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;


public class Sensors{
    private static XMLParser parser;
    private static Map<String, SubsystemCollection> m_subsystemCollections = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    
    public Sensors(Map subsystemCollections, SubsystemID subsystemID){
        m_subsystemCollections = subsystemCollections;
        m_subsystemID = subsystemID;

    }
    private DigitalInWrapper getDio(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("sensor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }
        DigitalInWrapper requestedsensor = requestedSystem.limits.get(id);
        if (requestedsensor == null) {
            System.out.println("sensor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }

        return requestedsensor;
    } 
    private ACLWrapper getAccelerometer(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Motor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }
        ACLWrapper requestedsensor = requestedSystem.ACL.get(id);
        if (requestedsensor == null) {
            System.out.println("Motor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }

        return requestedsensor;
    } 
    private PotentiometerWrapper getPotentiometer(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Motor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }
        PotentiometerWrapper requestedsensor = requestedSystem.potentiometers.get(id);
        if (requestedsensor == null) {
            System.out.println("Motor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }

        return requestedsensor;
    } 
    private UltrasonicWrapper getUltrasonic(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Motor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }
        UltrasonicWrapper requestedsensor = requestedSystem.ultrasonics.get(id);
        if (requestedsensor == null) {
            System.out.println("Motor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }

        return requestedsensor;
    } 
    private GyroWrapper getGyroscope(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Motor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }
        GyroWrapper requestedsensor = requestedSystem.gyros.get(id);
        if (requestedsensor == null) {
            System.out.println("Motor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }

        return requestedsensor;
    } 
    //limit switches or DIOs
    public Boolean getDIO(String id) {
        DigitalInWrapper requestedDio = getDio(id);
        return requestedDio.getDigitalIn();
    }
    //acclerometers
    public Double getACL(String id) {
        ACLWrapper requestedACL = getAccelerometer(id);
        return requestedACL.getAcceleration();
    }
    public Double getACLAxis(String id, String axis) {
        ACLWrapper requestedACL = getAccelerometer(id);
        return requestedACL.getAccelerometerAxis(axis);
    }
    public void setACLRange(String id, String range){
        ACLWrapper requestedACL = getAccelerometer(id);
        requestedACL.setAccelerometerRange(range);
    }
    public void setAClSensitivity(String id, double sensitivity) {
        ACLWrapper requestedACL = getAccelerometer(id);
        requestedACL.setAccelerometerSensitivity(sensitivity);
    }
    public void setACLZero(String id, Double zero) {
        ACLWrapper requestedACL = getAccelerometer(id);
        requestedACL.setAccelerometerZero(zero);
    }
    //potetiometers
    public Double getPOT(String id) {
        PotentiometerWrapper requestedPOT = getPotentiometer(id);
        return requestedPOT.getPotentiometer();
    }
    //ultrasonic
    public double getUTRangeInches(String id) {
        UltrasonicWrapper requestedUT = getUltrasonic(id);
        return requestedUT.getRangeInches();
    }
    public double getUTRangeMM(String id) {
        UltrasonicWrapper requestedUT = getUltrasonic(id);
        return requestedUT.getRangeMM();
    }
    public double getUTEchoChannel(String id) {
        UltrasonicWrapper requestedUT = getUltrasonic(id);
        return requestedUT.getEchoChannel();
    }
    //Gyroscopes
    public double getGYROAccel(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return requestedGYRO.getGyroAccel(axis);
    }
    public double getGYROAngle(String id) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return requestedGYRO.getGyroAngle();
    }
    public double getGYRORate(String id) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return requestedGYRO.getGyroRate();
    }
    public double getGYRORate(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return requestedGYRO.getGyroRate(axis);
    }
    public double getGYROMagneticField(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return requestedGYRO.getMagneticField(axis);
    }
    
}
