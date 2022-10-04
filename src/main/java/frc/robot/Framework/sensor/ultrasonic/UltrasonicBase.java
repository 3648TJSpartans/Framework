package frc.robot.framework.sensor.ultrasonic;

public interface UltrasonicBase {
    public double getRangeInches();
    public double getRangeMM();
    public int getEchoChannel();
}
