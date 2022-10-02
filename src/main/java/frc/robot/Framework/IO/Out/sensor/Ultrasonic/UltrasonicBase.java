package frc.robot.framework.io.out.sensor.ultrasonic;

public interface UltrasonicBase {
    public double getRangeInches();
    public double getRangeMM();
    public int getEchoChannel();
}
