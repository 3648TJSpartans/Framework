package frc.robot.framework.sensor.potentiometer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardFramework;
import frc.robot.framework.util.ShuffleboardFramework.ShuffleboardBase;



public class Potentiometers{
    private Map<String, PotentiometerBase> potentiometers = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardBase tab;

    public Potentiometers(String subsystemName){
        this.subsystemName = subsystemName;
        tab = ShuffleboardFramework.getSubsystem(subsystemName);
    }
    
    public void put(String id, PotentiometerBase pot){
        potentiometers.put(id, pot);
    }

    public Set<String> GetAllPotentiometerIDs(){
        return potentiometers.keySet();
    }

    private PotentiometerBase getPotentiometer(String id) {
        PotentiometerBase requestedsensor = potentiometers.get(id);
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
        PotentiometerBase requestedPOT = getPotentiometer(id);
        return tab.getEnabled(id) ? requestedPOT.getPotentiometer() : 0.0;
    }
}
