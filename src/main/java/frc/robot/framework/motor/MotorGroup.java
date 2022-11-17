package frc.robot.framework.motor;

import java.util.ArrayList;

import frc.robot.framework.encoder.EncoderBase;

public class MotorGroup extends MotorController implements MotorBase {
    private ArrayList<MotorBase> motors = new ArrayList<>();

    public MotorGroup() {
    };

    public void addMotor(MotorBase newMotor) {
        motors.add(newMotor);
    }

    @Override
    public void setPower(double power) {
        if (inverted)
            power*=-1.0;
        for (int i = 0; i < motors.size(); i++) {
                motors.get(i).setPower(power);
        }
    }

    @Override
    public void setInverted(boolean inverted)
    {
        super.setInverted(inverted);
    }


    // @Override
    // public boolean isCANEncoder() {
    //     for (MotorBase motorBase : motors) {
    //         if (motorBase.isCANEncoder())
    //             return true;
    //     }
    //     return false;
    // }

    @Override
    public EncoderBase getEncoder(){
        for (MotorBase motorBase : motors) {
            if (motorBase.getEncoder() != null)
                return motorBase.getEncoder();
        }
        return null;
    }
    
}