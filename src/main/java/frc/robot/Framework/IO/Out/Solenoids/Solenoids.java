package frc.robot.Framework.IO.Out.Solenoids;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.Framework.IO.Out.Out.SubsystemCollection;
import frc.robot.Framework.Util.ShuffleboardHandler;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;

public class Solenoids {
    private static XMLParser parser;
    private static Map<String, SubsystemCollection> m_subsystemCollections = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;
    
    public Solenoids(Map subsystemCollections, SubsystemID subsystemID){
        m_subsystemCollections = subsystemCollections;
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }
    /** 
     * [setSolenoid] returns the value of requested button
     * 
     * @param id the id of the Solenoid (ie "HOOD_ADJUST")
     * @param extended whether or not the solenoid is extended or not
     */
    public void setSolenoid(String id, boolean extended) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Solenoid not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return;
        }
        SolenoidWrapper requestedSolenoid = requestedSystem.solenoids.get(id);
        if (requestedSolenoid == null) {
            System.out.println("Solenoid not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return;
        }
        //might need testing
        if(tab.getEnabled(id, m_subsystemID.toString())){
            requestedSolenoid.set(extended);
        }
        
    }

}
