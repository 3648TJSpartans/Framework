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
    private CommandMode mode = CommandMode.PERCENTAGE;
    private MotorBase motor;
    private EncoderBase encoder;

    public SoftwarePID(double kP, double kI, double kD, double kF, MotorBase motor, EncoderBase encoder){
        setPID(kP, kI, kD, kF);
        this.motor=motor;
        this.encoder = encoder;
    }
    
    public SoftwarePID(MotorBase motor, EncoderBase encoder){
        this.motor=motor;
        this.encoder=encoder;
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
        lastOutput = Math.max(1.0,Math.min(-1.0,lastOutput));
        return lastOutput;
    }

    @Override
    public void setReference(double value, CommandMode mode) {
        double currentValue;
        switch (mode){
            case POSITION:
                currentValue=encoder.getPosition();
                break;
            case VELOCITY:
                currentValue=encoder.getVelocity();
                break;
            case PERCENTAGE:
                motor.setPower(value);
                return;          
            default:
                System.out.println("Invalid command mode :"+mode.toString()+" in SoftwarePID");
                return;
        }
        motor.setPower(calculateOutput(currentValue, value));
    }
}