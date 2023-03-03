package frc.robot.framework.subsystems.SwerveDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.ADIS16470_IMU.IMUAxis;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.algorithm.SoftwarePID;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.sensor.gyroscope.GyroTypes.ADIS_16470;
import frc.robot.framework.util.CommandMode;

public class SwerveDrive_Balance extends CommandBase implements RobotXML {

    private Element myElement;
    private SubsystemCollection subsystemColection;
    private SwerveDrive swerveDriveSubsystem;
    private SoftwarePID pid = new SoftwarePID(0.03, 0, 0, 0);
    private double startTime;
    private final Timer m_timer = new Timer();
    private double command_timeout = 0;

    public SwerveDrive_Balance(Element element) {
        myElement = element;

        ADIS_16470.m_gyro.setYawAxis(IMUAxis.kX);

        SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
        if (temp == null || !(temp instanceof SwerveDrive)) {
            System.out.println(
                    "SetRelative could not find swerve subsystem(subsystemID):" + element.getAttribute("subSystemID"));
            return;
        }
        swerveDriveSubsystem = (SwerveDrive) temp;
        this.addRequirements(swerveDriveSubsystem);
        subsystemColection = new SubsystemCollection(element);

    }

    @Override
    public void initialize() {

        startTime = System.currentTimeMillis();
        try {
            command_timeout = Double.parseDouble((myElement.getAttribute("timeout")));
        } catch (Exception NumberFormatException) {
            throw new NumberFormatException("Invalid Format on timeout: " + command_timeout);
        }
        m_timer.reset();
        m_timer.start();

    }

    @Override
    public void execute() {

        // double gyro = subsystemColection.gyroscopes.getGYROAngle("swerveGyro", "X");
        double gyro = ADIS_16470.m_gyro.getAngle();

        double motorSpeed = pid.getPowerOutput(gyro, 0, CommandMode.VELOCITY);

        swerveDriveSubsystem.teleOpInput(motorSpeed, 0, 0, false);

    }

    @Override
    public boolean isFinished() {
        System.out.println("Time Complete: " + (System.currentTimeMillis() - startTime) + " " + (command_timeout * 1000)
                + (System.currentTimeMillis() - startTime > command_timeout * 1000));
        if (System.currentTimeMillis() - startTime > command_timeout * 1000) {
            ADIS_16470.m_gyro.setYawAxis(IMUAxis.kY);
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