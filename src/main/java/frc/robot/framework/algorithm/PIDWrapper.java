package frc.robot.framework.algorithm;

import org.w3c.dom.Element;

import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.encoder.SparkMaxEncoder;
import frc.robot.framework.motor.MotorBase;
import frc.robot.framework.motor.SparkMaxController;
import frc.robot.framework.util.CommandMode;

public class PIDWrapper implements PIDBase{
    private PIDBase pidController;
    private MotorBase motor;
    private EncoderBase encoder;
    private double kP, kI, kD, kF = 0;
    public double measuredValue=0;

    public PIDWrapper(Element element, MotorBase motor, EncoderBase encoder){
        
        kP=Double.parseDouble(element.getAttribute("kP"));
        kI=Double.parseDouble(element.getAttribute("kI"));
        kD=Double.parseDouble(element.getAttribute("kD"));
        kF=Double.parseDouble(element.getAttribute("kF"));

        switch (element.getAttribute("type")){
            case "sparkmax":
                if (!(motor instanceof SparkMaxController) && !(encoder instanceof SparkMaxEncoder)){
                    System.out.println("PIDWrapper: Sparkmax PID requires SparkMax encoder and SparkMax Motor");
                    return;
                }
                pidController = new SparkMaxPID(kP, kI, kD, kF, ((SparkMaxController)motor), ((SparkMaxEncoder)encoder));
                break;
            case "talonsrx":
                pidController = new SoftwarePID(kP,kI,kD,kF, motor, encoder);
                break;
            case "sparkpwm":
                pidController = new SoftwarePID(kP,kI,kD,kF, motor, encoder);
                break;
            default:
                return;
        }
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
    public void setReference(double value, CommandMode mode) {
        pidController.setReference(value, mode);        
    }
}