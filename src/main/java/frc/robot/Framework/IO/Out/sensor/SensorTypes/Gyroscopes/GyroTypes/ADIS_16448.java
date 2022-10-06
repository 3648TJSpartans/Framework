package frc.robot.framework.io.out.sensor.SensorTypes.Gyroscopes.GyroTypes;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.ADIS16448_IMU.CalibrationTime;
//import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.ADIS16448_IMU.IMUAxis;
import frc.robot.framework.io.out.sensor.SensorTypes.Gyroscopes.GyroBase;

public class ADIS_16448 implements GyroBase{
    ADIS16448_IMU m_gyro;
    public ADIS_16448(){
        m_gyro = new ADIS16448_IMU();
    }
    public ADIS_16448(String yaw_axis,String cal_time){
        IMUAxis m_yaw_axis = IMUAxis.valueOf(yaw_axis);
        CalibrationTime m_cal_time = CalibrationTime.valueOf(cal_time);
        m_gyro = new ADIS16448_IMU(m_yaw_axis, SPI.Port.kMXP, m_cal_time);
    }
    public double getGyroAccel(String axis){
        if(axis == "Z"|| axis == "z"){
            return m_gyro.getAccelZ();
        }else if(axis == "X"|| axis == "x"){
            return m_gyro.getAccelX();
        }else{
            return m_gyro.getAccelY();
        }
    }
    public double getGyroAngle(String axis){
        if(axis == "Z"|| axis == "z"){
            return m_gyro.getGyroAngleZ();
        }else if(axis == "X"|| axis == "x"){
            return m_gyro.getGyroAngleX();
        }else{
            return m_gyro.getGyroAngleY();
        }
    }
    public double getGyroRate(String axis){
        if(axis == "Z"|| axis == "z"){
            return m_gyro.getGyroRateZ();
        }else if(axis == "X"|| axis == "x"){
            return m_gyro.getGyroRateX();
        }else{
            return m_gyro.getGyroRateY();
        }
    }
    public double getMagneticField(String axis){
        if(axis == "Z"|| axis == "z"){
            return m_gyro.getMagneticFieldZ();
        }else if(axis == "X"|| axis == "x"){
            return m_gyro.getMagneticFieldX();
        }else{
            return m_gyro.getMagneticFieldY();
        }
    }
    public double getGyroRate(){
        return m_gyro.getRate();
    }
    public double getGyroAngle(){
        return m_gyro.getAngle();
    }
}
