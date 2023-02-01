package frc.robot.framework.sensor.accelerometer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;


import frc.robot.framework.util.ShuffleboardHandler;



public class Accelerometers{
    private Map<String, ACLBase> accelerometers = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public Accelerometers(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }

    public Set<String> GetAllAccelerometerIDs(){
        return accelerometers.keySet();
    }

    public void put(String id, ACLBase accelerometer){
        accelerometers.put(id, accelerometer);
    }

    private ACLBase getAccelerometer(String id) {
        ACLBase requestedsensor = accelerometers.get(id);
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
        ACLBase requestedACL = getAccelerometer(id);
        return tab.getEnabled(id, subsystemName) ? requestedACL.getAcceleration() : 0.0;
    }
    public Double getACLAxis(String id, String axis) {
        ACLBase requestedACL = getAccelerometer(id);
        return tab.getEnabled(id, subsystemName) ? requestedACL.getAccelerometerAxis(axis) : 0.0;
    }
    public void setACLRange(String id, String range){
        ACLBase requestedACL = getAccelerometer(id);
        if(tab.getEnabled(id, subsystemName)) requestedACL.setAccelerometerRange(range) ;
    }
    public void setAClSensitivity(String id, double sensitivity) {
        ACLBase requestedACL = getAccelerometer(id);
        if(tab.getEnabled(id, subsystemName)) requestedACL.setAccelerometerSensitivity(sensitivity);
    }
    public void setACLZero(String id, Double zero) {
        ACLBase requestedACL = getAccelerometer(id);
        if(tab.getEnabled(id, subsystemName)) requestedACL.setAccelerometerZero(zero);
        requestedACL.setAccelerometerZero(zero);
    }
}
