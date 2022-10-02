package frc.robot.framework.io.out.sensor.Ultrasonic;

public interface UltrasonicBase {
    public double getRangeInches();
    public double getRangeMM();
    public int getEchoChannel();
}
