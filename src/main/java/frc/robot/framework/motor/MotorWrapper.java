package frc.robot.framework.motor;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import frc.robot.framework.algorithm.PIDWrapper;
import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.encoder.EncoderWrapper;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.util.CommandMode;

public class MotorWrapper implements MotorBase, EncoderBase {

    private MotorBase motor;
    private double motor_lastOutput = 0;
    private CommandMode commandMode = CommandMode.PERCENTAGE;

    private EncoderWrapper encoder;
    private PIDWrapper pid;
    private double setpoint = 0;
    private Element motorElement;

    public MotorWrapper(Element element, boolean groupFlag, SubsystemCollection collection) {
        motorElement = element;
        if (!groupFlag) { // single motor, not motorgroup
            int port = Integer.parseInt(motorElement.getAttribute("port"));
            motor = getMotorType(motorElement.getAttribute("controller"), port);
            boolean invertedMotor = false;
            if (motorElement.hasAttribute("inverted")) {
                invertedMotor = (Boolean.parseBoolean(motorElement.getAttribute("inverted")));
            }
            motor.setInverted(invertedMotor);
        } else {

            // MotorGroup
            NodeList groupMotorNodes = element.getElementsByTagName("motor");
            MotorGroup group = new MotorGroup();
            Element motorGroupElement = null;

            // each motor in motor group
            for (int o = 0; o < groupMotorNodes.getLength(); o++) {
                Node currentMotor = groupMotorNodes.item(o);
                if (currentMotor.getNodeType() == Node.ELEMENT_NODE) {
                    motorGroupElement = (Element) currentMotor;
                    int port = Integer.parseInt(motorGroupElement.getAttribute("port"));
                    MotorBase motorInMotorGroup = getMotorType(motorGroupElement.getAttribute("controller"), port);
                    boolean invertedMotor = false;
                    if (motorGroupElement.hasAttribute("inverted")) {
                        invertedMotor = (Boolean.parseBoolean(motorGroupElement.getAttribute("inverted")));
                        motorInMotorGroup.setInverted(invertedMotor);
                    }
                    group.addMotor(motorInMotorGroup);
                }
            }
            motor = group;

            // Motorgroup level config
            boolean invertedMotor = false;
            if (element.hasAttribute("inverted")) {
                invertedMotor = (Boolean.parseBoolean(element.getAttribute("inverted")));
                motor.setInverted(invertedMotor);
            }
        }
        // read encoder
        NodeList childNodeList = element.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element childElement = (Element) childNodeList.item(i);
            switch (childElement.getTagName().toLowerCase()) {
                case "encoder":
                    ;
                    if (childElement.getAttribute("type").toLowerCase().equals("talonsrx")) {
                        encoder = new EncoderWrapper(childElement);
                        collection.encoders.put(childElement.getAttribute("id"), encoder);
                    } else if (childElement.getAttribute("type").toLowerCase().equals("sparkmax")) {
                        encoder = new EncoderWrapper(childElement, motor.getEncoder());
                        collection.encoders.put(childElement.getAttribute("id"), encoder);
                    } else {
                        System.out.println(
                                "MotorWrapper: Encoder type: " + childElement.getAttribute("type") + " not found.");
                    }
                    break;
                default:
                    break;
            }
        }

        // read pid
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element childElement = (Element) childNodeList.item(i);
            switch (childElement.getTagName().toLowerCase()) {
                case "pid":
                    ;
                    pid = new PIDWrapper(childElement, motor, encoder);
                    break;
                default:
                    break;
            }
        }
    }

    private MotorBase getMotorType(String controllerType, int port) {
        MotorBase motorBase;
        switch (controllerType.toLowerCase()) {
            case "sparkpwm":
                motorBase = new SparkController(port);
                break;
            case "talonsrx":
                motorBase = new TalonSRXController(port);
                break;
            case "sparkmax":
                motorBase = new SparkMaxController(port);
                break;
            default:
                System.out.println("For motor:" + port +
                        " motor controller type: " + controllerType + " not found.");
                return null;
        }
        return motorBase;
    }

    public void setReference(double reference, CommandMode mode) {
        motor.setReference(reference, mode);
    }

    public void setInverted(boolean inverted) {
        motor.setInverted(inverted);
    }

    public String getAttribute(String attribute) {
        return motorElement.getAttribute(attribute);
    }

    public void setPID(double kP, double kI, double kD, double kF) {
        // SmartDashboard.putNumber("KP", kP);
        // SmartDashboard.putNumber("KI", kI);
        // SmartDashboard.putNumber("KD", kD);
        // SmartDashboard.putNumber("kF", kF);
        pid.setPID(kP, kI, kD, kF);
    }

    public MotorBase getMotor() {
        return motor;
    }

    public int getTicks() {
        if (encoder == null) {
            return 0;
        }
        return encoder.getTicks();
    }

    public double getVelocity() {
        if (encoder == null) {
            return 0;
        }
        return encoder.getVelocity();
    }

    public double getPosition() {
        if (encoder == null) {
            return 0;
        }
        return encoder.getPosition();
    }

    public EncoderWrapper getEncoder() {
        return encoder;
    }

    public double getLastOutput() {
        return motor_lastOutput;
    }

    public boolean hasEncoder() {
        return encoder != null;
    }

    public void setEncoder(EncoderWrapper encoder) {
        this.encoder = encoder;
    }

    @Override
    public void setDistancePerPulse(double factor) {
        encoder.setDistancePerPulse(factor);
    }

    @Override
    public void resetEncoder() {
        encoder.resetEncoder();
    }
}