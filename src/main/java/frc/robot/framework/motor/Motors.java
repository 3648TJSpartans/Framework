package frc.robot.framework.motor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import frc.robot.framework.util.CommandMode;
import frc.robot.framework.util.ShuffleboardHandler;


public class Motors {
    private Map<String, MotorWrapper> motors = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardHandler tab;
    public Motors(String subsystemName){
        this.subsystemName = subsystemName;
        tab = new ShuffleboardHandler(subsystemName.toString());
    }
    /** 
     * [getMotor] returns the motor associated with the id
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    private MotorWrapper getMotor(String id) {
        MotorWrapper requestedMotor = motors.get(id);
        if (requestedMotor == null) {
            motorError(id, subsystemName);
            return null;
        }

        return requestedMotor;
    }

    public Set<String> GetAllMotorIDs(){
        return motors.keySet();
    }

    public void put(String id, MotorWrapper motor){
        // tab = new ShuffleboardHandler(subsystemName.toString());
        // SimpleWidget widget = tab.add
        // NetworkTableEntry entry = widget.getEntry();
        // Widgets.put(title, entry);
        // SimpleWidget liveWindowWidget = liveWindow.add(id, enabled);
        // NetworkTableEntry liveWindowEntry = liveWindowWidget.getEntry();
        // liveWindowWidgets.put(title, liveWindowEntry);
        motors.put(id, motor);
    }

    private void motorError(String id, String subsystemName){
        System.out.println("Motor:" + id + " not found. Subsystem: " + subsystemName + " not registered for output.");
    }
    /** 
     * [setMotor] sets the speed of the requested motor or motor group
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     * @param speed the speed of the motor
     */
    public void setPower(String id, double power) {
        MotorWrapper requestedMotor = getMotor(id);
        if(tab.getEnabled(id, subsystemName))  requestedMotor.setPower(power);
    }
    /** 
     * [setMotor] returns the value of requested button
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     * @param setpoint 
     * @param mode 
     */
    public void setCommandMode(String id, CommandMode mode) {
        MotorWrapper requestedMotor = getMotor(id);
        
        if(tab.getEnabled(id, subsystemName)) requestedMotor.setCommandMode(mode);
    }
    
    /** 
     * [getVelocity] returns the speed of requested motor
     * 
     *  @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    public double getVelocity(String id) {
        MotorWrapper requestedMotor = getMotor(id);
        return tab.getEnabled(id, subsystemName) ? requestedMotor.getVelocity() : 0.0;
        
    }
    /** 
     * [getPosition] returns the postion of requested motor
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE"))
     */
    public double getPosition(String id) {
        MotorWrapper requestedMotor = getMotor(id);
        return tab.getEnabled(id, subsystemName)  ? requestedMotor.getPosition() : 0.0;
        
    }
    /** 
     * [resetEncoder] returns the value of requested button
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    public void resetEncoder(String id) {
        MotorWrapper requestedMotor = getMotor(id);
        if(tab.getEnabled(id, subsystemName)) requestedMotor.resetEncoder();
    }
}
