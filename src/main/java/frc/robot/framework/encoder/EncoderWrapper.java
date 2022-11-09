package frc.robot.framework.encoder;

import java.util.HashMap;
import java.util.Map;


import org.w3c.dom.*;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import frc.robot.framework.algorithm.PID;
import frc.robot.framework.motor.MotorWrapper;

public class EncoderWrapper{
    private EncoderBase encoder;
    public MotorWrapper motor;
    public String motorid;
    public Map<String,PID> pidMap=new HashMap<String,PID>();
    public String pidProfile;

    public EncoderWrapper(Element element){

        String encoderType = element.getAttribute("type");
        int portOne = Integer.parseInt(element.getAttribute("port_one"));
        int portTwo = Integer.parseInt(element.getAttribute("port_two"));
       
        boolean foundMotor=false;
        NodeList childNodeList = element.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeType() != Node.ELEMENT_NODE){continue;}
            Element childElement = (Element)childNodeList.item(i);
            switch (childElement.getTagName().toLowerCase()) {
                case "motor":
                case "motorgroup":
                    if (foundMotor){
                        System.out.println("Encoder "+element.getAttribute("id")+" only supports a single motor/motorgroup - id:"+
                            childElement.getAttribute("id")+
                            " Convert to a single motor group");
                        break;
                    }
                    motor = new MotorWrapper(childElement, true);
                    motorid = childElement.getAttribute("id");
                    foundMotor=true;
                    break;
                case "pid":;
                    PID tempPID = new PID(childElement);
                    pidMap.put(childElement.getAttribute("id"), tempPID);
                    pidProfile=childElement.getAttribute("id");
                    break;
                default:
                    break;
            }
        }

        encoder = getEncoderType(encoderType, portOne, portTwo);
        if(element.getAttribute("distance_per_pulse").equals(null)){
            encoder.setDistancePerPulse(Double.parseDouble(element.getAttribute("distance_per_pulse")));
        }
    }

    private EncoderBase getEncoderType(String encoderType, int portOne, int portTwo){
        if (encoderType.equals("TOUGHBOX" ) || encoderType.equals("775")) {
            return new DIOEncoder(portOne, portTwo, false, EncodingType.k4X);
        //  }else if(encoderType.equals("NEO")){
        //      return new NeoEncoder(motors);
        }else if(encoderType.equals("SPARKMAX") || encoderType.equals("TALONSRX")){
            return motor.getEncoder();
        }else{
            System.out.println("Unknown encoder type: "+encoderType);
            return null;
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

    public void setPID(double kP, double kI, double kD, double kF){
        if (!pidMap.containsKey("ShuffleBoard"))
            pidMap.put("ShuffleBoard", new PID(kP, kI, kD, kF));
        else
            pidMap.get("ShuffleBoard").setPID(kP, kI, kD, kF);
    }

    public double getPIDOutput(){
        return pidMap.get(pidProfile).getOutput();
    }
}