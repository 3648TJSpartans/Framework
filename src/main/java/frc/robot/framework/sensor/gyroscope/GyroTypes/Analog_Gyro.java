package frc.robot.framework.sensor.gyroscope.GyroTypes;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.framework.sensor.gyroscope.GyroBase;

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
    public double getGyroAccel(String axis) {
        return 0;
    }
    public double getGyroAngle(String axis) {
        return 0;
    }
    public double getGyroRate(String axis) {
        return 0;
    }
    public double getMagneticField(String axis) {
        return 0;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        // builder.setSmartDashboardType("Motor Controller");
        // builder.setActuator(false);
        builder.addDoubleProperty("GyroAngle", this::getGyroAngle, null);
        // builder.addDoubleProperty("Y", this::get, this::set);
        // builder.addDoubleProperty("X", this::get, this::set);
    }
}
