package frc.robot.framework.sensor.digitalinput;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.subsystem.SubsystemID;


public class DigitalInputs{
    private static Map<String, DigitalInWrapper> digitalinputs = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public DigitalInputs(SubsystemID subsystemID){
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }

    private DigitalInWrapper getDio(String id) {
        DigitalInWrapper requestedSensor = digitalinputs.get(id);
        if (requestedSensor == null) {
            sensorError(id, m_subsystemID.name());
            return null;
        }

        return requestedSensor;
    } 

    private void sensorError(String id, String subsystemID){
        System.out.println("DigitalInput:" + id + " not found in Subsystem: " + subsystemID);
    }

    //limit switches or DIOs
    public Boolean getDIO(String id) {
        DigitalInWrapper requestedDio = getDio(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedDio.getDigitalIn() : false;
    }
}
