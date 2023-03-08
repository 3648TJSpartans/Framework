package frc.robot.framework.sensor.gyroscope;

import edu.wpi.first.util.sendable.Sendable;

public interface GyroBase extends Sendable{
    public double getGyroAccel(String axis);
    public double getGyroAngle(String axis);
    public double getGyroRate(String axis);
    public double getMagneticField(String axis);
    public double getGyroRate();
    public double getGyroAngle();
}