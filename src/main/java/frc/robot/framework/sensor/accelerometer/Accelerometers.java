package frc.robot.framework.sensor.accelerometer;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;


import frc.robot.framework.util.ShuffleboardHandler;



public class Accelerometers{
    private Map<String, ACLWrapper> accelerometers = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public Accelerometers(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }

    public void put(String id, ACLWrapper accelerometer){
        accelerometers.put(id, accelerometer);
    }

    private ACLWrapper getAccelerometer(String id) {
        ACLWrapper requestedsensor = accelerometers.get(id);
        if (requestedsensor == null) {
            sensorError("Gyro", id, subsystemName);
            return null;
        }
        return requestedsensor;
    } 

    private void sensorError(String sensorType, String id, String subsystemName){
        System.out.println(sensorType + ":" + id + " not found in Subsystem: " + subsystemName);
    }

    //acclerometers
    public Double getACL(String id) {
        ACLWrapper requestedACL = getAccelerometer(id);
        return tab.getEnabled(id, subsystemName) ? requestedACL.getAcceleration() : 0.0;
    }
    public Double getACLAxis(String id, String axis) {
        ACLWrapper requestedACL = getAccelerometer(id);
        return tab.getEnabled(id, subsystemName) ? requestedACL.getAccelerometerAxis(axis) : 0.0;
    }
    public void setACLRange(String id, String range){
        ACLWrapper requestedACL = getAccelerometer(id);
        if(tab.getEnabled(id, subsystemName)) requestedACL.setAccelerometerRange(range) ;
    }
    public void setAClSensitivity(String id, double sensitivity) {
        ACLWrapper requestedACL = getAccelerometer(id);
        if(tab.getEnabled(id, subsystemName)) requestedACL.setAccelerometerSensitivity(sensitivity);
    }
    public void setACLZero(String id, Double zero) {
        ACLWrapper requestedACL = getAccelerometer(id);
        if(tab.getEnabled(id, subsystemName)) requestedACL.setAccelerometerZero(zero);
        requestedACL.setAccelerometerZero(zero);
    }
}
