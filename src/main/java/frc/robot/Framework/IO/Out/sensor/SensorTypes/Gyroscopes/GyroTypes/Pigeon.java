package frc.robot.framework.io.out.sensor.SensorTypes.Gyroscopes.GyroTypes;

import com.ctre.phoenix.sensors.WPI_PigeonIMU;

import frc.robot.framework.io.out.sensor.SensorTypes.Gyroscopes.GyroBase;

public class Pigeon implements GyroBase{
    WPI_PigeonIMU m_gyro;
    public Pigeon(int port){
        m_gyro = new WPI_PigeonIMU(port);
    }
    public void test(){
        
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
