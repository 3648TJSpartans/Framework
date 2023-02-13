package frc.robot.framework.subsystems.SwerveDrive;

import java.util.List;
// import java.util.Timer;

import org.w3c.dom.Element;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.framework.robot.*;
import frc.robot.framework.subsystems.SwerveDrive.Constants.AutoConstants;
import frc.robot.framework.subsystems.SwerveDrive.Constants.DriveConstants;
public class SwerveDrive_Set extends CommandBase implements RobotXML{
    private static final TrajectoryConfig TrajectoryConfig = null;
    private SwerveDrive swerveDrive;

    private double startTime;
    private double command_timeout = 0;
    private Element element;
    private double desired_xTranslation;
    private double desired_yTranslation;
    private double desired_degree;
    private final Timer m_timer = new Timer();
    public SwerveDrive_Set(Element _element){
        element = _element;
        SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
        addRequirements(temp);
        if (temp == null || !(temp instanceof SwerveDrive)) {
            System.out.println(
                    "SwerveDrive_SetPower could not find Swerve subsystem with id:" + element.getAttribute("subSystemID"));
            return;
        }
        swerveDrive = (SwerveDrive) temp;

    }
    public void initialize() {
        startTime = System.currentTimeMillis();
        command_timeout = Double.parseDouble((element.getAttribute("timeout")));
        m_timer.reset();
        m_timer.start();    
    }

    @Override
    public void execute() {
        TrajectoryConfig config = new TrajectoryConfig(
            AutoConstants.kMaxSpeedMetersPerSecond,
            AutoConstants.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(DriveConstants.kDriveKinematics);



            //add absoulte/field relative parameters
            //xAbsolute
            //yAbsolute

            //add parameters for rotation/speed..?
        if (element.hasAttribute("xTranslation")) {
           desired_xTranslation =Double.parseDouble( element.getAttribute("xTranslation"));
        }
        if (element.hasAttribute("yTranslation")) {
            desired_yTranslation =Double.parseDouble( element.getAttribute("yTranslation"));
        }
        if (element.hasAttribute("heading")){
            desired_degree = Double.parseDouble(element.getAttribute("heading"));
        }
        Trajectory tragTrajectory = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(new Translation2d(desired_xTranslation, desired_yTranslation)),
            new Pose2d(desired_xTranslation, desired_yTranslation, new Rotation2d((desired_degree*Math.PI)/180)),
            config);
       swerveDrive.setCommandTrajectory(tragTrajectory,m_timer);
     

    }
    @Override
    public void end(boolean interrupted) {
        m_timer.stop();
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
    public void ReadXML(Element node) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }
}
