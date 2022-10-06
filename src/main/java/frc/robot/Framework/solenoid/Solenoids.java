package frc.robot.framework.solenoid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.robot.Out.SubsystemCollection;
import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.framework.util.XMLParser;
import frc.robot.subsystem.SubsystemID;

public class Solenoids {
    private static XMLParser parser;
    private static Map<String, SubsystemCollection> m_subsystemCollections = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;
    
    public Solenoids(Map<String, SubsystemCollection> subsystemCollections, SubsystemID subsystemID){
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
            solenoidError(id, m_subsystemID.name());
            return;
        }
        SolenoidWrapper requestedSolenoid = requestedSystem.solenoids.get(id);
        if (requestedSolenoid == null) {
            solenoidError(id, m_subsystemID.name());
            return;
        }
        //might need testing
        if(tab.getEnabled(id, m_subsystemID.toString())){
            requestedSolenoid.set(extended);
        }
        
    }
    
    static List<String> errorAry = new ArrayList<>();
    private void solenoidError(String id, String subsystemID){
        boolean found = false;
        for(var i = 0; i< errorAry.size() ; i++){
            
            if(errorAry.get(i) == id){
                found = true;
            }
        }
        if(found == false){
            System.out.println("Solenoid:" + id + " not found. Subsystem: " + subsystemID + " not registered for output.");
            errorAry.add(id);
        }
        
    }

}
