package frc.robot.framework.io.out.sensor.SensorTypes.Accelerometers.ACLTypes;

import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
import frc.robot.framework.io.out.sensor.SensorTypes.Accelerometers.ACLBase;

public class ADXL_362 implements ACLBase{
    Accelerometer m_accelerometer;
    
    public ADXL_362(String port, String range){
        Port m_port = SPI.Port.valueOf(port);
        Range m_range = Accelerometer.Range.valueOf(range);
        m_accelerometer = new ADXL362(m_port, m_range);
    }
    public double getAccelerometerAxis(String axis){
        if(axis == "Z"|| axis == "z"){
            return m_accelerometer.getZ();
        }else if(axis == "X"|| axis == "x"){
            return m_accelerometer.getX();
        }else{
            return m_accelerometer.getY();
        }
    }
    public void setAccelerometerRange(String range){
        Range m_range = Accelerometer.Range.valueOf(range);
        m_accelerometer.setRange(m_range);
    }
    public double getAcceleration() {
        return 0;
    }
    public void setAccelerometerZero(double zero) {}
    public void setAccelerometerSensitivity(double sensitivity) {}
}
