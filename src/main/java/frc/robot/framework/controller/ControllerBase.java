package frc.robot.framework.controller;

import java.util.Map;

public interface ControllerBase{

    public boolean getButton(String buttonID);

    public double getAxis(String axisID);
    public Map<String, Integer> GetButtonMap();
    public Map<String, Integer> GetAxisMap();
}