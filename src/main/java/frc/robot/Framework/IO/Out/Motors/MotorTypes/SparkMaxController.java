package frc.robot.Framework.IO.Out.Motors.MotorTypes;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Framework.IO.Out.Motors.MotorBase;
import frc.robot.Framework.Util.CommandMode;

public class SparkMaxController implements MotorBase{
    private CANSparkMax controller;
    private CANPIDController pidController;
    private CANEncoder encoder;

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
}