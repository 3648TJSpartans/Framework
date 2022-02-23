package frc.robot.Framework.IO.Out.Motors;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.Framework.IO.Out.Out.SubsystemCollection;
import frc.robot.Framework.Util.CommandMode;
import frc.robot.Framework.Util.ShuffleboardHandler;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;

public class Motors {
    private static XMLParser parser;
    private static Map<String, SubsystemCollection> m_subsystemCollections = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;
    public Motors(Map subsystemCollections, SubsystemID subsystemID){
        m_subsystemCollections = subsystemCollections;
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }
    /** 
     * [getMotor] returns the motor associated with the id
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    private MotorWrapper getMotor(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            System.out.println("Motor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }
        MotorWrapper requestedMotor = requestedSystem.motors.get(id);
        if (requestedMotor == null) {
            System.out.println("Motor not found. Subsystem: " + m_subsystemID.name() + " not registered for output.");
            return null;
        }

        return requestedMotor;
    }
    /** 
     * [setMotor] sets the speed of the requested motor or motor group
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     * @param speed the speed of the motor
     */
    public void setMotor(String id, double speed) {
        MotorWrapper requestedMotor = getMotor(id);
        
        if(tab.getEnabled(id, m_subsystemID.toString()))  requestedMotor.set(speed);
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
        
        if(tab.getEnabled(id, m_subsystemID.toString())) requestedMotor.set(setpoint, mode);
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
        if(tab.getEnabled(id, m_subsystemID.toString())) requestedMotor.setPID(kP, kI, kD, kF);
    }
    /** 
     * [getVelocity] returns the speed of requested motor
     * 
     *  @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    public double getVelocity(String id) {
        MotorWrapper requestedMotor = getMotor(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedMotor.getVelocity() : 0.0;
        
    }
    /** 
     * [getPosition] returns the postion of requested motor
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE"))
     */
    public double getPosition(String id) {
        MotorWrapper requestedMotor = getMotor(id);
        return tab.getEnabled(id, m_subsystemID.toString())  ? requestedMotor.getPosition() : 0.0;
        
    }
    /** 
     * [resetEncoder] returns the value of requested button
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    public void resetEncoder(String id) {
        MotorWrapper requestedMotor = getMotor(id);
        if(tab.getEnabled(id, m_subsystemID.toString())) requestedMotor.resetEncoder();
    }
}
