package frc.robot.framework.motor;

import java.util.ArrayList;

import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;

public class MotorGroup extends MotorController implements MotorBase {
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
    public void setInverted(boolean inverted)
    {
        super.setInverted(inverted);
    }

    @Override
    public EncoderBase getEncoder(){
        for (MotorBase motorBase : motors) {
            if (motorBase.getEncoder() != null)
                return motorBase.getEncoder();
        }
        return null;
    }
    
}