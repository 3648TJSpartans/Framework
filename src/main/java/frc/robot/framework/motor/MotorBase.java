package frc.robot.framework.motor;

import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;

public interface MotorBase{
    public void setReference(double reference, CommandMode mode);
    public void setInverted(boolean invert);
    public boolean getInverted();
    //public double getLastOutput();

    // public void setPID(double kP, double kI, double kD, double kF);
    // public void setCommandMode(CommandMode mode);

    // public boolean hasEncoder();
    // public boolean isCANEncoder();
    // public void setEncoder(EncoderBase encoder);
    // public EncoderBase getEncoder();

    // public int getTicks();
    // public double getVelocity();
    // public double getPosition();
    // public void setDistancePerPulse(double factor);
}