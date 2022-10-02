package frc.robot.framework.io.in.controller;

public interface ControllerBase{

    public boolean getButton(String buttonID);

    public double getAxis(String axisID);
}