package frc.robot.framework.solenoid;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardFramework;
import frc.robot.framework.util.ShuffleboardFramework.ShuffleboardBase;


public class Solenoids {
    private Map<String, SolenoidBase> solenoids = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardBase tab;
    
    public Solenoids(String subsystemName){
        this.subsystemName = subsystemName;
        tab = ShuffleboardFramework.getSubsystem(subsystemName);
    }
    
    public void put(String id, SolenoidBase solenoid){
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
        SolenoidBase requestedSolenoid = solenoids.get(id);
        if (requestedSolenoid == null) {
            solenoidError(id, subsystemName);
            return;
        }
        //might need testing
        if(tab.getEnabled(id)){
            requestedSolenoid.set(extended);
        }
        
    }
    
    private void solenoidError(String id, String subsystemName){
        System.out.println("Solenoid:" + id + " not found. Subsystem: " + subsystemName + " not registered for output.");
    }

}
