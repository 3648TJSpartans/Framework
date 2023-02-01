package frc.robot.framework.encoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;


import frc.robot.framework.util.ShuffleboardHandler;



public class Encoders{
    private Map<String, EncoderBase> encoders = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public Encoders(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }

    public void put(String id, EncoderBase encoder){
        encoders.put(id, encoder);
    }
    
    public Set<String> GetAllEncoderIDs(){
        return encoders.keySet();
    }

    public EncoderBase getEncoder(String id) {
        EncoderBase requestedsensor = encoders.get(id);
        if (requestedsensor == null) {
            sensorError("Encoder", id, subsystemName);
            return null;
        }
        return requestedsensor;
    } 

    private void sensorError(String sensorType, String id, String subsystemName){
        System.out.println(sensorType + ":" + id + " not found in Subsystem: " + subsystemName);
    }

    public int getTicks(String id){
        EncoderBase requestedEncoder = getEncoder(id);
        return tab.getEnabled(id, subsystemName) ? requestedEncoder.getTicks() : 0;
    }

    public double getVelocity(String id){
        EncoderBase requestedEncoder = getEncoder(id);
        return tab.getEnabled(id, subsystemName) ? requestedEncoder.getVelocity() : 0;
    }

    public double getPosition(String id){
        EncoderBase requestedEncoder = getEncoder(id);
        return tab.getEnabled(id, subsystemName) ? requestedEncoder.getPosition() : 0;
    }

    public void reset(String id){
        EncoderBase requestedEncoder = getEncoder(id);
        requestedEncoder.resetEncoder();
    }
}
