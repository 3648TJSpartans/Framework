package frc.robot.framework.sensor.analoginput;

import edu.wpi.first.wpilibj.AnalogInput;

public class RoboRioAnalogIn implements AnalogInBase{
    private AnalogInput m_input;
    public RoboRioAnalogIn(Integer port){
        m_input = new AnalogInput(port); 
    }
    
    public int getValue() {
        return m_input.getValue();
    }

    public int getAverageValue() {
        return m_input.getAverageValue();
    }

    public double getVoltage(){
        return m_input.getVoltage();
    }

    public double getAverageVoltage(){
        return m_input.getAverageVoltage();
    }

}
