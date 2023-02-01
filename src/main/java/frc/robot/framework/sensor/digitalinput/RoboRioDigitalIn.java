package frc.robot.framework.sensor.digitalinput;

import edu.wpi.first.wpilibj.DigitalInput;

public class RoboRioDigitalIn implements DigitalInBase{
    private DigitalInput m_input;
    public RoboRioDigitalIn(Integer port){
        m_input = new DigitalInput(port); 
    }
    public Boolean getDigitalIn() {
        return m_input.get();
    }
    

}
