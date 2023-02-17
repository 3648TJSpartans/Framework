package frc.robot.framework.encoder;

import frc.robot.framework.util.CommandMode;

public class EncoderController {
    protected boolean inverted = false;
    
    public void setInverted(Boolean inverted){
        this.inverted=inverted;
    }
}
