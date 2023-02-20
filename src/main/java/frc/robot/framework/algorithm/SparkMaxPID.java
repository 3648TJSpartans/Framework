package frc.robot.framework.algorithm;

import org.w3c.dom.Element;

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
        setPID(kP, kI, kD, kF);
    }

    public SparkMaxPID(Element element, SparkMaxController motor) {
        double kP = 0, kI = 0, kD = 0, kF = 0;

        try{
        if(!element.getAttribute("kp").isEmpty()){
            kP=Double.parseDouble(element.getAttribute("kp"));
        }
        if(!element.getAttribute("ki").isEmpty()){
            kI=Double.parseDouble(element.getAttribute("ki"));
        }
        if(!element.getAttribute("kd").isEmpty()){
            kD=Double.parseDouble(element.getAttribute("kd"));
        }
        if(!element.getAttribute("kf").isEmpty()){
            kF=Double.parseDouble(element.getAttribute("kf"));
        }
    } catch (Exception NumberFormatException){
        throw new NumberFormatException("SparkMaxPID Invalid Formats kP: "+kP+" kI: "+kI+" kD: "+kD+" kF: "+ kF);
    }
        
        
        

        this.pid=motor.getPidController();
        this.motor=motor;
        setPID(kP, kI, kD, kF);
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
