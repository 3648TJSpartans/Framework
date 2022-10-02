package frc.robot.framework.io.in.controller;

import edu.wpi.first.wpilibj.Joystick;

import java.util.Map;


public class PSGamepad implements ControllerBase{
    private Joystick controller;

    private Map<String, Integer> buttonIDs = Map.ofEntries(
        Map.entry("SQUARE", 1),
        Map.entry("X", 2),
        Map.entry("CIRCLE", 3),
        Map.entry("TRIANGLE", 4),
        Map.entry("LEFT_SHOULDER", 5),
        Map.entry("RIGHT_SHOULDER", 6),
        Map.entry("LEFT_TRIGGER", 7),
        Map.entry("RIGHT_TRIGGER", 8),
        Map.entry("SHARE", 9),
        Map.entry("OPTIONS", 10),
        Map.entry("LEFT_JOYSTICK_IN", 11),
        Map.entry("RIGHT_JOYSTICK_IN", 12),
        Map.entry("PS_BUTTON", 9),
        Map.entry("TOUCHPAD", 10)
    );
    private Map<String, Integer> axisIDs = Map.ofEntries(
        Map.entry("LEFT_JOYSTICK_X", 0),
        Map.entry("LEFT_JOYSTICK_Y", 1),
        Map.entry("RIGHT_JOYSTICK_X", 2),
        Map.entry("LEFT_TRIGGER", 3),
        Map.entry("RIGHT_TRIGGER", 4),
        Map.entry("RIGHT_JOYSTICK_Y", 5)
    );

    public PSGamepad(Integer port){
        controller = new Joystick(port);
    }

    public boolean getButton(String id){
        return controller.getRawButton(buttonIDs.get(id));
    }

    public double getAxis(String id){
        return controller.getRawAxis(axisIDs.get(id));
    }
    public int getPOV(){
        return controller.getPOV();
    }
}