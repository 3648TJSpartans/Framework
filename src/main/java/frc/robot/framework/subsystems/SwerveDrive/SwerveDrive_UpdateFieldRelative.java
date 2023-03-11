package frc.robot.framework.subsystems.SwerveDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;

public class SwerveDrive_UpdateFieldRelative extends CommandBase implements RobotXML {

  private Element myElement;
  private SubsystemCollection subsystemColection;
  private SwerveDrive swerveDriveSubsystem;

  public SwerveDrive_UpdateFieldRelative(Element element) {
    ReadXML(element);

    SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
    if (temp == null || !(temp instanceof SwerveDrive)) {
      throw new UnsupportedOperationException("SwerveDrive_UpdateFieldRelative could not find swerve subsystem(subsystemID):" + element.getAttribute("subSystemID"));
    }
    swerveDriveSubsystem = (SwerveDrive) temp;
    this.addRequirements(swerveDriveSubsystem);

  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    swerveDriveSubsystem.updateFieldRelative();
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void ReadXML(Element element) {
  }

  @Override
  public void ReloadConfig() {

  }
}