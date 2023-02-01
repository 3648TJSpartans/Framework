package frc.robot.framework.sensor.analoginput;

import com.revrobotics.SparkMaxAnalogSensor;

public class SparkMaxAnalogIn implements AnalogInBase{
    private SparkMaxAnalogSensor  m_input;
    public SparkMaxAnalogIn(SparkMaxAnalogSensor analogSensor){
        m_input =analogSensor; 
    }
    
    //Spark max doesn't give you the same 12 bit int for value. Copy it to match roborio
    public int getValue() {
        return (int) Math.round(m_input.getVoltage()/5.0*4096.0); 
    }

    public int getAverageValue() {
        return (int) Math.round(m_input.getVoltage()/5.0*4096.0); 
    }

    public double getVoltage(){
        return m_input.getVoltage();
    }

    public double getAverageVoltage(){
        return m_input.getVoltage();
    }

}
