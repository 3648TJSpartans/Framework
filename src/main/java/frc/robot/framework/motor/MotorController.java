package frc.robot.framework.motor;

import frc.robot.framework.util.CommandMode;

public class MotorController {
    protected boolean inverted = false;
    protected CommandMode mode = CommandMode.PERCENTAGE;
    
    public void setInverted(Boolean inverted){
        this.inverted=inverted;
    }

    public boolean getInverted(){
        return inverted;
    }

    public void setCommandMode(CommandMode mode) {
        this.mode=mode;
    }
}
