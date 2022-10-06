package frc.robot.framework.io.in.encoder;

public interface EncoderBase{
    public int getTicks();
    public double getVelocity();
    public double getPosition();
    public void setDistancePerPulse(double factor);
    public void reset();
}