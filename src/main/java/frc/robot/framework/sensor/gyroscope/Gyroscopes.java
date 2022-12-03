package frc.robot.framework.sensor.gyroscope;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;



public class Gyroscopes{
    private Map<String, GyroWrapper> gyroscopes = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public Gyroscopes(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }

    public void put(String id, GyroWrapper gyro){
        gyroscopes.put(id, gyro);
    }

    public Set<String> GetAllGyroscopeIDs(){
        return gyroscopes.keySet();
    }

    private GyroWrapper getGyroscope(String id) {
        GyroWrapper requestedsensor = gyroscopes.get(id);
        if (requestedsensor == null) {
            sensorError(id, subsystemName);
            return null;
        }
        return requestedsensor;
    } 

    private void sensorError(String id, String subsystemName){
        System.out.println("Gyro:" + id + " not found in Subsystem: " + subsystemName);
    }
    
    public double getGYROAccel(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, subsystemName) ? requestedGYRO.getGyroAccel(axis) : 0.0;
    }
    public double getGYROAngle(String id) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, subsystemName) ? requestedGYRO.getGyroAngle() : 0.0;
    }
    public double getGYRORate(String id) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, subsystemName) ? requestedGYRO.getGyroRate() : 0.0;
    }
    public double getGYRORate(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, subsystemName) ? requestedGYRO.getGyroRate(axis) : 0.0;
    }
    public double getGYROMagneticField(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, subsystemName) ? requestedGYRO.getMagneticField(axis) : 0.0;
    }
    
}
