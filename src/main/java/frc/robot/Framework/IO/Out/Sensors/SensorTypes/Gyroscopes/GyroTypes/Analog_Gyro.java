package frc.robot.Framework.IO.Out.Sensors.SensorTypes.Gyroscopes.GyroTypes;

import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Gyroscopes.GyroBase;

public class Analog_Gyro implements GyroBase{
    Analog_Gyro m_gyro;
    public Analog_Gyro(){
        m_gyro = new Analog_Gyro();
    }
    public double getGyroRate(){
        return m_gyro.getRate();
    }
    public double getGyroAngle(){
        return m_gyro.getAngle();
    }
    public double getRate(){
        return m_gyro.getGyroRate();
    }
    public double getAngle(){
        return m_gyro.getGyroAngle();
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
