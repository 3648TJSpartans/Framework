package frc.robot.framework.algorithm;

import org.w3c.dom.Element;

import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.motor.MotorBase;
import frc.robot.framework.motor.SparkMaxController;
import frc.robot.framework.util.CommandMode;

public class PIDWrapper implements PIDBase{
    public PIDBase pidController;
    private double kP, kI, kD, kF = 0;
    public double measuredValue=0;

    public PIDWrapper(Element element){
        try{
        kP=element.hasAttribute("kp") ? Double.parseDouble(element.getAttribute("kp")) : 0;
        kI=element.hasAttribute("ki") ? Double.parseDouble(element.getAttribute("ki")) : 0;
        kD=element.hasAttribute("kd") ? Double.parseDouble(element.getAttribute("kd")) : 0;
        kF=element.hasAttribute("kf") ? Double.parseDouble(element.getAttribute("kf")) : 0;
        }catch (Exception NumberFormatException){
            throw new NumberFormatException("PIDWrapper Invalid Formats kP: "+kP+" kI: "+kI+" kD: "+kD+" kF: "+ kF);
        }
        pidController = new SoftwarePID(kP,kI,kD,kF);
        pidController.setPID(kP,kI,kD,kF);
    }

    @Override
    public void setPID(double kP, double kI, double kD, double kF){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }


    public double getLastOutput() {
        return pidController.getLastOutput();
    }

    @Override
    public double getPowerOutput(double input, double reference, CommandMode mode){
        return pidController.getPowerOutput(input, reference, mode);        
    }
}