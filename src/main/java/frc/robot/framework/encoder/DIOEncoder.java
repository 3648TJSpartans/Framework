package frc.robot.framework.encoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class DIOEncoder extends EncoderController implements EncoderBase{

    Encoder encoder;

    public DIOEncoder(int portOne, int portTwo, boolean inverted, EncodingType type){
        encoder = new Encoder(portOne, portTwo, inverted, type);
        encoder.setDistancePerPulse(1);
    }

    @Override
    public int getTicks() {
        return encoder.get();
    }

    @Override
    public double getVelocity() {
        return encoder.getRate();
    }

    @Override
    public double getPosition() {
        return encoder.getDistance();
    }

    public void setDistancePerPulse(double factor){
        encoder.setDistancePerPulse(factor);
    }

    public void resetEncoder(){
        encoder.reset();
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