package frc.robot.framework.encoder;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class DutyCycleEncoder extends EncoderController implements EncoderBase{

    edu.wpi.first.wpilibj.DutyCycleEncoder encoder;
    private double offset=0;
    private double lastPosition=0;

    public DutyCycleEncoder(int port){
        encoder=new edu.wpi.first.wpilibj.DutyCycleEncoder(port);
    }

    @Override
    public int getTicks() {
        return 0; //TODO: fix this  
    }

    @Override
    public double getVelocity() {
        return 0; //TODO: fix this
    }

    @Override
    public double getPosition() {
        return encoder.getAbsolutePosition();
    }

    public void setDistancePerPulse(double factor){
        encoder.setDistancePerRotation(factor);
    }

    public void resetEncoder(){
        encoder.reset();
    }

    @Override
    public void setInverted(boolean inverted) {
        super.setInverted(inverted);
        //TODO: fix this
    }

    @Override
    public void setPosition(double position) {
        //TODO: fix this
        offset=position;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Position", this::getPosition, this::setPosition);
        builder.addDoubleProperty("Velocity", this::getVelocity, null);
    }
}