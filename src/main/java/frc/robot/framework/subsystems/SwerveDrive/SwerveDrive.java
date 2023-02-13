// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.framework.subsystems.SwerveDrive;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.subsystems.SwerveDrive.Constants.AutoConstants;
import frc.robot.framework.subsystems.SwerveDrive.Constants.DriveConstants;
import frc.robot.framework.util.ShuffleboardHandler;

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
    private ShuffleboardHandler tab;
    private HolonomicDriveController m_controller;
    private double maxAngularSpeed;
    private double maxSpeedMetersPerSecond;
    SwerveDriveOdometry m_odometry;
    private double xController;
    private double yController;
    private double thetaController;
    private SwerveDriveKinematics driveKinematics = new SwerveDriveKinematics(
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2));

    /** Creates a new DriveSubsystem. */
    public SwerveDrive(Element _element) {
        element = _element;
        ReadXML(element);
        tab = new ShuffleboardHandler(element.getAttribute("id"));
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
        if (element.hasAttribute("xController") && element.hasAttribute("yController")){
            xController = Double.parseDouble(element.getAttribute("xController"));
            yController = Double.parseDouble(element.getAttribute("yController"));
        }else if(element.hasAttribute("xContoller") || element.hasAttribute("yController")){
            System.out.println("");
        }
        m_controller = new HolonomicDriveController(
            new PIDController(xController, 0, 0),
            new PIDController(yController, 0, 0),
            new ProfiledPIDController(thetaController, 0, 0, kThetaControllerConstraints));
    }

    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        m_odometry.update(
                Rotation2d.fromDegrees(getGyroAngle()),
                new SwerveModulePosition[] {
                        m_frontLeft.getPosition(),
                        m_frontRight.getPosition(),
                        m_backLeft.getPosition(),
                        m_backRight.getPosition()
                });
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

    private static void initSwerveModules(NodeList _moduleNodeList) {
        for (int i = 0; i < _moduleNodeList.getLength(); i++) {
            Node currentChild = _moduleNodeList.item(i);
            if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
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
    }

    public double getGyroAngle() {
        return subsystemColection.gyroscopes.getGYROAngle("swerveGyro", "X");
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
    public void teleOpInput(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        // Adjust input based on max speed
        xSpeed *= maxSpeedMetersPerSecond;
        ySpeed *= maxSpeedMetersPerSecond;
        rot *= maxAngularSpeed;

        var swerveModuleStates = driveKinematics.toSwerveModuleStates(
                fieldRelative
                        ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot,
                                Rotation2d.fromDegrees(getGyroAngle()))
                        : new ChassisSpeeds(xSpeed, ySpeed, rot));
        SwerveDriveKinematics.desaturateWheelSpeeds(
                swerveModuleStates, maxSpeedMetersPerSecond);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_backLeft.setDesiredState(swerveModuleStates[2]);
        m_backRight.setDesiredState(swerveModuleStates[3]);
    }



    public void setCommandTrajectory(Trajectory tragTrajectory, Timer m_timer) {

        final SwerveDriveKinematics m_kinematics =  DriveConstants.kDriveKinematics;
        
        var desiredState = tragTrajectory.sample(m_timer.get());
        Rotation2d m_desiredRotation = desiredState.poseMeters.getRotation();
        var targetChassisSpeeds = m_controller.calculate(getPose(), desiredState, m_desiredRotation);

        m_controller.calculate(getPose(), desiredState, m_desiredRotation);

        var targetModuleStates = m_kinematics.toSwerveModuleStates(targetChassisSpeeds);
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
        // TODO Auto-generated method stub

    }

}
