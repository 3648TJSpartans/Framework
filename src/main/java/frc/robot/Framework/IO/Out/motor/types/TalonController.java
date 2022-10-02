package frc.robot.framework.io.out.motor.types;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.framework.io.out.motor.MotorBase;
import frc.robot.framework.util.CommandMode;

public class TalonController implements MotorBase {
    private TalonSRX controller;

    public TalonController(int port) {
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

    @Override
    public void setPID(double kP, double kI, double kD, double kF) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setVoltage(double voltage) {
        // TODO Auto-generated method stub
        
    }
}