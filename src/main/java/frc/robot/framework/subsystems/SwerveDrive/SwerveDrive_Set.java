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
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.framework.robot.*;
import frc.robot.framework.vision.Limelight;
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
    private double kMaxSpeedMetersPerSecond;
    private double kMaxAccelerationMetersPerSecondSquared;

    public static final double kTrackWidth = Units.inchesToMeters(26.5);
    public static final double kWheelBase = Units.inchesToMeters(26.5);
   
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));
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
        try{
        command_timeout = Double.parseDouble((element.getAttribute("timeout")));
        } catch (Exception NumberFormatException){
            throw new NumberFormatException("Invalid Format on timeout: "+command_timeout);
        }
        m_timer.reset();
        m_timer.start();    
    }

    @Override
    public void execute() {
        if(element.hasAttribute("setMaxSpeed") && element.hasAttribute("setMaxAcceleration")){
            try{
            kMaxSpeedMetersPerSecond = Double.parseDouble(element.getAttribute("setMaxSpeed"));
            kMaxAccelerationMetersPerSecondSquared = Double.parseDouble(element.getAttribute("setMaxAcceleration"));
            }catch (Exception NumberFormatException){
                throw new NumberFormatException("Invalid Format on SwerveDrive_Set Subsystem on setMaxSpeed: "+ kMaxSpeedMetersPerSecond+"setMaxAcceleration: "+kMaxAccelerationMetersPerSecondSquared+" not supported varible type");
            }
        }else if(element.hasAttribute("setMaxSpeed") || element.hasAttribute("setMaxAcceleration")){
            throw new NumberFormatException("Invalid Fields on SwerveDrive_Set Subsystem on setMaxSpeed: "+ kMaxSpeedMetersPerSecond+"setMaxAcceleration: "+kMaxAccelerationMetersPerSecondSquared);
        }

        TrajectoryConfig config = new TrajectoryConfig(
        kMaxSpeedMetersPerSecond,
        kMaxAccelerationMetersPerSecondSquared)
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(kDriveKinematics);

            //add absoulte/field relative parameters
            //xAbsolute
            //yAbsolute

            //add parameters for rotation/speed..?

        if (element.hasAttribute("xTranslation") && element.hasAttribute("yTranslation") && element.hasAttribute("heading")) {
            try{
           desired_xTranslation =Double.parseDouble( element.getAttribute("xTranslation"));
           desired_yTranslation =Double.parseDouble( element.getAttribute("yTranslation"));
           desired_degree = Double.parseDouble(element.getAttribute("heading"));
            } catch (Exception NumberFormatException){
                throw new NumberFormatException("Invalid Format on SwerveDrive_Set Subsystem on xTranslation: "+ desired_xTranslation+"yTranslation: "+desired_yTranslation+" heading: "+desired_degree+" not supported varible type");
            }
        }
        if (element.hasAttribute("yTranslation") || element.hasAttribute("xTranslation") || (element.hasAttribute("xTranslation") && element.hasAttribute("yTranslation"))||
        (element.hasAttribute("xTranslation") && element.hasAttribute("heading")) || (element.hasAttribute("yTranslation") && element.hasAttribute("heading"))) {
            throw new NumberFormatException("Invalid Fields on SwerveDrive_Set Subsystem on xTranslation: "+ desired_xTranslation+"yTranslation: "+desired_yTranslation+" heading: "+desired_degree);
        }
        Trajectory tragTrajectory = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(new Translation2d(desired_xTranslation, desired_yTranslation)),
            new Pose2d(desired_xTranslation, desired_yTranslation, new Rotation2d(Math.toRadians(desired_degree))),
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