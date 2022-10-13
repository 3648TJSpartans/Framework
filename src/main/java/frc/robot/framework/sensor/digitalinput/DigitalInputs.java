package frc.robot.framework.sensor.digitalinput;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;



public class DigitalInputs{
    private static Map<String, DigitalInWrapper> digitalinputs = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public DigitalInputs(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }

    public void put(String id, DigitalInWrapper digital){
        digitalinputs.put(id, digital);
    }

    private DigitalInWrapper getDio(String id) {
        DigitalInWrapper requestedSensor = digitalinputs.get(id);
        if (requestedSensor == null) {
            sensorError(id, subsystemName);
            return null;
        }

        return requestedSensor;
    } 

    private void sensorError(String id, String subsystemName){
        System.out.println("DigitalInput:" + id + " not found in Subsystem: " + subsystemName);
    }

    //limit switches or DIOs
    public Boolean getDIO(String id) {
        DigitalInWrapper requestedDio = getDio(id);
        return tab.getEnabled(id, subsystemName) ? requestedDio.getDigitalIn() : false;
    }
}
