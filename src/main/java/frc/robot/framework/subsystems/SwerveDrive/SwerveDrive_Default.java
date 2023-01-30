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
  private int xSpeed = -1;
  private int ySpeed = -1;
  private int turningSpeed = -1;


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
    if (myElement.getAttribute("xSpeed") != "")
      xSpeed = myController.GetAxisMap().get(myElement.getAttribute("xSpeed"));
    if (myElement.getAttribute("ySpeed") != "")
      ySpeed = myController.GetAxisMap().get(myElement.getAttribute("ySpeed"));
    if (myElement.getAttribute("turningSpeed") != "")
      turningSpeed = myController.GetAxisMap().get(myElement.getAttribute("turningSpeed"));
  }

  @Override
  public void execute() {
    if (xSpeed != -1)
      swerveDriveSubsystem.getXSpeed(myController.getAxis(xSpeed));
    if (ySpeed != -1)
      swerveDriveSubsystem.setYSpeed(myController.getAxis(ySpeed));
    if (turningSpeed != -1)
      swerveDriveSubsystem.setTurningSpeed(myController.getAxis(turningSpeed));

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