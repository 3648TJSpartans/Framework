package frc.robot.framework.sensor.analoginput;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;



public class AnalogInputs{
    private Map<String, AnalogInBase> analoginputs = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public AnalogInputs(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }

    public void put(String id, AnalogInBase digital){
        analoginputs.put(id, digital);
    }

    private AnalogInBase getAnalog(String id) {
        AnalogInBase requestedSensor = analoginputs.get(id);
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
        AnalogInBase requestedAnalog = getAnalog(id);
        return tab.getEnabled(id, subsystemName) ? requestedAnalog.getValue() : 0;
    }

    public int getAverageValue(String id) {
        AnalogInBase requestedAnalog = getAnalog(id);
        return tab.getEnabled(id, subsystemName) ? requestedAnalog.getAverageValue() : 0;
    }

    public double getVoltage(String id) {
        AnalogInBase requestedAnalog = getAnalog(id);
        return tab.getEnabled(id, subsystemName) ? requestedAnalog.getVoltage() : 0;
    }

    public double getAverageVoltage(String id) {
        AnalogInBase requestedAnalog = getAnalog(id);
        return tab.getEnabled(id, subsystemName) ? requestedAnalog.getAverageVoltage() : 0;
    }

    
}
