package frc.robot.framework.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardFramework;
import frc.robot.framework.util.ShuffleboardFramework.ShuffleboardBase;

public class Pids {
    private Map<String, PIDBase> pids = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardBase tab;

    public Pids(String subsystemName){
        this.subsystemName = subsystemName;
        tab = ShuffleboardFramework.getSubsystem(subsystemName);
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
