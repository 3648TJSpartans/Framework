package frc.robot.framework.sensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.io.out.Out.SubsystemCollection;
import frc.robot.framework.sensor.accelerometer.ACLWrapper;
import frc.robot.framework.sensor.digitalinput.DigitalInWrapper;
import frc.robot.framework.sensor.gyroscope.GyroWrapper;
import frc.robot.framework.sensor.potentiometer.PotentiometerWrapper;
import frc.robot.framework.sensor.ultrasonic.UltrasonicWrapper;
import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.framework.util.XMLParser;
import frc.robot.subsystem.SubsystemID;


public class Sensors{
    private static XMLParser parser;
    private static Map<String, SubsystemCollection> m_subsystemCollections = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public Sensors(Map<String, SubsystemCollection> subsystemCollections, SubsystemID subsystemID){
        m_subsystemCollections = subsystemCollections;
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }
    private DigitalInWrapper getDio(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            subsystemError(m_subsystemID.name());
            return null;
        }
        DigitalInWrapper requestedSensor = requestedSystem.limits.get(id);
        if (requestedSensor == null) {
            sensorError("Gyro", id, m_subsystemID.name());
            return null;
        }

        return requestedSensor;
    } 
    private ACLWrapper getAccelerometer(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            subsystemError(m_subsystemID.name());
            return null;
        }
        ACLWrapper requestedsensor = requestedSystem.ACL.get(id);
        if (requestedsensor == null) {
            sensorError("Gyro", id, m_subsystemID.name());
            return null;
        }

        return requestedsensor;
    } 
    private PotentiometerWrapper getPotentiometer(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            subsystemError(m_subsystemID.name());
            return null;
        }
        PotentiometerWrapper requestedsensor = requestedSystem.potentiometers.get(id);
        if (requestedsensor == null) {
            sensorError("Gyro", id, m_subsystemID.name());
            return null;
        }

        return requestedsensor;
    } 
    private UltrasonicWrapper getUltrasonic(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            subsystemError(m_subsystemID.name());
            return null;
        }
        UltrasonicWrapper requestedsensor = requestedSystem.ultrasonics.get(id);
        if (requestedsensor == null) {
            sensorError("Gyro", id, m_subsystemID.name());
            return null;
        }

        return requestedsensor;
    } 
    private GyroWrapper getGyroscope(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            subsystemError(m_subsystemID.name());
            return null;
        }
        GyroWrapper requestedsensor = requestedSystem.gyros.get(id);
        if (requestedsensor == null) {
            sensorError("Gyro", id, m_subsystemID.name());
            return null;
        }

        return requestedsensor;
    } 
    
    static List<String> subsystemErrorAry = new ArrayList<>();
    static List<String> sensorErrorAry = new ArrayList<>();
    private void subsystemError(String id){
        boolean found = false;
        for(var i = 0; i< subsystemErrorAry.size() ; i++){
            if(subsystemErrorAry.get(i) == id){
                found = true;
            }
        }
        if(found == false){
            System.out.println("Subsystem: " + id + " not registered for output.");
            subsystemErrorAry.add(id);
        }
        
    }
    private void sensorError(String sensorType, String id, String subsystemID){
        boolean found = false;
        for(var i = 0; i< sensorErrorAry.size() ; i++){
            if(sensorErrorAry.get(i) == id){
                found = true;
            }
        }
        if(found == false){
            System.out.println(sensorType + ":" + id + " not found in Subsystem: " + subsystemID);
            sensorErrorAry.add(id);
        }
             
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
