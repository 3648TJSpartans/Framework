package frc.robot.Framework.IO.In.Controllers.ControllerTypes;

import frc.robot.Framework.IO.In.Controllers.ControllerBase;
import edu.wpi.first.wpilibj.Joystick;
import java.util.Map;


public class LogitechGamepad implements ControllerBase{
    private Joystick controller;

    private Map<String, Integer> buttonIDs = Map.ofEntries(
        Map.entry("A", 1),
        Map.entry("B", 2),
        Map.entry("X", 3),
        Map.entry("Y", 4),
        Map.entry("LEFT_SHOULDER", 5),
        Map.entry("RIGHT_SHOULDER", 6),
        Map.entry("BACK", 7),
        Map.entry("RESTART", 8),
        Map.entry("LEFT_JOYSTICK_IN", 9),
        Map.entry("RIGHT_JOYSTICK_IN", 10)
    );
    private Map<String, Integer> axisIDs = Map.ofEntries(
        Map.entry("LEFT_JOYSTICK_X", 0),
        Map.entry("LEFT_JOYSTICK_Y", 1),
        Map.entry("LEFT_TRIGGER", 2),
        Map.entry("RIGHT_TRIGGER", 3),
        Map.entry("RIGHT_JOYSTICK_X", 4),
        Map.entry("RIGHT_JOYSTICK_Y", 5)
    );

    public LogitechGamepad(Integer port){
        controller = new Joystick(port);
    }

    public boolean getButton(String id){
        return controller.getRawButton(buttonIDs.get(id));
    }

    public double getAxis(String id){
        return controller.getRawAxis(axisIDs.get(id));
    }
}