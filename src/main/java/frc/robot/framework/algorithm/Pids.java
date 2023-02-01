package frc.robot.framework.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;

public class Pids {
    private Map<String, PIDBase> pids = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public Pids(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }

    public void put(String id, PIDBase pid){
        pids.put(id, pid);
    }
    
    public Set<String> GetAllPIDsIDs(){
        return pids.keySet();
    }

    public PIDBase getPID(String id) {
        PIDBase requestedPID = pids.get(id);
        if (requestedPID == null) {
            System.out.println("PID id="+id+" not found in substem "+subsystemName);
            return null;
        }
        return requestedPID;
    }
}
