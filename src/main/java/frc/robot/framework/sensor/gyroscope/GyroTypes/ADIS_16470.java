package frc.robot.framework.sensor.gyroscope.GyroTypes;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import frc.robot.framework.sensor.gyroscope.GyroBase;

public class ADIS_16470 implements GyroBase{
    public static ADIS16470_IMU m_gyro;

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
    public double getGyroAccel(String axis) {
        return 0;
    }
    public double getGyroAngle(String axis) {
            return m_gyro.getAngle();

    }
    public double getGyroRate(String axis) {
        return 0;
    }
    public double getMagneticField(String axis) {
        return 0;
    }
}
