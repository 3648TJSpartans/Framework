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
    private double motor_lastOutput=0;
    private boolean inverted=false;
    private CommandMode commandMode=CommandMode.PERCENTAGE;
    private Element motorElement;
    private EncoderWrapper encoder;
    private PIDWrapper pid;
    private double setpoint=0;

    public MotorWrapper(Element element, boolean groupFlag, SubsystemCollection collection) {
        if (!groupFlag){ // single motor, not motorgroup
            motorElement = element;
            int port = Integer.parseInt(motorElement.getAttribute("port"));
            motor = getMotorType(motorElement.getAttribute("controller"), port);

            if (motorElement.hasAttribute("inverted")) {
                inverted=(Boolean.parseBoolean(motorElement.getAttribute("inverted")));
            }
        }
        else{
            //MotorGroup
            NodeList groupMotorNodes = element.getElementsByTagName("motor");
            MotorGroup group = new MotorGroup();
            for (int o = 0; o < groupMotorNodes.getLength(); o++) {
                Node currentMotor = groupMotorNodes.item(o);
                if (currentMotor.getNodeType() == Node.ELEMENT_NODE) {
                    Element motorElement = (Element) currentMotor;
                    int port = Integer.parseInt(motorElement.getAttribute("port"));
                    MotorBase motorInMotorGroup=getMotorType(motorElement.getAttribute("controller"), port);
                    group.addMotor(motorInMotorGroup);
                }
            }
            motor = group;
            if (element.hasAttribute("inverted")) {
                inverted = Boolean.parseBoolean(element.getAttribute("inverted"));
            }
        }
        //read encoder
        NodeList childNodeList = element.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeType() != Node.ELEMENT_NODE){continue;}
            Element childElement = (Element)childNodeList.item(i);
            switch (childElement.getTagName().toLowerCase()) {
                case "encoder":;
                    if (childElement.getAttribute("type").toLowerCase().equals("talonsrx")){
                        encoder = new EncoderWrapper(childElement);
                        collection.encoders.put(childElement.getAttribute("id"), encoder);
                    }
                    else if ( childElement.getAttribute("type").toLowerCase().equals("sparkmax")){
                        encoder = new EncoderWrapper(childElement, motor.getEncoder());
                        collection.encoders.put(childElement.getAttribute("id"), encoder);
                    }
                    else {
                        System.out.println("MotorWrapper: Encoder type: "+childElement.getAttribute("type")+" not found.");
                    }
                    break;
                default:
                    break;
            }
        }

        //read pid
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeType() != Node.ELEMENT_NODE){continue;}
            Element childElement = (Element)childNodeList.item(i);
            switch (childElement.getTagName().toLowerCase()) {
                case "pid":;
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
                System.out.println("For motor:" + motorElement.getAttribute("port")
                    + " motor controller type: " + motorElement.getAttribute("controller")
                    + " was not found!");
                return null;
        }
        return motorBase;
    }

    public void setPower(double power) {
        if (inverted)
            power*=-1;
        motor.setPower(power);
    }

    public void setInverted(boolean inverted) {
        this.inverted =inverted;
    }

    public String getAttribute(String attribute) {
        return motorElement.getAttribute(attribute);
    }

    public void setCommandMode(CommandMode mode) {
        commandMode = mode;
    }

    public void setPID(double kP, double kI, double kD, double kF) {
        // SmartDashboard.putNumber("KP", kP);
        // SmartDashboard.putNumber("KI", kI);
        // SmartDashboard.putNumber("KD", kD);
        // SmartDashboard.putNumber("kF", kF);
        pid.setPID(kP,kI,kD,kF);
    }

    public MotorBase getMotor() {
        return motor;
    }

    public int getTicks() {
        if (encoder==null){
            return 0;
        }
        return encoder.getTicks();
    }

    public double getVelocity() {
        if (encoder==null){
            return 0;
        }
        return encoder.getVelocity();
    }

    public double getPosition() {
        if (encoder==null){
            return 0;
        }return encoder.getPosition();
    }
    
    public EncoderWrapper getEncoder(){
        return encoder;
    }

    public double getLastOutput() {
        return motor_lastOutput;
    }

    public boolean hasEncoder() {
        return encoder!=null;
    }

    public void setEncoder(EncoderWrapper encoder) {
        this.encoder=encoder;       
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