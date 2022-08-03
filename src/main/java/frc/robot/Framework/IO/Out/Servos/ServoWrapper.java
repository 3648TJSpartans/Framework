package frc.robot.Framework.IO.Out.Servos;

import org.w3c.dom.*;

import frc.robot.Framework.IO.In.Encoders.EncoderBase;
import frc.robot.Framework.IO.In.Encoders.EncoderWrapper;
import frc.robot.Framework.IO.Out.Servos.ServoBase;
import frc.robot.Framework.IO.Out.Servos.ServoGroup;
import frc.robot.Framework.IO.Out.Servos.ServoController;
import frc.robot.Framework.Util.CommandMode;
import frc.robot.Framework.Util.PID.PIDController;

public class ServoWrapper implements ServoBase {

    private ServoBase motor;
    private Element motorElement;

    private EncoderWrapper encoder;

    public ServoWrapper(Element element) {
        motorElement = element;
        String id = motorElement.getAttribute("id");
        int port = Integer.parseInt(motorElement.getAttribute("port"));
        motor = getMotorType(motorElement.getAttribute("controller"), port);

        if (motor == null) {
            System.out.println("For motor: " + id + " motor controller type: " + motorElement.getAttribute("controller")
                    + " was not found!");
            return;
        }
    }

    public ServoWrapper(Element groupElement, boolean groupFlag) {
        String groupID = groupElement.getAttribute("id");
        NodeList groupMotorNodes = groupElement.getElementsByTagName("motor");
        ServoGroup group = new ServoGroup();
        for (int o = 0; o < groupMotorNodes.getLength(); o++) {
            Node currentMotor = groupMotorNodes.item(o);
            if (currentMotor.getNodeType() == Node.ELEMENT_NODE) {
                Element motorElement = (Element) currentMotor;
                int port = Integer.parseInt(motorElement.getAttribute("port"));
                ServoBase controllerType = getMotorType(motorElement.getAttribute("controller"), port);

                if (controllerType != null) {
                    group.addMotor(new ServoWrapper(motorElement));
                } else {
                    System.out.println("For motor:" + motorElement.getAttribute("port") + " in group: " + groupID
                            + " motor controller type: " + controllerType
                            + " was not found!");
                    continue;
                }
            }
        }
        motor = group;

    }


    private ServoBase getMotorType(String controllerType, int port) {
        if (controllerType.equals("SERVO")) {
            return new ServoController(port);
        } else {
            return null;
        }
    }

    public void set(double position) {
        motor.set(position);
    }

    public void setAngle(int angle) {
        motor.setAngle(angle);
    }

    public String getAttribute(String attribute) {
        return motorElement.getAttribute(attribute);
    }



    public ServoBase getMotor() {
        return motor;
    }

    public void resetEncoder() {
        encoder.reset();
    }
}