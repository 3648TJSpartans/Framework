package frc.robot.framework.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;

public class TalonSRXController extends MotorController implements EncoderBase{
    private TalonSRX controller;

    public TalonSRXController(int port) {
        controller = new TalonSRX(port);
        controller.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void setReference(double reference, CommandMode mode) {
        if (inverted)
            reference*=-1;
        
        this.mode=mode;
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
    public int getTicks() {
        
        return 0;
    }

    @Override
    public double getVelocity() {
        
        return 0;
    }

    @Override
    public double getPosition() {
        
        return 0;
    }

    @Override
    public void setDistancePerPulse(double factor) {
                
    }

    @Override
    public void resetEncoder() {
                
    }

    @Override
    public void setPosition(double position) {
                
    }

    public double getAbsolutePosition() {
        return 0;
    }

    

    @Override
    public void set(double speed){
        controller.set(TalonSRXControlMode.PercentOutput, speed);
    }

    @Override
    public double get() {
        return controller.getMotorOutputPercent();
    }

    @Override
    public void disable() {
        controller.set(TalonSRXControlMode.Disabled, 0);
    }

    @Override
    public void stopMotor() {
        controller.set(TalonSRXControlMode.PercentOutput, 0);
    }


    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Motor Controller");
        builder.setActuator(true);
        builder.setSafeState(this::disable);
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}