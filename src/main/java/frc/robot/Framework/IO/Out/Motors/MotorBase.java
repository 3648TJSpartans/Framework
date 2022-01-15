package frc.robot.Framework.IO.Out.Motors;

import frc.robot.Framework.Util.CommandMode;

public interface MotorBase{
    public void set(double speed);

    public void set(double setpoint, CommandMode mode);

    public void setInverted(boolean invert);

    public void setPID(double kP, double kI, double kD, double kF);
}