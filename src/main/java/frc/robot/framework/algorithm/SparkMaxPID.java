package frc.robot.framework.algorithm;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import frc.robot.framework.util.CommandMode;

public class SparkMaxPID implements PIDBase{

    private SparkMaxPIDController controller;

    public SparkMaxPID(double kP, double kI, double kD, double kF, SparkMaxPIDController controller) {
        this.controller=controller;
    }
    
    @Override
    public void setPID(double kP, double kI, double kD, double kF) {
        controller.setP(kP);
        controller.setI(kI);
        controller.setD(kD);
        controller.setFF(kF);
    }

    @Override
    public void setReference(double value, CommandMode mode) {
        ControlType ctrlType;
        switch (mode){
            case PERCENTAGE:
                ctrlType = ControlType.kDutyCycle;
                break;
            case POSITION:
                ctrlType= ControlType.kPosition;
                break;
            case VELOCITY:
                ctrlType = ControlType.kVelocity;
                break;
            default:
                return;
        }
        controller.setReference(value, ctrlType);        
    }
    
}
