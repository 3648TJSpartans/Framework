package frc.robot.framework.io.in.encoder;

import com.revrobotics.RelativeEncoder;

import frc.robot.framework.io.out.motor.MotorBase;

import com.revrobotics.CANSparkMax;
public class SparkMaxEncoder implements EncoderBase{

    private RelativeEncoder encoder;

    public SparkMaxEncoder(MotorBase sparkMax){
        encoder = ((CANSparkMax)sparkMax).getEncoder();
    }

    @Override
    public int getTicks() {
        // TODO Auto-generated method stub
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
    public void setDistancePerPulse(double factor) {
        encoder.setPositionConversionFactor(factor);
        encoder.setVelocityConversionFactor(factor);
    }

    @Override
    public void reset() {
        
    }
    
}
