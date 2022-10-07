package frc.robot.framework.sensor.gyroscope;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.framework.util.XMLParser;
import frc.robot.subsystem.SubsystemID;


public class Gyroscopes{
    private static XMLParser parser;
    private static Map<String, GyroWrapper> gyroscopes = new HashMap<>();
    private SubsystemID m_subsystemID;
    public Element sensorElement;
    private ShuffleboardHandler tab;

    public Gyroscopes(SubsystemID subsystemID){
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }

    private GyroWrapper getGyroscope(String id) {
        GyroWrapper requestedsensor = gyroscopes.get(id);
        if (requestedsensor == null) {
            sensorError(id, m_subsystemID.name());
            return null;
        }
        return requestedsensor;
    } 

    private void sensorError(String id, String subsystemID){
        System.out.println("Gyro:" + id + " not found in Subsystem: " + subsystemID);
    }
    
    public double getGYROAccel(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedGYRO.getGyroAccel(axis) : 0.0;
    }
    public double getGYROAngle(String id) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedGYRO.getGyroAngle() : 0.0;
    }
    public double getGYRORate(String id) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedGYRO.getGyroRate() : 0.0;
    }
    public double getGYRORate(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedGYRO.getGyroRate(axis) : 0.0;
    }
    public double getGYROMagneticField(String id, String axis) {
        GyroWrapper requestedGYRO = getGyroscope(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedGYRO.getMagneticField(axis) : 0.0;
    }
    
}
