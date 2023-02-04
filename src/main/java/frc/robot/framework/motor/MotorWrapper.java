package frc.robot.framework.motor;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import frc.robot.framework.algorithm.PIDWrapper;
import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.encoder.EncoderWrapper;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.util.CommandMode;

public class MotorWrapper implements MotorBase {

    private MotorBase motor;

    private Element motorElement;

    public MotorWrapper(Element element, boolean groupFlag, SubsystemCollection collection) {
        motorElement = element;
        if (!groupFlag) { // single motor, not motorgroup
            motor = createMotorBase(element, collection);
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
                    MotorBase motorInMotorGroup = createMotorBase(motorGroupElement, collection);
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
    }

    //we need collection because some motors might have nested element like sparkmax
    private MotorBase createMotorBase(Element element, SubsystemCollection collection) {
        int port = Integer.parseInt(element.getAttribute("port"));
        String controllerType=element.getAttribute("controller");
        MotorBase motorBase;
        switch (controllerType.toLowerCase()) {
            case "sparkpwm":
                motorBase = new SparkController(port);
                break;
            case "talonsrx":
                throw new UnsupportedOperationException("TalonSRX not implemented in MotorWrapper");
            case "sparkmax":
                motorBase = new SparkMaxController(element, collection);
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

    public MotorBase getMotor() {
        return motor;
    }
}