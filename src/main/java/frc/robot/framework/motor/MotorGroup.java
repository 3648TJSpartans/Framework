package frc.robot.framework.motor;

import java.util.ArrayList;

import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;

public class MotorGroup implements MotorBase {
    private ArrayList<MotorBase> motors = new ArrayList<>();

    public MotorGroup() {
    };

    public void addMotor(MotorBase newMotor) {
        motors.add(newMotor);
    }

    public void set(double speed) {
        for (int i = 0; i < motors.size(); i++) {
            motors.get(i).set(speed);
        }
    }

    public void setInverted(boolean invert) {
        for (int i = 0; i < motors.size(); i++) {
            motors.get(i).setInverted(invert);
        }
    }

    @Override
    public void set(double setpoint, CommandMode mode) {
        for (int i = 0; i < motors.size(); i++) {
            motors.get(i).set(setpoint, mode);
        }
    }

    @Override
    public void setVoltage(double voltage) {
        for (int i = 0; i < motors.size(); i++) {
            motors.get(i).setVoltage(voltage);
        }
        
    }

    @Override
    public void setPID(double kP, double kI, double kD, double kF) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isCANEncoder() {
        return motors.get(0).isCANEncoder();
    }

    @Override
    public EncoderBase getEncoder(){
        return motors.get(0).getEncoder();
    }
}