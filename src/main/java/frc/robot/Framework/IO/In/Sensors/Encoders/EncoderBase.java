package frc.robot.Framework.IO.In.Sensors.Encoders;

public interface EncoderBase{
    public int getTicks();
    public double getVelocity();
    public double getPosition();
    public void setDistancePerPulse(double factor);
    public void reset();
}