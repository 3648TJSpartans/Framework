package frc.robot.framework.encoder;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.util.sendable.SendableBuilder;


public class SparkMaxEncoderRelativeEncoder extends EncoderController implements EncoderBase{

    private RelativeEncoder encoder;

    public SparkMaxEncoderRelativeEncoder(RelativeEncoder relativeEncoder){
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
    public double getAbsolutePosition() {
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
        encoder.setInverted(inverted);
    }

    @Override
    public void setPosition(double position) {
        encoder.setPosition(position);       
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