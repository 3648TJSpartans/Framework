// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.framework.subsystems.SwerveDrive;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPlannerTrajectory.PathPlannerState;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.subsystems.SwerveDrive.Constants.DriveConstants;
import frc.robot.framework.util.Log;
import frc.robot.framework.util.ShuffleboardFramework;
import frc.robot.framework.util.ShuffleboardFramework.ShuffleboardBase;

public class SwerveDrive extends SubsystemBase implements RobotXML {
    // Create SwerveModules
    private static SwerveModule m_frontLeft;
    private static SwerveModule m_frontRight;
    private static SwerveModule m_backLeft;
    private static SwerveModule m_backRight;

    private static final double kTrackWidth = Units.inchesToMeters(26.5);
    private static final double kWheelBase = Units.inchesToMeters(26.5);

    private Element element;
    private SubsystemCollection subsystemColection;
    private ShuffleboardBase tab;
    private String subsystemName;

    private HolonomicDriveController m_controller;
    private double maxAngularSpeed;
    private double maxSpeedMetersPerSecond;
    private SwerveDriveOdometry m_odometry;

    private double xController = 1;
    private double yController = 1;
    private double thetaController = 1;
    public boolean fieldRelative = false;

    private final SwerveDriveKinematics driveKinematics = new SwerveDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackWidth / 2), // FRONT LEFT
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2), // FRONT RIGHT
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2), // BACK LEFT
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2)); // BACK RIGHT

    private double m_currentRotation = 0.0;
    private double m_currentTranslationDir = 0.0;
    private double m_currentTranslationMag = 0.0;
    public boolean teleFieldRelative;

    private SlewRateLimiter m_magLimiter = new SlewRateLimiter(1.8);
    private SlewRateLimiter m_rotLimiter = new SlewRateLimiter(2.0);
    private double m_prevTime = WPIUtilJNI.now() * 1e-6;

    private String[] headers = { "Time", "Subsystem", "controlMode", "FRspeed", "FRrad", "FLspeed", "FLrad", "BRspeed",
            "BRrad", "BLspeed", "BLrad" };
    private Log swerveLog = new Log("Swerve_Drive", headers);

    /** Creates a new DriveSubsystem. */
    public SwerveDrive(Element _element) {
        element = _element;
        subsystemName = element.getAttribute("id");

        ReadXML(element);
        tab = ShuffleboardFramework.getSubsystem(subsystemName);
        NodeList moduleNodeList = element.getElementsByTagName("module");
        initSwerveModules(moduleNodeList);

        maxSpeedMetersPerSecond = Double.parseDouble(element.getAttribute("maxSpeedMetersPerSecond"));
        maxAngularSpeed = Double.parseDouble(element.getAttribute("maxSpeedMetersPerSecond"));
        final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
                3.141592653589793, 3.141592653589793);
        // Odometry class for tracking robot pose
        m_odometry = new SwerveDriveOdometry(
                driveKinematics,
                Rotation2d.fromDegrees(getGyroAngle()),
                new SwerveModulePosition[] {
                        m_frontLeft.getPosition(),
                        m_frontRight.getPosition(),
                        m_backLeft.getPosition(),
                        m_backRight.getPosition()
                });
        if (element.hasAttribute("xController") && element.hasAttribute("yController")
                && element.hasAttribute("thetaController")) {
            try {
                xController = Double.parseDouble(element.getAttribute("xController"));
                yController = Double.parseDouble(element.getAttribute("yController"));
                thetaController = Double.parseDouble(element.getAttribute("thetaController"));
            } catch (Exception NumberFormatException) {
                throw new NumberFormatException(
                        "Invalid Format on Swerve Drive Subsystem on xController:" + xController + "yController: "
                                + yController + "thetaController: " + thetaController + " not supported varible type");
            }
        } else if (element.hasAttribute("xController") || element.hasAttribute("yController")
                || element.hasAttribute("thetaController")) {
            throw new NumberFormatException(
                    "Invalid Fields on SwerveDrive Subsystem on xController: " + xController + "yController: "
                            + yController + "thetaController: " + thetaController + " not supported varible type");
        }
        m_controller = new HolonomicDriveController(
                new PIDController(xController, 0, 0),
                new PIDController(yController, 0, 0),
                new ProfiledPIDController(thetaController, 0, 0, kThetaControllerConstraints));
    }

    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        m_odometry.resetPosition(
                Rotation2d.fromDegrees(getGyroAngle()),
                new SwerveModulePosition[] {
                        m_frontLeft.getPosition(),
                        m_frontRight.getPosition(),
                        m_backLeft.getPosition(),
                        m_backRight.getPosition()
                },
                pose);
    }

    /**
     * Method to drive the robot using joystick info.
     *
     * @param xSpeed        Speed of the robot in the x direction (forward).
     * @param ySpeed        Speed of the robot in the y direction (sideways).
     * @param rot           Angular rate of the robot.
     * @param fieldRelative Whether the provided x and y speeds are relative to the
     *                      field.
     */
    public void teleFieldRelative(boolean fieldRelative) {
        this.fieldRelative = fieldRelative;
    }

    public void drive(double xSpeed, double ySpeed, double rot) {
        // Adjust input based on max speed
        xSpeed *= maxSpeedMetersPerSecond;
        ySpeed *= maxSpeedMetersPerSecond;
        rot *= maxAngularSpeed;

        var swerveModuleStates = driveKinematics.toSwerveModuleStates(
                fieldRelative
                        ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot,
                                Rotation2d.fromDegrees(getGyroAngle()))
                        : new ChassisSpeeds(xSpeed, ySpeed, rot));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, maxSpeedMetersPerSecond);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_backLeft.setDesiredState(swerveModuleStates[2]);
        m_backRight.setDesiredState(swerveModuleStates[3]);

        if (Math.random() > 0.9) {
            System.out.println(swerveModuleStates[0]);
        }

    }

    /**
     * Sets the wheels into an X formation to prevent movement.
     */
    public void setX() {
        m_frontLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
        m_frontRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        m_backLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        m_backRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
    }

    /**
     * Sets the swerve ModuleStates.
     *
     * @param desiredStates The desired SwerveModule states.
     */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(
                desiredStates, maxSpeedMetersPerSecond);
        m_frontLeft.setDesiredState(desiredStates[0]);
        m_frontRight.setDesiredState(desiredStates[1]);
        m_backLeft.setDesiredState(desiredStates[2]);
        m_backRight.setDesiredState(desiredStates[3]);
    }

    /** Resets the drive encoders to currently read a position of 0. */
    public void resetEncoders() {
        m_frontLeft.resetEncoders();
        m_backLeft.resetEncoders();
        m_frontRight.resetEncoders();
        m_backRight.resetEncoders();
    }

    /**
     * Returns the heading of the robot.
     *
     * @return the robot's heading in degrees, from -180 to 180
     */
    public double getHeading() {
        return Rotation2d.fromDegrees(getGyroAngle()).getDegrees();
    }

    private void initSwerveModules(NodeList _moduleNodeList) {
        for (int i = 0; i < _moduleNodeList.getLength(); i++) {
            Node currentChild = _moduleNodeList.item(i);
            if (currentChild.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element childElement = (Element) currentChild;
            if (childElement.getTagName().equals("module")) {
                switch (childElement.getAttribute("id").toLowerCase()) {
                    case "front_right":
                        m_frontRight = new SwerveModule(childElement);
                        break;

                    case "front_left":
                        m_frontLeft = new SwerveModule(childElement);
                        break;

                    case "back_left":
                        m_backLeft = new SwerveModule(childElement);
                        break;

                    case "back_right":
                        m_backRight = new SwerveModule(childElement);
                        break;

                }
            }
        }
    }

    public void setTeleFieldRelative(boolean fieldRelative) {
        this.teleFieldRelative = fieldRelative;
    }

    public boolean getTeleFieldRelative() {
        return teleFieldRelative;
    }

    public void teleOpInput(double input_xSpeed, double input_ySpeed, double input_rotation,
            boolean input_fieldRelative, boolean rateLimit) {
        double xSpeedCommanded;
        double ySpeedCommanded;

        if (rateLimit) {
            // Convert XY to polar for rate limiting
            double inputTranslationDir = Math.atan2(input_ySpeed, input_xSpeed);
            double inputTranslationMag = Math.sqrt(Math.pow(input_xSpeed, 2) + Math.pow(input_ySpeed, 2));

            // Calculate the direction slew rate based on an estimate of the lateral
            // acceleration
            double directionSlewRate;
            if (m_currentTranslationMag != 0.0) {
                directionSlewRate = Math.abs(2.0 / m_currentTranslationMag);
            } else {
                directionSlewRate = 500.0; // some high number that means the slew rate is effectively instantaneous
            }

            double currentTime = WPIUtilJNI.now() * 1e-6;
            double elapsedTime = currentTime - m_prevTime;
            double angleDif = SwerveUtils.AngleDifference(inputTranslationDir, m_currentTranslationDir);
            if (angleDif < 0.45 * Math.PI) {
                m_currentTranslationDir = SwerveUtils.StepTowardsCircular(m_currentTranslationDir, inputTranslationDir,
                        directionSlewRate * elapsedTime);
                m_currentTranslationMag = m_magLimiter.calculate(inputTranslationMag);
            } else if (angleDif > 0.85 * Math.PI) {
                if (m_currentTranslationMag > 1e-4) { // some small number to avoid floating-point errors with equality
                                                      // checking
                    // keep currentTranslationDir unchanged
                    m_currentTranslationMag = m_magLimiter.calculate(0.0);
                } else {
                    m_currentTranslationDir = SwerveUtils.WrapAngle(m_currentTranslationDir + Math.PI);
                    m_currentTranslationMag = m_magLimiter.calculate(inputTranslationMag);
                }
            } else {
                m_currentTranslationDir = SwerveUtils.StepTowardsCircular(m_currentTranslationDir, inputTranslationDir,
                        directionSlewRate * elapsedTime);
                m_currentTranslationMag = m_magLimiter.calculate(0.0);
            }
            m_prevTime = currentTime;

            xSpeedCommanded = m_currentTranslationMag * Math.cos(m_currentTranslationDir);
            ySpeedCommanded = m_currentTranslationMag * Math.sin(m_currentTranslationDir);
            m_currentRotation = m_rotLimiter.calculate(input_rotation);

        } else {
            xSpeedCommanded = input_xSpeed;
            ySpeedCommanded = input_ySpeed;
            m_currentRotation = input_rotation;
        }

        // Adjust input based on max speed
        double xSpeedDelivered = xSpeedCommanded * maxSpeedMetersPerSecond;
        double ySpeedDelivered = ySpeedCommanded * maxSpeedMetersPerSecond;
        double rotDelivered = m_currentRotation * maxAngularSpeed;

        var swerveModuleStates = driveKinematics.toSwerveModuleStates(
                input_fieldRelative
                        ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered,
                                Rotation2d.fromDegrees(getGyroAngle()))
                        : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
        SwerveDriveKinematics.desaturateWheelSpeeds(
                swerveModuleStates, maxSpeedMetersPerSecond);

        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_backLeft.setDesiredState(swerveModuleStates[2]);
        m_backRight.setDesiredState(swerveModuleStates[3]);
        String[] data = { String.valueOf(swerveModuleStates[0].speedMetersPerSecond),
                String.valueOf(swerveModuleStates[0].angle),
                String.valueOf(swerveModuleStates[1].speedMetersPerSecond),
                String.valueOf(swerveModuleStates[1].angle),
                String.valueOf(swerveModuleStates[2].speedMetersPerSecond),
                String.valueOf(swerveModuleStates[2].angle),
                String.valueOf(swerveModuleStates[3].speedMetersPerSecond),
                String.valueOf(swerveModuleStates[3].angle) };

        swerveLog.Write("Swerve_Drive_Module_SET", data);
    }

    @Override
    public void periodic() {
        m_odometry.update(
                Rotation2d.fromDegrees(getGyroAngle()),
                new SwerveModulePosition[] {
                        m_frontLeft.getPosition(),
                        m_frontRight.getPosition(),
                        m_backLeft.getPosition(),
                        m_backRight.getPosition()
                });

        String[] data = { String.valueOf(m_frontLeft.getState().speedMetersPerSecond),
                String.valueOf(m_frontLeft.getState().angle),
                String.valueOf(m_frontRight.getState().speedMetersPerSecond),
                String.valueOf(m_frontRight.getState().angle),
                String.valueOf(m_backLeft.getState().speedMetersPerSecond),
                String.valueOf(m_backLeft.getState().angle),
                String.valueOf(m_backRight.getState().speedMetersPerSecond),
                String.valueOf(m_backRight.getState().angle) };

        swerveLog.Write("Swerve_Drive_Module_ACTUAL", data);
    }

    public double getGyroAngle() {
        return subsystemColection.gyroscopes.getGYROAngle("swerveGyro", "Z");
    }

    public void setCommandTrajectory(PathPlannerTrajectory tragTrajectory, Timer m_timer) {

        // var desiredState = tragTrajectory.sample(m_timer.get());
        PathPlannerState desiredState = (PathPlannerState) tragTrajectory.sample(m_timer.get());
        Rotation2d m_desiredRotation = desiredState.poseMeters.getRotation();
        var targetChassisSpeeds = m_controller.calculate(getPose(), desiredState,
                m_desiredRotation);

        m_controller.calculate(getPose(), desiredState, m_desiredRotation);

        var targetModuleStates = driveKinematics.toSwerveModuleStates(targetChassisSpeeds);
        m_frontLeft.setDesiredState(targetModuleStates[0]);
        m_frontRight.setDesiredState(targetModuleStates[1]);
        m_backLeft.setDesiredState(targetModuleStates[2]);
        m_backRight.setDesiredState(targetModuleStates[3]);

        // new PPSwerveControllerCommand(tragTrajectory,
        // this::getPose,
        // driveKinematics,
        // new PIDController(1, 0, 0),
        // new PIDController(1, 0, 0),
        // new PIDController(1, 0, 0),
        // this::setModuleStates);
    }

    public void setLimelightTrajectory(Trajectory tragTrajectory, Timer m_timer) {
        var desiredState = tragTrajectory.sample(m_timer.get());
        Rotation2d m_desiredRotation = desiredState.poseMeters.getRotation();
        var targetChassisSpeeds = m_controller.calculate(getPose(), desiredState, m_desiredRotation);

        m_controller.calculate(getPose(), desiredState, m_desiredRotation);

        var targetModuleStates = driveKinematics.toSwerveModuleStates(targetChassisSpeeds);
        m_frontLeft.setDesiredState(targetModuleStates[0]);
        m_frontRight.setDesiredState(targetModuleStates[1]);
        m_backLeft.setDesiredState(targetModuleStates[2]);
        m_backRight.setDesiredState(targetModuleStates[3]);
    }

    @Override
    public void ReadXML(Element node) {
        subsystemColection = new SubsystemCollection(element);

    }

    @Override
    public void ReloadConfig() {

    }
}
