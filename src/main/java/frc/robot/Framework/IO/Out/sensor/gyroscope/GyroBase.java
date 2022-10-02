package frc.robot.framework.io.out.sensor.gyroscope;

public interface GyroBase{
    public double getGyroAccel(String axis);
    public double getGyroAngle(String axis);
    public double getGyroRate(String axis);
    public double getMagneticField(String axis);
    public double getGyroRate();
    public double getGyroAngle();
}