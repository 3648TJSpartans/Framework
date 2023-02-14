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

  private double deadzone;
  private double x = 0;
  private double y = 0;
  private double turning = 0;



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

    if (element.hasAttribute("deadzone"))
      deadzone = Double.parseDouble(element.getAttribute("deadzone"));
  }

  @Override
  public void execute() {
    if (xSpeed == -1 || ySpeed == -1 || turningSpeed == -1)
      return;
      ;

    if (Math.abs(myController.getAxis("LEFT_JOYSTICK_X"))>deadzone){
      x = myController.getAxis(xSpeed);
    }else{
      x = 0;
    }
    if (Math.abs(myController.getAxis("LEFT_JOYSTICK_Y"))>deadzone){
      y = myController.getAxis(ySpeed);
    }else{
      y = 0;
    }
    if (Math.abs(myController.getAxis("RIGHT_JOYSTICK_X"))>deadzone){
      turning = myController.getAxis(turningSpeed);
    }else{
      turning = 0;
    }

    swerveDriveSubsystem.teleOpInput(x, y, turning, false);
    
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