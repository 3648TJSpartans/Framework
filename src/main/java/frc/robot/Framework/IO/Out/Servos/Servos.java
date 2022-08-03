package frc.robot.Framework.IO.Out.Servos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.Framework.IO.Out.Out.SubsystemCollection;
import frc.robot.Framework.Util.CommandMode;
import frc.robot.Framework.Util.ShuffleboardHandler;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;

public class Servos {
    private static XMLParser parser;
    private static Map<String, SubsystemCollection> m_subsystemCollections = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;
    public Servos(Map subsystemCollections, SubsystemID subsystemID){
        m_subsystemCollections = subsystemCollections;
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }
    /** 
     * [getMotor] returns the Servo associated with the id
     * 
     * @param id the id of the servo or Servo group (ie "SHOOTER_WHEEL" or "LEFT_SIDE")
     */
    private ServoWrapper getMotor(String id) {
        SubsystemCollection requestedSystem = m_subsystemCollections.get(m_subsystemID.name());
        if (requestedSystem == null) {
            motorError(id, m_subsystemID.name());
            return null;
        }
        ServoWrapper requestedMotor = requestedSystem.servos.get(id);
        if (requestedMotor == null) {
            motorError(id, m_subsystemID.name());
            return null;
        }

        return requestedMotor;
    }
    static List<String> errorAry = new ArrayList<>();
    private void motorError(String id, String subsystemID){
        boolean found = false;
        for(var i = 0; i< errorAry.size() ; i++){
            if(errorAry.get(i) == id){
                found = true;
            }
        }
        if(found == false){
            System.out.println("Motor:" + id + " not found. Subsystem: " + subsystemID + " not registered for output.");
            errorAry.add(id);
        }
        
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
