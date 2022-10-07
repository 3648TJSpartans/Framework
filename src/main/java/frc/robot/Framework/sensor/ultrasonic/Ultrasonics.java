package frc.robot.framework.sensor.ultrasonic;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.framework.util.XMLParser;
import frc.robot.subsystem.SubsystemID;


public class Ultrasonics{
    private static XMLParser parser;
    private Map<String, UltrasonicWrapper> ultrasonics = new HashMap<>();
    private SubsystemID m_subsystemID;
    public  Element sensorElement;
    private ShuffleboardHandler tab;

    public Ultrasonics(SubsystemID subsystemID){
        m_subsystemID = subsystemID;
        tab = new ShuffleboardHandler(subsystemID.toString());
    }
    private UltrasonicWrapper getUltrasonic(String id) {
        UltrasonicWrapper requestedsensor = ultrasonics.get(id);
        if (requestedsensor == null) {
            sensorError("Gyro", id, m_subsystemID.name());
            return null;
        }

        return requestedsensor;
    } 

    private void sensorError(String sensorType, String id, String subsystemID){
        System.out.println(sensorType + ":" + id + " not found in Subsystem: " + subsystemID);
    }

    //ultrasonic
    public double getUTRangeInches(String id) {
        UltrasonicWrapper requestedUT = getUltrasonic(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedUT.getRangeInches() : 0.0;
    }
    public double getUTRangeMM(String id) {
        UltrasonicWrapper requestedUT = getUltrasonic(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedUT.getRangeMM() : 0.0;
    }
    public double getUTEchoChannel(String id) {
        UltrasonicWrapper requestedUT = getUltrasonic(id);
        return tab.getEnabled(id, m_subsystemID.toString()) ? requestedUT.getEchoChannel() : 0.0;
    }
}
