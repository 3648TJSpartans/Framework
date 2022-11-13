package frc.robot.framework.motor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.framework.encoder.EncoderBase;


public class SparkMaxController extends MotorController implements MotorBase, EncoderBase {
    private CANSparkMax controller;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;

    public SparkMaxController(int port){
        controller = new CANSparkMax(port, MotorType.kBrushless);
        pidController = controller.getPIDController();
        encoder = controller.getEncoder();
    }

    public void setPower(double power){
        controller.set(power);
    };

    public CANSparkMax getCanSparkMax(){
        return controller;
    }


    public void setVoltage(double voltage){
        controller.setVoltage(voltage);
    }

    @Override
    public EncoderBase getEncoder(){
        return this;
    }

    public SparkMaxPIDController getPidController(){
        return pidController;
    }

    @Override
    public int getTicks() {
        return 0;
    }

    @Override
    public double getVelocity() {
        return encoder.getVelocity();
    }

    @Override
    public double getPosition() {
        return encoder.getPosition();
    }

    @Override
    public void setDistancePerPulse(double factor){
        encoder.setPositionConversionFactor(factor);
        encoder.setVelocityConversionFactor(factor);
    }

    @Override
    public void resetEncoder(){
        encoder.setPosition(0);
    }
}