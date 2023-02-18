package frc.robot.framework.encoder;

import org.w3c.dom.*;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class EncoderWrapper implements EncoderBase {
    private EncoderBase encoder;

    public EncoderWrapper(Element element) {

        String encoderType = element.getAttribute("type");
        int portOne = Integer.parseInt(element.getAttribute("port_one"));
        int portTwo = 0;
        if (!element.getAttribute("port_two").isEmpty()) {
            portTwo = Integer.parseInt(element.getAttribute("port_two"));
        }

        encoder = getEncoderType(encoderType, portOne, portTwo);
        if (!element.getAttribute("distance_per_pulse").isEmpty()) {
            encoder.setDistancePerPulse(Double.parseDouble(element.getAttribute("distance_per_pulse")));
        }

        if ( element.hasAttribute("setPosition") ) {
            encoder.setPosition(Double.parseDouble(element.getAttribute("setPosition")));
        }

        boolean invertedMotor = false;
            if (element.hasAttribute("inverted")) {
                invertedMotor = (Boolean.parseBoolean(element.getAttribute("inverted")));
            }
            encoder.setInverted(invertedMotor);
    }

    public EncoderWrapper(Element element, EncoderBase encoderBase) {
        encoder = encoderBase;
        if (!element.getAttribute("distance_per_pulse").isEmpty()) {
            encoder.setDistancePerPulse(Double.parseDouble(element.getAttribute("distance_per_pulse")));
        }
    }

    private EncoderBase getEncoderType(String encoderType, int portOne, int portTwo) {
        if (encoderType.toLowerCase().equals("toughbox") || encoderType.toLowerCase().equals("775")) {
            return new DIOEncoder(portOne, portTwo, false, EncodingType.k4X);
        } else if (encoderType.toLowerCase().equals("sparkmax") || encoderType.toLowerCase().equals("talonsrx")) {
            System.out.println(
                    "CAN Encoder found, initalize by passing EncoderBase into the consturctor: " + encoderType);
            return null;
        } else {
            System.out.println("Unknown encoder type: " + encoderType);
            return null;
        }
    }

    public int getTicks() {
        return encoder.getTicks();
    }

    public double getVelocity() {
        return encoder.getVelocity();
    }

    public double getPosition() {
        return encoder.getPosition();
    }

    public void resetEncoder() {
        encoder.resetEncoder();
    }

    @Override
    public void setDistancePerPulse(double factor) {
        encoder.setDistancePerPulse(factor);
    }

    @Override
    public void setInverted(boolean inverted) {
        encoder.setInverted(inverted);
        
    }

    @Override
    public void setPosition(double position) {
        // TODO Auto-generated method stub
        
    }
}