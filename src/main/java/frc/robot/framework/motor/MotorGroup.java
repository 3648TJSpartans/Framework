package frc.robot.framework.motor;

import java.util.ArrayList;

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
}