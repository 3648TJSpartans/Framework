package frc.robot.framework.motor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;


public class SparkMaxController extends MotorController implements MotorBase, EncoderBase {
    private CANSparkMax controller;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;

    public SparkMaxController(int port){
        controller = new CANSparkMax(port, MotorType.kBrushless);
        controller.restoreFactoryDefaults();
        pidController = controller.getPIDController();
        encoder = controller.getEncoder();
        encoder.setPosition(0);
    }

    @Override
    public void setInverted(boolean inverted)
    {
        super.setInverted(inverted);
    }

    public void setReference(double reference, CommandMode mode){
        if (inverted)
        reference*=-1;
        switch (mode) {
            case PERCENTAGE:
                pidController.setReference(reference, ControlType.kDutyCycle);
                //controller.set(output);
                break;
            case VELOCITY:
                pidController.setReference(reference, ControlType.kVelocity);
                break;
            case POSITION:
                pidController.setReference(reference, ControlType.kPosition);
                break;
            default:
                System.out.println("SparkMaxController - Unknown commandmode:"+mode);
                break;
        }
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