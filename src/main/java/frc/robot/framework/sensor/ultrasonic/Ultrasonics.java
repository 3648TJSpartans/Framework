package frc.robot.framework.sensor.ultrasonic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;


public class Ultrasonics{
    private Map<String, UltrasonicBase> ultrasonics = new HashMap<>();
    private String subsystemName;
    public  Element sensorElement;
    private ShuffleboardHandler tab;

    public Ultrasonics(String subsystemName){
        subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }

    public void put(String id, UltrasonicBase ultrasonic){
        ultrasonics.put(id, ultrasonic);
    }

    public Set<String> GetAllUltrasonicIDs(){
        return ultrasonics.keySet();
    }

    private UltrasonicBase getUltrasonic(String id) {
        UltrasonicBase requestedsensor = ultrasonics.get(id);
        if (requestedsensor == null) {
            sensorError("Gyro", id, subsystemName);
            return null;
        }

        return requestedsensor;
    } 

    private void sensorError(String sensorType, String id, String subsystemName){
        System.out.println(sensorType + ":" + id + " not found in Subsystem: " + subsystemName);
    }

    //ultrasonic
    public double getUTRangeInches(String id) {
        UltrasonicBase requestedUT = getUltrasonic(id);
        return tab.getEnabled(id, subsystemName) ? requestedUT.getRangeInches() : 0.0;
    }
    public double getUTRangeMM(String id) {
        UltrasonicBase requestedUT = getUltrasonic(id);
        return tab.getEnabled(id, subsystemName) ? requestedUT.getRangeMM() : 0.0;
    }
    public double getUTEchoChannel(String id) {
        UltrasonicBase requestedUT = getUltrasonic(id);
        return tab.getEnabled(id, subsystemName) ? requestedUT.getEchoChannel() : 0.0;
    }
}
