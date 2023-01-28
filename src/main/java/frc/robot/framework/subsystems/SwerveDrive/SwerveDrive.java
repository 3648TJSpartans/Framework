package frc.robot.framework.subsystems.SwerveDrive;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.framework.util.XMLUtil;
import frc.robot.framework.util.CommandMode;
import frc.robot.framework.util.Log;
import frc.robot.framework.sensor.gyroscope.*;

public class SwerveDrive extends SubsystemBase implements RobotXML {
    ShuffleboardHandler tab;
    private SubsystemCollection subsystemColection;
    private Element myElement;

    private static double trackWidth;
    // Distance between right and left wheels
    private static double wheelBase;
    // Distance between front and back wheels
    private SwerveDriveKinematics driveKinematics;
    private SwerveDriveOdometry odometer;
    private double MaxSpeedMetersPerSecond = 5;

    private static SwerveModule frontRight;
    private static SwerveModule frontLeft;
    private static SwerveModule backRight;
    private static SwerveModule backLeft;

    private double xSpeed;
    private double ySpeed;
    private double turningSpeed;

    public SwerveDrive(Element subsystem) {
        myElement = subsystem;
        subsystemColection = new SubsystemCollection(myElement);
        tab = new ShuffleboardHandler(subsystem.getAttribute("id"));
        NodeList moduleNodeList = subsystem.getElementsByTagName("module");
        initSwerveModules(moduleNodeList);

        trackWidth = Units.inchesToMeters(Double.parseDouble(subsystem.getAttribute("width")));
        wheelBase = Units.inchesToMeters(Double.parseDouble(subsystem.getAttribute("wheel_base")));

        driveKinematics = new SwerveDriveKinematics(
                new Translation2d(wheelBase / 2, -trackWidth / 2),
                new Translation2d(wheelBase / 2, trackWidth / 2),
                new Translation2d(-wheelBase / 2, -trackWidth / 2),
                new Translation2d(-wheelBase / 2, trackWidth / 2));

        odometer = new SwerveDriveOdometry(driveKinematics,
                new Rotation2d(0));

    }

    private static void initSwerveModules(NodeList _moduleNodeList) {
        for (int i = 0; i < _moduleNodeList.getLength(); i++) {
            Node currentChild = _moduleNodeList.item(i);
            if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) currentChild;
                if (childElement.getTagName().equals("module")) {
                    switch(childElement.getAttribute("id").toLowerCase()){
                        case "front_right":
                            frontRight = new SwerveModule(childElement);
                            break;

                        case "front_left":
                            frontLeft = new SwerveModule(childElement);
                            break;
                        
                        case "back_left":
                            backLeft = new SwerveModule(childElement);
                            break;

                        case "back_right":
                            backRight = new SwerveModule(childElement);
                            break;

                    }
                }
            }
        }
    }

    @Override
    public void periodic() {
        odometer.update(getRotation2d(), frontRight.getState(), frontLeft.getState(), backLeft.getState(),
                backRight.getState());

        // 4. Construct desired chassis speeds
        ChassisSpeeds chassisSpeeds;
        if (Boolean.parseBoolean(myElement.getAttribute("fieldOriented"))) {
            // Relative to field
            chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
                    xSpeed, ySpeed, turningSpeed, getRotation2d());
        } else {
            // Relative to robot
            chassisSpeeds = new ChassisSpeeds(xSpeed, ySpeed, turningSpeed);
        }

        // 5. Convert chassis speeds to individual module states
        SwerveModuleState[] moduleStates = driveKinematics.toSwerveModuleStates(chassisSpeeds);

        // 6. Output each module states to wheels
        setModuleStates(moduleStates);

        if(Math.random() > 0.9){
            System.out.println(moduleStates[0]);
            System.out.println(odometer.getPoseMeters());
        }

        SmartDashboard.putNumber("Robot Heading", getHeading());
        SmartDashboard.putString("Robot Location", getPose().getTranslation().toString());

    }

    @Override
    public void simulationPeriodic() {

    }

    @Override
    public void ReadXML(Element element) {
        // XMLUtil.prettyPrint(element);

    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub

    }

    public void getXSpeed(double _xSpeed) {
        xSpeed = _xSpeed;
    }

    public void setYSpeed(double _ySpeed) {
        ySpeed = _ySpeed;
    }

    public void setTurningSpeed (double _turningSpeed){

    }

    public double getHeading() {
        return subsystemColection.gyroscopes.getGYROAngle("swerveGyro", "X") % 360;
    }

    public Rotation2d getRotation2d() {
        return Rotation2d.fromDegrees(getHeading());
    }

    public Pose2d getPose() {
        return odometer.getPoseMeters();
    }

    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, MaxSpeedMetersPerSecond);
        frontLeft.setDesiredState(desiredStates[0]);
        frontRight.setDesiredState(desiredStates[1]);
        backLeft.setDesiredState(desiredStates[2]);
        backRight.setDesiredState(desiredStates[3]);
    }

    public void stopModules() {
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }


}
