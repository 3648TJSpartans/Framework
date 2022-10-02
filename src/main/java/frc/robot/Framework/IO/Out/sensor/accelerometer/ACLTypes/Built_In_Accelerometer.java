package frc.robot.framework.io.out.sensor.accelerometer.ACLTypes;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
import frc.robot.framework.io.out.sensor.accelerometer.ACLBase;

public class Built_In_Accelerometer implements ACLBase{
    Accelerometer m_accelerometer;
    public Built_In_Accelerometer(){
        m_accelerometer = new BuiltInAccelerometer();
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
