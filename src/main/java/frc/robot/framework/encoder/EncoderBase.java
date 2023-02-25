package frc.robot.framework.encoder;

import edu.wpi.first.util.sendable.Sendable;

public interface EncoderBase extends Sendable{
    public int getTicks();
    public double getVelocity();
    public double getPosition();
    public double getAbsolutePosition();
    public void setDistancePerPulse(double factor);
    public void resetEncoder();
    public void setInverted(boolean inverted);
    public void setPosition(double position);
}