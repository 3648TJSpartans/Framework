package frc.robot.framework.algorithm;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import frc.robot.framework.motor.SparkMaxController;
import frc.robot.framework.util.CommandMode;

public class SparkMaxPID implements PIDBase{

    private SparkMaxPIDController pid;
    private SparkMaxController motor;

    public SparkMaxPID(double kP, double kI, double kD, double kF, SparkMaxController motor) {
        this.pid=motor.getPidController();
        this.motor=motor;
    }
    
    @Override
    public void setPID(double kP, double kI, double kD, double kF) {
        pid.setP(kP);
        pid.setI(kI);
        pid.setD(kD);
        pid.setFF(kF);
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
        pid.setReference(value, ctrlType);        
    }

    @Override
    public double getLastOutput(){
        return motor.getCanSparkMax().getAppliedOutput();
    }
    
}
