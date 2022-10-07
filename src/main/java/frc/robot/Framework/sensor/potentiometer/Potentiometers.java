package frc.robot.framework.sensor.potentiometer;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.framework.util.XMLParser;
import frc.robot.subsystem.SubsystemID;


public class Potentiometers{
    private static XMLParser parser;
    private static Map<String, PotentiometerWrapper> potentiometers = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public Potentiometers(SubsystemID subsystemID){
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }
    
    private PotentiometerWrapper getPotentiometer(String id) {
        PotentiometerWrapper requestedsensor = potentiometers.get(id);
        if (requestedsensor == null) {
            sensorError(id, m_subsystemID.name());
            return null;
        }
        return requestedsensor;
    } 
    
    private void sensorError(String id, String subsystemID){
        System.out.println("Pot:" + id + " not found in Subsystem: " + subsystemID);
    }

    public Double getPOT(String id) {
        PotentiometerWrapper requestedPOT = getPotentiometer(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedPOT.getPotentiometer() : 0.0;
    }
}
