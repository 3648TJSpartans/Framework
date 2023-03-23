package frc.robot.framework.subsystems.SwerveDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.algorithm.SoftwarePID;
import frc.robot.framework.robot.*;
import frc.robot.framework.sensor.gyroscope.GyroTypes.ADIS_16448;
import frc.robot.framework.util.CommandMode;

public class SwerveDrive_Balance extends CommandBase implements RobotXML {

    private Element myElement;
    private SwerveDrive swerveDriveSubsystem;
    private SoftwarePID pid;
    private double startTime;
    private final Timer m_timer = new Timer();
    private double command_timeout = 0;
    private double deadzone = 0.015;
    private boolean inverted = false;
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double kF = 0;

    public SwerveDrive_Balance(Element element) {
        myElement = element;

        SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
        if (temp == null || !(temp instanceof SwerveDrive)) {
            System.out.println(
                    "SetRelative could not find swerve subsystem(subsystemID):" + element.getAttribute("subSystemID"));
            return;
        }
        swerveDriveSubsystem = (SwerveDrive) temp;
        this.addRequirements(swerveDriveSubsystem);

    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        try {
            command_timeout = Double.parseDouble((myElement.getAttribute("timeout")));
        } catch (Exception NumberFormatException) {
            throw new NumberFormatException("Invalid Format on timeout: " + command_timeout);
        }

        try {
            kP = Double.parseDouble((myElement.getAttribute("kP")));
        } catch (Exception numberFormatException) {
            throw new NumberFormatException("Invalid Format on kP: " + kP);
        }

        try {
            deadzone = Double.parseDouble((myElement.getAttribute("deadzone")));
        } catch (Exception NumberFormatException) {
            throw new NumberFormatException("Invalid Format on deadzone: " + deadzone);
        }

        try {
            inverted = Boolean.parseBoolean(((myElement.getAttribute("inverted"))));
        } catch (Exception NumberFormatException) {
            throw new NumberFormatException("Invalid Format on inverted: " + inverted);
        }

        if (!myElement.getAttribute("kI").isEmpty()) {
            kI = Double.parseDouble((myElement.getAttribute("kI")));
        }

        if (!myElement.getAttribute("kD").isEmpty()) {
            kD = Double.parseDouble((myElement.getAttribute("kD")));
        }

        if (!myElement.getAttribute("kF").isEmpty()) {
            kF = Double.parseDouble((myElement.getAttribute("kF")));
        }

        pid = new SoftwarePID(kP, kI, kD, kF);

        m_timer.reset();
        m_timer.start();

    }

    @Override
    public void execute() {

        double angle = ADIS_16448.m_gyro.getGyroAngleX();

        double motorSpeed = pid.getPowerOutput(angle, 0, CommandMode.VELOCITY);

        if (Math.abs(motorSpeed) < deadzone) {
            motorSpeed = 0;
        }

        if (inverted) {
            motorSpeed *= -1;
        }

        swerveDriveSubsystem.teleOpInput(motorSpeed, 0, 0, false);

    }

    @Override
    public boolean isFinished() {
        System.out.println("Time Complete: " + (System.currentTimeMillis() - startTime) + " " + (command_timeout * 1000)
                + (System.currentTimeMillis() - startTime > command_timeout * 1000));
        if (System.currentTimeMillis() - startTime > command_timeout * 1000) {
            return true;
        }
        return false;

    }

    @Override
    public void ReadXML(Element element) {
    }

    @Override
    public void ReloadConfig() {

    }
}