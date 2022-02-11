package frc.robot.Framework.IO.Out.Sensors.SensorTypes.Gyroscopes.GyroTypes;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Gyroscopes.GyroBase;

public class ADXRS_450 implements GyroBase{
    ADXRS450_Gyro m_gyro;
    public ADXRS_450(){
        m_gyro = new ADXRS450_Gyro();
        
    }
    public ADXRS_450(String port){
        SPI.Port m_port = SPI.Port.valueOf(port);
        m_gyro = new ADXRS450_Gyro(m_port);
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
