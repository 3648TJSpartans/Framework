package frc.robot.Framework.IO.Out.Servos;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Framework.IO.Out.Servos.ServoBase;
import frc.robot.Framework.Util.CommandMode;

public class ServoController implements ServoBase {
    private Servo controller;

    public ServoController(int port) {
        controller = new Servo(port);
    }

    public void set(double position) {
        controller.set(position);
    };

    public void setAngle(int angle) {
        controller.setAngle(angle);
    }

    public void setInverted(boolean invert) {
        return;
    }

}