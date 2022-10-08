package frc.robot.framework.solenoid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.subsystem.SubsystemID;

public class Solenoids {
    private Map<String, SolenoidWrapper> solenoids = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;
    
    public Solenoids(SubsystemID subsystemID){
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }
    
    public void put(String id, SolenoidWrapper solenoid){
        solenoids.put(id, solenoid);
    }

    /** 
     * [setSolenoid] returns the value of requested button
     * 
     * @param id the id of the Solenoid (ie "HOOD_ADJUST")
     * @param extended whether or not the solenoid is extended or not
     */
    public void setSolenoid(String id, boolean extended) {
        SolenoidWrapper requestedSolenoid = solenoids.get(id);
        if (requestedSolenoid == null) {
            solenoidError(id, m_subsystemID.name());
            return;
        }
        //might need testing
        if(tab.getEnabled(id, m_subsystemID.toString())){
            requestedSolenoid.set(extended);
        }
        
    }
    
    private void solenoidError(String id, String subsystemID){
        System.out.println("Solenoid:" + id + " not found. Subsystem: " + subsystemID + " not registered for output.");
    }

}
