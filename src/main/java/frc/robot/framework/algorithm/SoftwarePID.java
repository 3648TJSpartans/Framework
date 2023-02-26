package frc.robot.framework.algorithm;

import org.w3c.dom.Element;

import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.motor.MotorBase;
import frc.robot.framework.util.CommandMode;

public class SoftwarePID implements PIDBase{
    private double kP, kI, kD, kF = 0;
    private double lastOutput=0;
    public double measuredValue=0;
    private double previousErrors[] = new double[5]; 

    public SoftwarePID(double kP, double kI, double kD, double kF){
        setPID(kP, kI, kD, kF);
    }
    
    @Override
    public void setPID(double kP, double kI, double kD, double kF){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }

    private double calculateOutput(double measuredValue, double desiredValue){
        double error = desiredValue - measuredValue;
        double sumError=error;
        for (int i=previousErrors.length-1; i> 0; i--) {
            previousErrors[i]=previousErrors[i-1];
            sumError+=previousErrors[i];
        }
        previousErrors[0]=error;

        double p = error * kP;

        double d = (previousErrors[0] - previousErrors[1])*kD;

        double i = sumError/previousErrors.length*kI;

        double f = desiredValue * kF;
        lastOutput= p + i + d + f;
        lastOutput = Math.min(1.0,Math.max(-1.0,lastOutput));
        return lastOutput;
    }

    @Override
    public double getPowerOutput(double measuredValue, double desiredValue, CommandMode mode) {
        double output=0;
        switch (mode){
            case POSITION:
                output=calculateOutput(measuredValue, desiredValue);
                break;
            case VELOCITY:
                output=calculateOutput(measuredValue, desiredValue);
                break;
            case PERCENTAGE:
                output=desiredValue;
                break;          
        }
        return output;
    }

    @Override
    public double getLastOutput(){
        return lastOutput;
    }

}