package frc.robot.framework.motor;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.framework.encoder.EncoderBase;

public class SparkController extends MotorController implements MotorBase {
    private Spark controller;

    public SparkController(int port) {
        controller = new Spark(port);
    }

    @Override
    public void setPower(double power) {
        if (inverted)
            power*=-1;
        controller.setVoltage(power*RobotController.getBatteryVoltage());
    }

    @Override
    public void setInverted(boolean inverted)
    {
        super.setInverted(inverted);
    }

    @Override
    public EncoderBase getEncoder() {
        // TODO Auto-generated method stub
        return null;
    }
}