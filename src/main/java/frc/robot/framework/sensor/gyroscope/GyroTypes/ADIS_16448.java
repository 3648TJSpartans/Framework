package frc.robot.framework.sensor.gyroscope.GyroTypes;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.ADIS16448_IMU.CalibrationTime;
//import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.ADIS16448_IMU.IMUAxis;
import frc.robot.framework.sensor.gyroscope.GyroBase;

public class ADIS_16448 implements GyroBase{
    public static ADIS16448_IMU m_gyro;
    public ADIS_16448(){
        m_gyro = new ADIS16448_IMU();
    }

    public ADIS_16448(String yaw_axis, String cal_time) {
        IMUAxis m_yaw_axis = IMUAxis.valueOf(yaw_axis);
        CalibrationTime m_cal_time = CalibrationTime.valueOf(cal_time);
        m_gyro = new ADIS16448_IMU(m_yaw_axis, SPI.Port.kMXP, m_cal_time);
    }

    public double getGyroAccel(String axis) {
        if (axis == "Z" || axis == "z") {
            return m_gyro.getAccelZ();
        } else if (axis == "X" || axis == "x") {
            return m_gyro.getAccelX();
        } else {
            return m_gyro.getAccelY();
        }
    }

    public double getGyroAngle(String axis) {
        if (axis == "Z" || axis == "z") {
            return m_gyro.getGyroAngleZ();
        } else if (axis == "X" || axis == "x") {
            return m_gyro.getGyroAngleX();
        } else {
            return m_gyro.getGyroAngleY();
        }
    }

    public double getGyroRate(String axis) {
        if (axis == "Z" || axis == "z") {
            return m_gyro.getGyroRateZ();
        } else if (axis == "X" || axis == "x") {
            return m_gyro.getGyroRateX();
        } else {
            return m_gyro.getGyroRateY();
        }
    }

    public double getMagneticField(String axis) {
        if (axis == "Z" || axis == "z") {
            return m_gyro.getMagneticFieldZ();
        } else if (axis == "X" || axis == "x") {
            return m_gyro.getMagneticFieldX();
        } else {
            return m_gyro.getMagneticFieldY();
        }
    }

    public double getGyroRate() {
        return m_gyro.getRate();
    }

    public double getGyroAngle() {
        return m_gyro.getAngle();
    }

    public double getGyroAngleX() {
        return getGyroAngle("x");
    }

    public double getGyroAngleY() {
        return getGyroAngle("y");
    }

    public double getGyroAngleZ() {
        return getGyroAngle("z");
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        // builder.setSmartDashboardType("Motor Controller");
        // builder.setActuator(false);
        builder.addDoubleProperty("X", this::getGyroAngleX, null);
        builder.addDoubleProperty("Y", this::getGyroAngleY, null);
        builder.addDoubleProperty("Z", this::getGyroAngleZ, null);
        // builder.addDoubleProperty("Y", this::get, this::set);
        // builder.addDoubleProperty("X", this::get, this::set);
    }
}
