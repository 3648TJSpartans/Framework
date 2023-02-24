package frc.robot.framework.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;

public class TalonSRXController extends MotorController implements MotorBase, EncoderBase{
    private TalonSRX controller;

    public TalonSRXController(int port) {
        controller = new TalonSRX(port);
        controller.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void setReference(double reference, CommandMode mode) {
        if (inverted)
            reference*=-1;
        switch (mode) {
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
    public void setInverted(boolean inverted)
    {
        super.setInverted(inverted);
    }

    public void setReference(CommandMode mode, double reference) {
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

    @Override
    public void setPosition(double position) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        //builder.setSmartDashboardType("Motor Controller");
        // builder.setActuator(true);
        // builder.setSafeState(this::disable);
        builder.addDoubleProperty("Position", this::getPosition, null);
        builder.addDoubleProperty("Velocity", this::getVelocity, null);
    }
}