package frc.robot.framework.io.out.motor.types;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.framework.io.out.motor.MotorBase;
import frc.robot.framework.util.CommandMode;

public class SparkMaxController implements MotorBase{
    private CANSparkMax controller;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;

    public SparkMaxController(int port){
        controller = new CANSparkMax(port, MotorType.kBrushless);
        pidController = controller.getPIDController();
        encoder = controller.getEncoder();
    }

    public void set(double speed){
        controller.set(speed);
    };

    public void setInverted(boolean invert){
        controller.setInverted(invert);
    }

    @Override
    public void set(double setpoint, CommandMode mode) {
        if(mode == CommandMode.PERCENTAGE){
            set(setpoint);
        }else if(mode == CommandMode.VELOCITY){
            pidController.setReference(setpoint, ControlType.kVelocity);
            //System.out.println("Setpoint: "+setpoint+" | Actual: "+encoder.getVelocity());
        }else{

        }
    }

    @Override
    public void setPID(double kP, double kI, double kD, double kF) {
        pidController.setP(kP);
        SmartDashboard.putNumber("KP", kP);
        pidController.setI(kI);
        SmartDashboard.putNumber("KI", kI);
        pidController.setD(kD);
        SmartDashboard.putNumber("KD", kD);
        pidController.setFF(kF);
        SmartDashboard.putNumber("kF", kF);
    }

    public CANSparkMax getCANObject(){
        return controller;
    }
    public void setVoltage(double voltage){
        controller.setVoltage(voltage);
    }
}