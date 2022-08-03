package frc.robot.Framework.IO.Out.Motors.MotorTypes;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Framework.IO.Out.Motors.MotorBase;
import frc.robot.Framework.Util.CommandMode;

public class ServoController implements MotorBase {
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

    @Override
    public void set(double setpoint, CommandMode mode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPID(double kP, double kI, double kD, double kF) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setVoltage(double voltage) {
        // TODO Auto-generated method stub
        
    }
}