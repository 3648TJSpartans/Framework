package frc.robot.framework.encoder;

import com.revrobotics.RelativeEncoder;

import frc.robot.framework.motor.MotorBase;

public class NeoEncoder implements EncoderBase{

    private RelativeEncoder encoder;

    public NeoEncoder(MotorBase spark){
        //encoder = new CANEncoder(((SparkMaxController)spark).getCANObject());
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

    public void setDistancePerPulse(double factor){
        encoder.setPositionConversionFactor(factor);
        encoder.setVelocityConversionFactor(factor);
    }

    public void reset(){
        
    }
    
}