package frc.robot.framework.servo;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.subsystem.SubsystemID;

public class Servos {
    private static Map<String, ServoWrapper> servos = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;
    public Servos(SubsystemID subsystemID){
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }
    /** 
     * [getMotor] returns the Servo associated with the id
     * 
     * @param id the id of the servo or Servo group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    private ServoWrapper getMotor(String id) {
        ServoWrapper requestedMotor = servos.get(id);
        if (requestedMotor == null) {
            motorError(id, m_subsystemID.name());
            return null;
        }
        return requestedMotor;
    }

    private void motorError(String id, String subsystemID){
        System.out.println("Motor:" + id + " not found. Subsystem: " + subsystemID + " not registered for output.");
    }
    /** 
     * [setServo] sets the speed of the requested motor or motor group
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     * @param position the position of the servo (0 to 1)
     */
    public void setServo(String id, double position) {
        ServoWrapper requestedMotor = getMotor(id);
        
        if(tab.getEnabled(id, m_subsystemID.toString()))  requestedMotor.set(position);
    }
 
    public void setServoAngle(String id, int angle) {
        ServoWrapper requestedMotor = getMotor(id);
        
        if(tab.getEnabled(id, m_subsystemID.toString()))  requestedMotor.setAngle(angle);
    }

}
