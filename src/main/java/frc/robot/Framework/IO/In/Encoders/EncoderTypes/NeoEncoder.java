package frc.robot.Framework.IO.In.Encoders.EncoderTypes;

import com.revrobotics.CANEncoder;

import frc.robot.Framework.IO.In.Encoders.EncoderBase;
import frc.robot.Framework.IO.Out.Motors.MotorBase;
import frc.robot.Framework.IO.Out.Motors.MotorTypes.SparkMaxController;

public class NeoEncoder implements EncoderBase{

    private CANEncoder encoder;

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