package frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLTypes;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLBase;

public class Analog_Accelerometer implements ACLBase{
    AnalogAccelerometer m_accelerometer;
    public Analog_Accelerometer(String port){
        int m_port = Integer.parseInt(port);
        m_accelerometer = new AnalogAccelerometer(m_port);
    }
    public void test(){
        //accelerometer.set
    }
    public double getAcceleration(){
        return m_accelerometer.getAcceleration();
    }
    public void setAccelerometerZero(double zero){
        m_accelerometer.setZero(zero);
    }
    public void setAccelerometerSensitivity(double sensitivity){
        m_accelerometer.setSensitivity(sensitivity);
    }
    @Override
    public double getAccelerometerAxis(String axis) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public void setAccelerometerRange(String range) {
        // TODO Auto-generated method stub
        
    }
}
