package frc.robot.framework.motor;

import frc.robot.framework.algorithm.PIDBase;
import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;

public class MotorController implements MotorBase{
    protected boolean inverted = false;
    protected CommandMode mode = CommandMode.PERCENTAGE;
    protected EncoderBase encoder;
    protected PIDBase pid;
    
    public void setInverted(Boolean inverted){
        this.inverted=inverted;
    }

    public boolean getInverted(){
        return inverted;
    }

    public void setCommandMode(CommandMode mode) {
        this.mode=mode;
    }

    public double getPosition(){
        if (encoder !=null)
            return encoder.getPosition();
        else
            throw new UnsupportedOperationException("MotorController: encoder is null. Can't getPosition");
    }

    public void setPID(double kP, double kI, double kD, double kF) {
        if (pid !=null)
            pid.setPID(kP, kI, kD, kF);
        else
            throw new UnsupportedOperationException("MotorController: pid is null. Can't setPID");
    }

    
    public PIDBase getPID(){
        if (pid !=null)
           return pid;
        else
            throw new UnsupportedOperationException("MotorController: pid is null. Can't setPID");
    }

    public EncoderBase getEncoder() {
        if (encoder !=null)
            return encoder;
        else
            throw new UnsupportedOperationException("MotorController: encoder is null. Can't getEncoder");
    }

    public boolean hasPID(){
        return pid !=null;
    }

    public boolean hasEncoder() {
        return encoder !=null;
    }

    public double getVelocity() {
        if (encoder !=null)
            return encoder.getVelocity();
        else
            throw new UnsupportedOperationException("MotorController: encoder is null. Can't getVelocity");
    }

    @Override
    public void setReference(double reference, CommandMode mode) {
        throw new UnsupportedOperationException("Can't call setReference on MotorController.java. Please use a subclass");
    }

    @Override
    public void setInverted(boolean invert) {
        inverted=invert;
    }
}
