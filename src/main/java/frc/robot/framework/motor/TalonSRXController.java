package frc.robot.framework.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;

public class TalonSRXController implements MotorBase, EncoderBase{
    private TalonSRX controller;

    public TalonSRXController(int port) {
        controller = new TalonSRX(port);
        controller.set(ControlMode.PercentOutput, 0);
    }

    public void set(double speed) {
        // System.out.println("Talon commanded to: "+speed);
        controller.set(ControlMode.PercentOutput, speed);
    };

    public void setInverted(boolean invert) {
        controller.setInverted(invert);
    }

    @Override
    public void set(double setpoint, CommandMode mode) {
        // TODO Auto-generated method stub

    }

    public void setPID(double kP, double kI, double kD, double kF) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setVoltage(double voltage) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isCANEncoder() {
        return true;
    }

    @Override
    public EncoderBase getEncoder(){
        return this;
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
    public void reset() {
        // TODO Auto-generated method stub
        
    }
}