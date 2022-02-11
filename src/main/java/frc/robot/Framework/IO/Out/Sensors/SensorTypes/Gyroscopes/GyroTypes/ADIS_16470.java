package frc.robot.Framework.IO.Out.Sensors.SensorTypes.Gyroscopes.GyroTypes;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Gyroscopes.GyroBase;

public class ADIS_16470 implements GyroBase{
    ADIS16470_IMU m_gyro;

    public ADIS_16470(){
        m_gyro = new ADIS16470_IMU();
        
    }
    public double getaccel(String axis){
        if(axis == "Z"|| axis == "z"){
            return m_gyro.getAccelZ();
        }else if(axis == "X"|| axis == "X"){
            return m_gyro.getAccelX();
        }else{
            return m_gyro.getAccelY();
        }
    }
    public double getGyroRate(){
        return m_gyro.getRate();
    }
    public double getGyroAngle(){
        return m_gyro.getAngle();
    }
    @Override
    public double getGyroAccel(String axis) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public double getGyroAngle(String axis) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public double getGyroRate(String axis) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public double getMagneticField(String axis) {
        // TODO Auto-generated method stub
        return 0;
    }
}
