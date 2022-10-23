package frc.robot.framework.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.Element;

import frc.robot.framework.encoder.EncoderWrapper;

public class PID{
    private double kP, kI, kD, kF = 0;
    private double lastOutput=0;
    public double measuredValue=0;
    private double previousErrors[] = new double[5]; 

    public PID(double kP, double kI, double kD, double kF){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }

    public PID(Element element ){
        kP=Double.parseDouble(element.getAttribute("kP"));
        kI=Double.parseDouble(element.getAttribute("kI"));
        kD=Double.parseDouble(element.getAttribute("kD"));
        kF=Double.parseDouble(element.getAttribute("kF"));
    }

    public double calculateOutput(double measuredValue, double desiredValue){
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
        return lastOutput;
    }

    public double getOutput(){
        return lastOutput;
    }
}