package frc.robot.framework.sensor.digitalinput;

import edu.wpi.first.wpilibj.DigitalInput;

public class Digital_In implements DigitalInBase{
    private DigitalInput m_input;
    public Digital_In(Integer port){
        m_input = new DigitalInput(port); 
    }
    public Boolean getDigitalIn() {
        return m_input.get();
    }
    

}
