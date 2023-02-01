package frc.robot.framework.servo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;


public class Servos {
    private Map<String, ServoBase> servos = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;
    public Servos(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }

    public void put(String id, ServoBase servo){
        servos.put(id, servo);
    }

    public Set<String> GetAllServoIDs(){
        return servos.keySet();
    }

    /** 
     * [getMotor] returns the Servo associated with the id
     * 
     * @param id the id of the servo or Servo group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    private ServoBase getMotor(String id) {
        ServoBase requestedMotor = servos.get(id);
        if (requestedMotor == null) {
            motorError(id, subsystemName);
            return null;
        }
        return requestedMotor;
    }

    private void motorError(String id, String subsystemName){
        System.out.println("Motor:" + id + " not found. Subsystem: " + subsystemName + " not registered for output.");
    }
    /** 
     * [setServo] sets the speed of the requested motor or motor group
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     * @param position the position of the servo (0 to 1)
     */
    public void setServo(String id, double position) {
        ServoBase requestedMotor = getMotor(id);
        
        if(tab.getEnabled(id, subsystemName))  requestedMotor.set(position);
    }
 
    public void setServoAngle(String id, int angle) {
        ServoBase requestedMotor = getMotor(id);
        
        if(tab.getEnabled(id, subsystemName))  requestedMotor.setAngle(angle);
    }

}
