package frc.robot.framework.encoder;

import com.revrobotics.AbsoluteEncoder;


public class SparkMaxEncoderAbsoluteEncoder extends EncoderController implements EncoderBase{

    private AbsoluteEncoder encoder;

    public SparkMaxEncoderAbsoluteEncoder(AbsoluteEncoder absoluteEncoder){
        this.encoder=absoluteEncoder;
    }

    @Override
    public int getTicks() {
        throw new UnsupportedOperationException("SparkMaxEncoderAbsoluteEncoder does not support getTicks()");
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
        encoder.setZeroOffset(getPosition());        
    }

    @Override
    public void setInverted(boolean inverted) {
        super.setInverted(inverted);   
    }

    @Override
    public void setPosition(double position) {
        encoder.setZeroOffset(position);       
    }  

}