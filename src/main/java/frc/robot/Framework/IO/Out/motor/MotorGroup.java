package frc.robot.framework.io.out.motor;

import java.util.ArrayList;

import frc.robot.framework.util.CommandMode;

public class MotorGroup implements MotorBase {
    private ArrayList<MotorWrapper> motors = new ArrayList<>();

    public MotorGroup() {
    };

    public void addMotor(MotorWrapper newMotor) {
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
    public void setPID(double kP, double kI, double kD, double kF) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setVoltage(double voltage) {
        for (int i = 0; i < motors.size(); i++) {
            motors.get(i).setVoltage(voltage);
        }
        
    }
}