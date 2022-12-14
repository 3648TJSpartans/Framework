package frc.robot.framework.sensor.potentiometer;

import edu.wpi.first.wpilibj.AnalogPotentiometer;


public class Analog_Potentiometer implements PotentiometerBase{

    AnalogPotentiometer m_Potentiometer;
    public Analog_Potentiometer(final int port, double scale){
        if(scale != 0){
            m_Potentiometer = new AnalogPotentiometer(port, scale);
        }else{
            m_Potentiometer = new AnalogPotentiometer(port);
        }
    }
    public double getPotentiometer(){
        return m_Potentiometer.get();
    }
}