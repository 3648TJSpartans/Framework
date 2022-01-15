package frc.robot.Framework.IO.In.Sensors.Encoders;

import org.w3c.dom.*;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import frc.robot.Framework.IO.In.Sensors.Encoders.EncoderTypes.DIOEncoder;
import frc.robot.Framework.IO.In.Sensors.Encoders.EncoderTypes.NeoEncoder;
import frc.robot.Framework.IO.Out.Motors.MotorWrapper;
import frc.robot.Framework.Util.PID.PIDController;

public class EncoderWrapper{
    private EncoderBase encoder;
    private MotorWrapper parent;
    private PIDController pidController = new PIDController(this);
    private String currentProfile;

    public EncoderWrapper(Element element, MotorWrapper parent){
        this.parent = parent;

        String encoderType = element.getAttribute("type");
        int portOne = Integer.parseInt(element.getAttribute("port_one"));
        int portTwo = Integer.parseInt(element.getAttribute("port_two"));
        encoder = getEncoderType(encoderType, portOne, portTwo);
        if(element.getAttribute("distance_per_pulse") != null){
            encoder.setDistancePerPulse(Double.parseDouble(element.getAttribute("distance_per_pulse")));
        }

        NodeList profiles = element.getElementsByTagName("pid");
        for(int i = 0; i < profiles.getLength(); i++) {
            Node currentProfile = profiles.item(i);
            if (currentProfile.getNodeType() == Node.ELEMENT_NODE) {
                Element profileElement = (Element) currentProfile;
                double kP = Double.parseDouble(profileElement.getAttribute("kP"));
                double kI = Double.parseDouble(profileElement.getAttribute("kI"));
                double kD = Double.parseDouble(profileElement.getAttribute("kD"));
                double kF = Double.parseDouble(profileElement.getAttribute("kF"));
                String type = profileElement.getAttribute("name");
                
                pidController.addProfile(type, kP, kI, kD, kF);
            }
        }
    }

    private EncoderBase getEncoderType(String encoderType, int portOne, int portTwo){
        if (encoderType.equals("TOUGH_BOX")) {
            return new DIOEncoder(portOne, portTwo, false, EncodingType.k4X);
        }else if(encoderType.equals("NEO")){
            return new NeoEncoder(parent.getMotor());
        }else if(encoderType.equals("775")){
            return new DIOEncoder(portOne, portTwo, false, EncodingType.k4X);
        }else{
            return null;
        }
    }

    public void setPIDProfile(String profile){
        if(!currentProfile.equals(profile)){
            this.currentProfile = profile;
            pidController.setProfile(profile);
        }
    }

    public int getTicks(){
        return encoder.getTicks();
    }

    public double getVelocity(){
        return encoder.getVelocity();
    }

    public double getPosition(){
        return encoder.getPosition();
    }

    public void reset(){
        encoder.reset();
    }

    public double getPIDOutput(){
        return pidController.getPIDOutput();
    }
}