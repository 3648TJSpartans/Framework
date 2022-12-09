package frc.robot.framework.subsystems.Servo;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;

public class Servo_Default extends CommandBase implements RobotXML {

  private Element myElement;
  private Servo servoSubsystem;
  private ControllerBase myController;

  private boolean button;

  // <defaultCommand subsystemID="TankDrive" axisForward="LEFT_JOYSTICK_Y"
  // axisTurn="RIGHT_JOYSTICK_X" command="TankDrive_Default" scaleX="2"
  // scaleY=".75"></axis>

  public Servo_Default(Element element, ControllerBase controller) {
    myElement = element;
    myController = controller;

    SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
    if (temp == null || !(temp instanceof Servo)) {
      System.out.println(
          "Servo_default could not find Servo subsystem(subsystemID):" + element.getAttribute("subSystemID"));
      return;
    }
    servoSubsystem = (Servo) temp;
    this.addRequirements(servoSubsystem);

    CommandScheduler.getInstance().setDefaultCommand(servoSubsystem, this);
    if (myElement.getAttribute("button") != "")
      button = myController.getButton(myElement.getAttribute("button"));
  }

  @Override
  public void execute() {
    if (button || !button)
      servoSubsystem.setInputTurn(myController.getButton(getName()));
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