package frc.robot.framework.subsystems.SwerveDrive;

import java.util.List;

import org.w3c.dom.Element;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.vision.Limelight;

public class SwerveAprilCenter extends CommandBase implements RobotXML {
    private Element myElement;
    private double startTime;
    private double delayLength = 0;
    private double currentX;
    private double distanceError;
    private double currentY;
    private double desiredx;
    private double center_offset;
    private double limlight_speed;
    private SwerveDrive swerveDrive;
    private final Timer m_timer = new Timer();
    public SwerveAprilCenter(Element element){
        myElement = element;
        SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
        addRequirements(temp);
        swerveDrive = (SwerveDrive) temp;
    }
    public void initialize() {
        startTime = System.currentTimeMillis();
        delayLength = Double.parseDouble((myElement.getAttribute("delayLength")));
        m_timer.reset();
        m_timer.start();    
    }

    @Override
    public void execute() {
        currentX = Limelight.getLimelightX();
        currentY = Limelight.getLimelightY();
        if (myElement.hasAttribute("distanceError") && myElement.hasAttribute("limelight_speed") && 
        myElement.hasAttribute("center_offset")){
            try{
        distanceError = Double.parseDouble(myElement.getAttribute("distanceError"));
        limlight_speed = Double.parseDouble(myElement.getAttribute("limelight_speed"));
        center_offset = Double.parseDouble(myElement.getAttribute("center_offset"));
            }catch(Exception NumberFormatException){
                throw new NumberFormatException(
                    "Invalid Format on Swerve Drive Subsystem on distanceError:" + distanceError + "limlight_speed: "
                            + limlight_speed+ "center_offset: "+center_offset);
            }
    }else if (myElement.hasAttribute("distanceError") || myElement.hasAttribute("limelight_speed") || myElement.hasAttribute("center_offset")||
    (myElement.hasAttribute("distanceError") && myElement.hasAttribute("limelight_speed")) || (myElement.hasAttribute("distanceError") && (myElement.hasAttribute("center_offset"))||
    myElement.hasAttribute("limelight_speed") && myElement.hasAttribute("center_offset"))){
        throw new NumberFormatException("Invalid Format on Swerve Drive Subsystem on distanceError:" + distanceError + "limlight_speed: "
                                + limlight_speed+ "center_offset: "+center_offset);
    }
        TrajectoryConfig config = new TrajectoryConfig(3,3);
        if (currentX > distanceError){
            //move right
            desiredx = limlight_speed;
        }else if(currentX < distanceError){
            //move left 
             desiredx = -limlight_speed;
        }else{
            desiredx = 0;
        }
        Trajectory tragTrajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(0, 0, new Rotation2d(0)),
                List.of(new Translation2d(center_offset+desiredx, 0)),
                new Pose2d(center_offset+desiredx, 0, new Rotation2d(0)),
                config);
        swerveDrive.setLimelightTrajectory(tragTrajectory, m_timer);
    }

    @Override
    public void end(boolean interrupted) {
        m_timer.stop();
      }
    @Override
    public boolean isFinished() {
        System.out.println("Time Complete: " + (System.currentTimeMillis() - startTime) + " " + (delayLength * 1000)
                + (System.currentTimeMillis() - startTime > delayLength * 1000));
        if (System.currentTimeMillis() - startTime > delayLength * 1000) {
            return true;
        }
        return false;
    }


    
    @Override
    public void ReloadConfig() {

    }
    @Override
    public void ReadXML(Element node) {
        // TODO Auto-generated method stub
        
    }
}
