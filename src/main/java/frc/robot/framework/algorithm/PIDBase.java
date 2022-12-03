package frc.robot.framework.algorithm;

import frc.robot.framework.util.CommandMode;

public interface PIDBase{
    public void setPID(double kP, double kI, double kD, double kF);

    public void setReference(double value, CommandMode mode);

    public double getLastOutput();
}