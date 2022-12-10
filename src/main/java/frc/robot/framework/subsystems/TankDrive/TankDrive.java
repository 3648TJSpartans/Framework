package frc.robot.framework.subsystems.TankDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.framework.util.CommandMode;
import frc.robot.framework.util.Log;

public class TankDrive extends SubsystemBase implements RobotXML {
    ShuffleboardHandler tab;
    private SubsystemCollection subsystemColection;
    private double input_forward = 0;
    private double input_turn = 0;
    private double input_left = 0;
    private double input_right = 0;
    private double rightCurrentPower = 0;
    private double leftCurrentPower = 0;

    // String[] headers = {"Left Encoder", "Right Encoder", "Left Speed", "Right
    // Speed", "Left Voltage", "Right Voltage", "Left Current", "Right Current"};
    private String[] headers = { "left", "right" };
    private Log log = new Log("TankDrive", headers);

    public TankDrive(Element subsystem) {
        tab = new ShuffleboardHandler(subsystem.getAttribute("id"));
        ReadXML(subsystem);
    }

    @Override
    public void periodic() {

        double leftOutput = input_turn + input_forward;
        double rightOutput = input_turn - input_forward;
        double powerDiff = 0;
        double correctionValue;

        // Fix turning at high speeds
        // Restricts output from exceeding 1
        if (rightOutput > 1 && rightOutput > leftOutput) {
            correctionValue = rightOutput - 1;
            rightOutput -= correctionValue;
            leftOutput += correctionValue;
        } else if (rightOutput < -1 && rightOutput < leftOutput) {
            correctionValue = rightOutput + 1;
            rightOutput -= correctionValue;
            leftOutput += correctionValue;
        }

        if (leftOutput > 1 && rightOutput < leftOutput) {
            correctionValue = leftOutput - 1;
            rightOutput += correctionValue;
            leftOutput -= correctionValue;
        } else if (leftOutput < -1) {
            correctionValue = leftOutput + 1;
            rightOutput += correctionValue;
            leftOutput -= correctionValue;
        }

        subsystemColection.motors.setOutput("left", leftOutput, CommandMode.PERCENTAGE);
        subsystemColection.motors.setOutput("right", rightOutput, CommandMode.PERCENTAGE);

        // System.out.println(leftOutput + " and " + rightOutput);

        // log.LogData(subsystemColection.motors.getEncoder("left").getDistance(),
        // subsystemColection.motors.getEncoder("right").getDistance(),
        // subsystemColection.motors.getEncoder("left").getRate(),
        // subsystemColection.motors.getEncoder("right").getRate(),
        // subsystemColection.motors.getMotor("left").getMotorOutputVoltage(),
        // subsystemColection.motors.getMotor("right").getMotorOutputVoltage(),
        // subsystemColection.motors.getMotor("left").getOutputCurrent(),
        // subsystemColection.motors.getMotor("right").getOutputCurrent());

        String[] data = { String.valueOf(leftOutput), String.valueOf(leftOutput) };
        log.Write("Teleop", data);

    }

    public void setInputForward(double forward) {
        input_forward = forward;
    }

    public void setInputTurn(double turn) {
        input_turn = turn;
    }

    public void setInputLeft(double left) {
        input_left = left;
    }

    public void setInputRight(double right) {
        input_right = right;
    }

    @Override
    public void simulationPeriodic() {
    }

    @Override
    public void ReadXML(Element element) {
        // XMLUtil.prettyPrint(element);
        subsystemColection = new SubsystemCollection(element);
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub

    }
}
