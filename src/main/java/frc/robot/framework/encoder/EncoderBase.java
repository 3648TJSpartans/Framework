package frc.robot.framework.encoder;

public interface EncoderBase{
    public int getTicks();
    public double getVelocity();
    public double getPosition();
    public void setDistancePerPulse(double factor);
    public void resetEncoder();
}