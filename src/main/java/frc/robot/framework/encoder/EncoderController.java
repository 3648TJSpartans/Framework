package frc.robot.framework.encoder;

public class EncoderController {
    protected boolean inverted = false;
    
    public void setInverted(Boolean inverted){
        this.inverted=inverted;
    }

    public double invertMath(double reference){
        return inverted ? reference*-1: reference;
    }
}
