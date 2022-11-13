package frc.robot.framework.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;

public class TalonSRXController extends MotorController implements MotorBase, EncoderBase{
    private TalonSRX controller;

    public TalonSRXController(int port) {
        controller = new TalonSRX(port);
        controller.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void setPower(double power) {
        if (inverted)
            power*=-1;
        switch (mode) {
            case PERCENTAGE:
                controller.set(TalonSRXControlMode.PercentOutput,power);
                break;        
            case POSITION:
                controller.set(TalonSRXControlMode.Position,power);
                break;
            case VELOCITY:
                controller.set(TalonSRXControlMode.Velocity,power);
                break;
        }
    }

    public void setReferencePoint(CommandMode mode, double reference) {
        if (inverted)
            reference*=-1;
        this.mode=mode;
        switch (this.mode) {
            case PERCENTAGE:
                controller.set(TalonSRXControlMode.PercentOutput,reference);
                break;        
            case POSITION:
                controller.set(TalonSRXControlMode.Position,reference);
                break;
            case VELOCITY:
                controller.set(TalonSRXControlMode.Velocity,reference);
                break;
        }
    }

    @Override
    public EncoderBase getEncoder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getTicks() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getVelocity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getPosition() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setDistancePerPulse(double factor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resetEncoder() {
        // TODO Auto-generated method stub
        
    }
}