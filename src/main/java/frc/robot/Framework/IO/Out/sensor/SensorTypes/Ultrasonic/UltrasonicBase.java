package frc.robot.framework.io.out.sensor.SensorTypes.Ultrasonic;

public interface UltrasonicBase {
    public double getRangeInches();
    public double getRangeMM();
    public int getEchoChannel();
}
