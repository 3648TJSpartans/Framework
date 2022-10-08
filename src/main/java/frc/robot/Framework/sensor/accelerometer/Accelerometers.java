package frc.robot.framework.sensor.accelerometer;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;


import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.subsystem.SubsystemID;


public class Accelerometers{
    private static Map<String, ACLWrapper> accelerometers = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public Accelerometers(SubsystemID subsystemID){
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }

    public void put(String id, ACLWrapper accelerometer){
        accelerometers.put(id, accelerometer);
    }

    private ACLWrapper getAccelerometer(String id) {
        ACLWrapper requestedsensor = accelerometers.get(id);
        if (requestedsensor == null) {
            sensorError("Gyro", id, m_subsystemID.name());
            return null;
        }
        return requestedsensor;
    } 

    private void sensorError(String sensorType, String id, String subsystemID){
        System.out.println(sensorType + ":" + id + " not found in Subsystem: " + subsystemID);
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
}
