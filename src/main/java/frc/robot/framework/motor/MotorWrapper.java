package frc.robot.framework.motor;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.encoder.EncoderWrapper;
import frc.robot.framework.util.CommandMode;

public class MotorWrapper implements MotorBase {

    private MotorBase motor;
    private Element motorElement;
    private EncoderWrapper encoder;

    public MotorWrapper(Element element) {
        motorElement = element;
        String id = motorElement.getAttribute("id");
        int port = Integer.parseInt(motorElement.getAttribute("port"));
        motor = getMotorType(motorElement.getAttribute("controller"), port);

        if (motorElement.hasAttribute("inverted")) {
            motor.setInverted(Boolean.parseBoolean(motorElement.getAttribute("inverted")));
        }
    }

    public MotorWrapper(Element groupElement, boolean groupFlag) {
        NodeList groupMotorNodes = groupElement.getElementsByTagName("motor");
        MotorGroup group = new MotorGroup();
        for (int o = 0; o < groupMotorNodes.getLength(); o++) {
            Node currentMotor = groupMotorNodes.item(o);
            if (currentMotor.getNodeType() == Node.ELEMENT_NODE) {
                Element motorElement = (Element) currentMotor;
                int port = Integer.parseInt(motorElement.getAttribute("port"));
                MotorBase controllerType=getMotorType(motorElement.getAttribute("controller"), port);
                
                group.addMotor(controllerType);
            }
        }
        motor = group;
        if (groupElement.hasAttribute("inverted")) {
            motor.setInverted(Boolean.parseBoolean(groupElement.getAttribute("inverted")));
        }
    }

    private MotorBase getMotorType(String controllerType, int port) {
        MotorBase motorBase;
        switch (controllerType) {
            case "SPARK":
                motorBase = new SparkController(port);
                break;
            case "TALONSRX":
                motorBase = new TalonSRXController(port);
                break;
            case "SPARKMAX":
                 motorBase = new SparkMaxController(port);
                break;
            default:
                System.out.println("For motor:" + motorElement.getAttribute("port")
                    + " motor controller type: " + motorElement.getAttribute("controller")
                    + " was not found!");
                return null;
        }
        return motorBase;
    }

    public void set(double speed) {
        motor.set(speed);
    }

    public void setInverted(boolean invert) {
        motor.setInverted(invert);
    }

    public String getAttribute(String attribute) {
        return motorElement.getAttribute(attribute);
    }

    @Override
    public void set(double setpoint, CommandMode mode) {
        motor.set(setpoint, mode);
    }

    @Override
    public void setPID(double kP, double kI, double kD, double kF) {
        motor.setPID(kP, kI, kD, kF);
    }

    public MotorBase getMotor() {
        return motor;
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
        encoder.reset();
    }

    @Override
    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);

    }

    @Override
    public boolean isCANEncoder() {
        return motor.isCANEncoder();
    }
    
    @Override
    public EncoderBase getEncoder(){
        return motor.getEncoder();
    }
}