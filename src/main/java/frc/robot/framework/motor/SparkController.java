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
    public void setOutput(double output, CommandMode mode) {
        if (inverted)
            output*=-1;
        controller.setVoltage(output*RobotController.getBatteryVoltage());
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