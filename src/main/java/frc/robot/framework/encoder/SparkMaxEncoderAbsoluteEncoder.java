package frc.robot.framework.encoder;

import com.revrobotics.AbsoluteEncoder;

import edu.wpi.first.util.sendable.SendableBuilder;


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
    public double getAbsolutePosition() {
        return encoder.getPosition()-encoder.getZeroOffset();
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
        encoder.setInverted(inverted);   
    }

    @Override
    public void setPosition(double position) {
        encoder.setZeroOffset(position);       
    }
    
    public void setZeroOffset(double offset){
        encoder.setZeroOffset(offset);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        //builder.setSmartDashboardType("Encoder");
        // builder.setActuator(true);
        // builder.setSafeState(this::disable);
        builder.addDoubleProperty("Pos", this::getPosition, this::setPosition);
        builder.addDoubleProperty("AbsPos", this::getAbsolutePosition, null);
        builder.addDoubleProperty("Vel", this::getVelocity, null);
    }
}