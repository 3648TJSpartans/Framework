package frc.robot.framework.subsystems.TankDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;

public class TankDrive_Default extends CommandBase implements RobotXML {

  private Element myElement;
  private TankDrive tankDrivSubsystem;
  private ControllerBase myController;

  private int axisNumberTurn = -1;
  private int axisNumberForward = -1;
  private int axisNumberLeft = -1;
  private int axisNumberRight = -1;
  private double deadzone = 0.0;

  // <defaultCommand subsystemID="TankDrive" axisForward="LEFT_JOYSTICK_Y"
  // axisTurn="RIGHT_JOYSTICK_X" command="TankDrive_Default" scaleX="2"
  // scaleY=".75"></axis>

  public TankDrive_Default(Element element, ControllerBase controller) {
    myElement = element;
    myController = controller;

    SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
    if (temp == null || !(temp instanceof TankDrive)) {
      System.out.println(
          "TankDrive_Default could not find TankDrive subsystem(subsystemID):" + element.getAttribute("subSystemID"));
      return;
    }
    tankDrivSubsystem = (TankDrive) temp;
    this.addRequirements(tankDrivSubsystem);

    CommandScheduler.getInstance().setDefaultCommand(tankDrivSubsystem, this);

    try {
      if (myElement.getAttribute("axisForward") != "")
        axisNumberForward = myController.GetAxisMap().get(myElement.getAttribute("axisForward"));
      if (myElement.getAttribute("axisTurn") != "")
        axisNumberTurn = myController.GetAxisMap().get(myElement.getAttribute("axisTurn"));
      if (myElement.getAttribute("axisLeft") != "")
        axisNumberLeft = myController.GetAxisMap().get(myElement.getAttribute("axisLeft"));
      if (myElement.getAttribute("axisRight") != "")
        axisNumberRight = myController.GetAxisMap().get(myElement.getAttribute("axisRight"));
      if (element.hasAttribute("deadzone"))
        deadzone = Double.parseDouble(element.getAttribute("deadzone"));
    } catch (Exception e) {
      throw new NumberFormatException("TankDrive_Default: Could not parse axisForward,axisTurn,axisLeft,axisRight,deadzone");
    }

  }

  @Override
  public void execute() {
    if (axisNumberTurn != -1 && Math.abs(myController.getAxis(axisNumberTurn)) > deadzone)
      tankDrivSubsystem.setInputTurn(myController.getAxis(axisNumberTurn));
    else
      tankDrivSubsystem.setInputTurn(0.0);
    if (axisNumberForward != -1 && Math.abs(myController.getAxis(axisNumberForward)) > deadzone)
      tankDrivSubsystem.setInputForward(myController.getAxis(axisNumberForward));
    else
      tankDrivSubsystem.setInputForward(0.0);
    if (axisNumberLeft != -1 && Math.abs(myController.getAxis(axisNumberLeft)) > deadzone)
      tankDrivSubsystem.setInputLeft(myController.getAxis(axisNumberLeft));
    else
      tankDrivSubsystem.setInputLeft(0.0);
    if (axisNumberRight != -1 && Math.abs(myController.getAxis(axisNumberRight)) > deadzone)
      tankDrivSubsystem.setInputRight(myController.getAxis(axisNumberRight));
    else
      tankDrivSubsystem.setInputRight(0.0);
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