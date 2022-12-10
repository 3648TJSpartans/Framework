package frc.robot.framework.sensor.analoginput;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;



public class AnalogInputs{
    private Map<String, AnaloginWrapper> analoginputs = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public AnalogInputs(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }

    public void put(String id, AnaloginWrapper digital){
        analoginputs.put(id, digital);
    }

    private AnaloginWrapper getAnalog(String id) {
        AnaloginWrapper requestedSensor = analoginputs.get(id);
        if (requestedSensor == null) {
            sensorError(id, subsystemName);
            return null;
        }
        return requestedSensor;
    } 

    private void sensorError(String id, String subsystemName){
        System.out.println("AnalogInputs:" + id + " not found in Subsystem: " + subsystemName);
    }

    public int getValue(String id) {
        AnaloginWrapper requestedAnalog = getAnalog(id);
        return tab.getEnabled(id, subsystemName) ? requestedAnalog.getValue() : 0;
    }

    public int getAverageValue(String id) {
        AnaloginWrapper requestedAnalog = getAnalog(id);
        return tab.getEnabled(id, subsystemName) ? requestedAnalog.getAverageValue() : 0;
    }

    public double getVoltage(String id) {
        AnaloginWrapper requestedAnalog = getAnalog(id);
        return tab.getEnabled(id, subsystemName) ? requestedAnalog.getVoltage() : 0;
    }

    public double getAverageVoltage(String id) {
        AnaloginWrapper requestedAnalog = getAnalog(id);
        return tab.getEnabled(id, subsystemName) ? requestedAnalog.getAverageVoltage() : 0;
    }

    
}
