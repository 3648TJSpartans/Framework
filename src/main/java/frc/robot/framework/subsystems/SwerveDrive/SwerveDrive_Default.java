package frc.robot.framework.subsystems.SwerveDrive;

import org.w3c.dom.Element;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;

public class SwerveDrive_Default extends CommandBase implements RobotXML {

  private Element myElement;
  private SwerveDrive swerveDriveSubsystem;
  private ControllerBase myController;
  private int axis_forward=-1;
  private int axis_sideway=-1;
  private int axis_turn=-1;

  private double scale_sideway = 1;
  private double scale_forward = 1;
  private double scale_turn = 1;
  private double deadzone=.08;

  private double output_forward,output_sideway,output_turn=0;



  public SwerveDrive_Default(Element element, ControllerBase controller) {
    myElement = element;
    myController = controller;

    SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
    if (temp == null || !(temp instanceof SwerveDrive)) {
      System.out.println(
          "SwerveDrive_Default could not find swerve subsystem(subsystemID):" + element.getAttribute("subSystemID"));
      return;
    }

    swerveDriveSubsystem = (SwerveDrive) temp;
    this.addRequirements(swerveDriveSubsystem);
    CommandScheduler.getInstance().setDefaultCommand(swerveDriveSubsystem, this);
    
    try{
      if (myElement.getAttribute("axis_forward") != "")
        axis_forward = myController.GetAxisMap().get(myElement.getAttribute("axis_forward"));
      if (myElement.getAttribute("axis_sideway") != "")
        axis_sideway = myController.GetAxisMap().get(myElement.getAttribute("axis_sideway"));
      if (myElement.getAttribute("axis_turn") != "")
        axis_turn = myController.GetAxisMap().get(myElement.getAttribute("axis_turn"));
      if (element.hasAttribute("deadzone"))
        deadzone = Double.parseDouble(element.getAttribute("deadzone"));
    } catch (Exception e){
      throw new NumberFormatException("SwerveDrive_Default: Could not parse axis_forward: "+axis_forward+" axis_sideways: "+axis_forward+" axis_turn: "+ axis_turn+ " deadzone: ");
    }
    try{
      if (myElement.getAttribute("scale_forward") != "")
        scale_forward = Double.parseDouble(myElement.getAttribute("scale_forward"));
      if (myElement.getAttribute("scale_sideway") != "")
        scale_sideway = Double.parseDouble(myElement.getAttribute("scale_sideway"));
      if (myElement.getAttribute("scale_turn") != "")
        scale_turn = Double.parseDouble(myElement.getAttribute("scale_turn"));
    } catch (Exception e){
      throw new NumberFormatException("SwerveDrive_Default: Could not parse scale_forward: "+scale_forward+" scale_sideway: "+scale_sideway+" scale_turn: "+scale_turn);
    }
  }
  
  @Override
    public void initialize(){
    }

  @Override
  public void execute() {
    //FRC coordinates are Y is down the field long ways. X is across the field short ways. Because of that and swerve uses wpi code, forward is actually sideways.
    //Instead of confusing people writing xml, we will flip in teleOPInput
    if (Math.abs(myController.getAxis(axis_sideway))>deadzone){
      output_sideway = myController.getAxis(axis_sideway)*scale_sideway;
    }else{
      output_sideway = 0;
    }
    if (Math.abs(myController.getAxis(axis_forward))>deadzone){
      output_forward = myController.getAxis(axis_forward)*scale_forward;
    }else{
      output_forward = 0;
    }
    if (Math.abs(myController.getAxis(axis_turn))>deadzone){
      output_turn = myController.getAxis(axis_turn)*scale_turn;
    }else{
       output_turn = 0;
    }

    //Now send the original coordinates over in X/Y
    swerveDriveSubsystem.teleOpInput(-output_sideway, -output_forward, -output_turn, false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void ReadXML(Element element) {
  }

  @Override
  public void ReloadConfig() {

  }
}