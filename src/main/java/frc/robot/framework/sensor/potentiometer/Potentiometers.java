package frc.robot.framework.sensor.potentiometer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;



public class Potentiometers{
    private Map<String, PotentiometerWrapper> potentiometers = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public Potentiometers(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }
    
    public void put(String id, PotentiometerWrapper pot){
        potentiometers.put(id, pot);
    }

    public Set<String> GetAllPotentiometerIDs(){
        return potentiometers.keySet();
    }

    private PotentiometerWrapper getPotentiometer(String id) {
        PotentiometerWrapper requestedsensor = potentiometers.get(id);
        if (requestedsensor == null) {
            sensorError(id, subsystemName);
            return null;
        }
        return requestedsensor;
    } 
    
    private void sensorError(String id, String subsystemName){
        System.out.println("Pot:" + id + " not found in Subsystem: " + subsystemName);
    }

    public Double getPOT(String id) {
        PotentiometerWrapper requestedPOT = getPotentiometer(id);
        return tab.getEnabled(id, subsystemName) ? requestedPOT.getPotentiometer() : 0.0;
    }
}
