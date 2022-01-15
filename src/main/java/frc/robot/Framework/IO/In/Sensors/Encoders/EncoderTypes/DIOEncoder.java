package frc.robot.Framework.IO.In.Sensors.Encoders.EncoderTypes;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import frc.robot.Framework.IO.In.Sensors.Encoders.EncoderBase;

public class DIOEncoder implements EncoderBase{

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

    public void reset(){
        encoder.reset();
    }
    
}