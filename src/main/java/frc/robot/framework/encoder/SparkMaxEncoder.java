package frc.robot.framework.encoder;

import com.revrobotics.RelativeEncoder;


public class SparkMaxEncoder extends EncoderController implements EncoderBase{

    private RelativeEncoder encoder;

    public SparkMaxEncoder(RelativeEncoder relativeEncoder){
        this.encoder=relativeEncoder;
    }

    @Override
    public int getTicks() {
        return encoder.getCountsPerRevolution();
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
        encoder.setVelocityConversionFactor(factor / 60);
    }

    @Override
    public void resetEncoder() {
        encoder.setPosition(0);        
    }

    @Override
    public void setInverted(boolean inverted) {
        super.setInverted(inverted);
        
    }

    @Override
    public void setPosition(double position) {
        // TODO Auto-generated method stub
        
    }

}