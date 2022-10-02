package frc.robot.framework.io.out.servo;

import edu.wpi.first.wpilibj.Servo;

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