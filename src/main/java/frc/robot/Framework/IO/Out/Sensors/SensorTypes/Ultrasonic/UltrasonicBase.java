package frc.robot.Framework.IO.Out.Sensors.SensorTypes.Ultrasonic;

public interface UltrasonicBase {
    public double getRangeInches();
    public double getRangeMM();
    public int getEchoChannel();
}
