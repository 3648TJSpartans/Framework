package frc.robot.framework.motor;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;

public class SparkController extends MotorController implements MotorBase {
    private Spark controller;

    public SparkController(int port) {
        controller = new Spark(port);
    }

    @Override
    public void setReference(double reference, CommandMode mode) {
        if (inverted)
            reference*=-1;
        if (mode == CommandMode.PERCENTAGE)
            controller.setVoltage(reference*RobotController.getBatteryVoltage());
        else{
            throw new UnsupportedOperationException("SparmPWM does not support position/velocity yet!");
        }
    }

    @Override
    public void setInverted(boolean inverted)
    {
        super.setInverted(inverted);
    }
}