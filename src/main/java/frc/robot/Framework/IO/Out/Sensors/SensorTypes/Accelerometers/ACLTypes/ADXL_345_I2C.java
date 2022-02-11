package frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLTypes;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLBase;

public class ADXL_345_I2C implements ACLBase{
    Accelerometer m_accelerometer;
    public ADXL_345_I2C(String port, String range){
        Port m_port = I2C.Port.valueOf(port);
        Range m_range = Accelerometer.Range.valueOf(range);
        m_accelerometer = new ADXL345_I2C(m_port, m_range);
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
    @Override
    public double getAcceleration() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public void setAccelerometerZero(double zero) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void setAccelerometerSensitivity(double sensitivity) {
        // TODO Auto-generated method stub
        
    }


}