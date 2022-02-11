package frc.robot.Framework.IO.Out.Sensors.SensorTypes.Potentiometers;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.robot.Framework.IO.Out.Sensors.SensorBase;

public class Analog_Potentiometer implements SensorBase, PotentiometerBase{

    AnalogPotentiometer m_Potentiometer;
    public Analog_Potentiometer(final int port, double scale){
        if(scale != 0){
            m_Potentiometer = new AnalogPotentiometer(port, scale);
        }else{
            m_Potentiometer = new AnalogPotentiometer(port);
        }
    }
    public void test(){
        //POT.
    }
    // public boolean equals(Object obj){
    //    return m_Potentiometer.equals(obj);
    // }
    public double getPotentiometer(){
        return m_Potentiometer.get();
    }
}