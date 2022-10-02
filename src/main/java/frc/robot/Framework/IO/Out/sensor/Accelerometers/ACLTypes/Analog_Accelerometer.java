package frc.robot.framework.io.out.sensor.Accelerometers.ACLTypes;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import frc.robot.framework.io.out.sensor.Accelerometers.ACLBase;

public class Analog_Accelerometer implements ACLBase{
    AnalogAccelerometer m_accelerometer;
    public Analog_Accelerometer(String port){
        int m_port = Integer.parseInt(port);
        m_accelerometer = new AnalogAccelerometer(m_port);
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
    public double getAccelerometerAxis(String axis) {
        return 0;
    }
    public void setAccelerometerRange(String range) {}
}
