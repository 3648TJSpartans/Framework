package frc.robot.framework.motor;

import frc.robot.framework.util.CommandMode;

public class MotorController {
    protected boolean inverted = false;
    protected CommandMode mode = CommandMode.PERCENTAGE;
    // public MotorController() {
    // }
    
    public void setInverted(Boolean inverted){
        this.inverted=inverted;
    }

    public void setCommandMode(CommandMode mode) {
        this.mode=mode;
    }
}
