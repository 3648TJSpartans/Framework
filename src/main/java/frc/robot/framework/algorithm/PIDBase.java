package frc.robot.framework.algorithm;

import frc.robot.framework.util.CommandMode;

public interface PIDBase{
    public void setPID(double kP, double kI, double kD, double kF);

    public double getPowerOutput(double input, double reference, CommandMode mode);

    public double getLastOutput();
}