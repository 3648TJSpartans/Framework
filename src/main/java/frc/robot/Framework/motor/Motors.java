package frc.robot.framework.motor;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.util.CommandMode;
import frc.robot.framework.util.ShuffleboardHandler;


public class Motors {
    private static Map<String, MotorWrapper> motors = new HashMap<>();
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

    public void put(String id, MotorWrapper motor){
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
    public void setMotor(String id, double speed) {
        MotorWrapper requestedMotor = getMotor(id);
        if(tab.getEnabled(id, subsystemName))  requestedMotor.set(speed);
    }
    /** 
     * [setMotor] returns the value of requested button
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     * @param setpoint 
     * @param mode 
     */
    public void setMotor(String id, double setpoint, CommandMode mode) {
        MotorWrapper requestedMotor = getMotor(id);
        
        if(tab.getEnabled(id, subsystemName)) requestedMotor.set(setpoint, mode);
    }
    public void setVoltage(String id, double voltage){
        MotorWrapper requestedMotor = getMotor(id);
        
        if(tab.getEnabled(id, subsystemName)) requestedMotor.setVoltage(voltage);
    }
    /** 
     * [setPID] returns the value of requested button
     * 
     *  @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     * @param kP 
     * @param kI
     * @param kD
     * @param kF
     */
    public void setPID(String id, double kP, double kI, double kD, double kF) {
        MotorWrapper requestedMotor = getMotor(id);
        if(tab.getEnabled(id, subsystemName)) requestedMotor.setPID(kP, kI, kD, kF);
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
