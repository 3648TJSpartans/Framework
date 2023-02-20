package frc.robot.framework.subsystems.SwerveDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;


public class SwerveDrive_Relative extends CommandBase implements RobotXML {

  private Element myElement;
  private SubsystemCollection subsystemColection;
  private SwerveDrive swerveDriveSubsystem;

  public SwerveDrive_Relative(Element element) {
    ReadXML(element);

  }

  @Override
  public void execute() {
      try{
        if(swerveDriveSubsystem.fieldRelative){
          swerveDriveSubsystem.teleFieldRelative(false);
        }else{
          swerveDriveSubsystem.teleFieldRelative(true);
        }
      }catch(Exception e){
        throw new NumberFormatException("SwerveDrive_Relative: Error in execute");
      }
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void ReadXML(Element element) {
    SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
    if (temp == null || !(temp instanceof SwerveDrive)) {
      System.out.println(
          "SwerveDrive_Default could not find swerve subsystem(subsystemID):" + element.getAttribute("subSystemID"));
      return;
    }
    swerveDriveSubsystem = (SwerveDrive) temp;
    this.addRequirements(swerveDriveSubsystem);
  }

  @Override
  public void ReloadConfig() {

  }
}