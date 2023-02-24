package frc.robot.framework.sensor.digitalinput;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardFramework;
import frc.robot.framework.util.ShuffleboardFramework.ShuffleboardBase;



public class DigitalInputs{
    private Map<String, DigitalInBase> digitalinputs = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardBase tab;

    public DigitalInputs(String subsystemName){
        this.subsystemName = subsystemName;
        tab = ShuffleboardFramework.getSubsystem(subsystemName);
    }

    public void put(String id, DigitalInBase digital){
        digitalinputs.put(id, digital);
    }

    private DigitalInBase getDio(String id) {
        DigitalInBase requestedSensor = digitalinputs.get(id);
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
        DigitalInBase requestedDio = getDio(id);
        return tab.getEnabled(id) ? requestedDio.getDigitalIn() : false;
    }
}
