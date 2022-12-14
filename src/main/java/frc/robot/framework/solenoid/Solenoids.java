package frc.robot.framework.solenoid;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;


public class Solenoids {
    private Map<String, SolenoidWrapper> solenoids = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;
    
    public Solenoids(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }
    
    public void put(String id, SolenoidWrapper solenoid){
        solenoids.put(id, solenoid);
    }

    public Set<String> GetAllSolenoidIDs(){
        return solenoids.keySet();
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
            solenoidError(id, subsystemName);
            return;
        }
        //might need testing
        if(tab.getEnabled(id, subsystemName)){
            requestedSolenoid.set(extended);
        }
        
    }
    
    private void solenoidError(String id, String subsystemName){
        System.out.println("Solenoid:" + id + " not found. Subsystem: " + subsystemName + " not registered for output.");
    }

}
