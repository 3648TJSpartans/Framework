package frc.robot.framework.motor;

import java.util.ArrayList;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.framework.algorithm.PIDBase;
import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;

public class MotorGroup extends MotorController{
    private ArrayList<MotorBase> motors = new ArrayList<>();

    public MotorGroup() {
    };

    public void addMotor(MotorBase newMotor) {
        motors.add(newMotor);
    }

    @Override
    public void setReference(double reference, CommandMode mode) {
        if (inverted)
            reference*=-1.0;
        for (int i = 0; i < motors.size(); i++) {
                motors.get(i).setReference(reference, mode);
        }
    }

    @Override
    public void set(double speed){
        motors.get(0).set(speed);
    }

    @Override
    public double get() {
        return motors.get(0).get();
    }

    @Override
    public void disable() {
        motors.get(0).disable();
    }

    @Override
    public void stopMotor() {
        motors.get(0).stopMotor();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        motors.get(0).initSendable(builder);
    }
}