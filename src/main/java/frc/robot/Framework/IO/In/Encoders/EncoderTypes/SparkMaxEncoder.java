package frc.robot.Framework.IO.In.Encoders.EncoderTypes;

import frc.robot.Framework.IO.In.Encoders.EncoderBase;
import frc.robot.Framework.IO.Out.Motors.MotorBase;

import com.revrobotics.RelativeEncoder;
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
