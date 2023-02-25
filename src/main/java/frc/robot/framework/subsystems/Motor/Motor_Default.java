package frc.robot.framework.subsystems.Motor;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.util.CommandMode;

public class Motor_Default extends CommandBase implements RobotXML {

  private Element myElement;
  private Motor motorSubsystem;
  private ControllerBase myController;
  private double deadzone = .05;

  private int axisNumberPower1 = -1;
  private int axisNumberPower2 = -1;
  private boolean axis1BeingUsed = true;
  private boolean axis2BeingUsed = false;
  private boolean inverted1 = false;
  private boolean inverted2 = false;

  public Motor_Default(Element element, ControllerBase controller) {
    myElement = element;
    myController = controller;

    SubsystemBase temp = RobotInit.GetSubsystem(myElement.getAttribute("subsystemID"));
    if (temp == null || !(temp instanceof Motor)) {
      System.out
          .println("Motor_Default could not find Motor subsystem with id:" + myElement.getAttribute("subsystemID"));
      return;
    }
    
    motorSubsystem = (Motor) temp;
    this.addRequirements(motorSubsystem);
    CommandScheduler.getInstance().setDefaultCommand(motorSubsystem, this);

    if (element.hasAttribute("deadzone"))
      deadzone = Double.parseDouble(element.getAttribute("deadzone"));
    if (element.hasAttribute("axisReference1") || element.hasAttribute("axisReference")) {
      if (element.hasAttribute("axisReference1"))
        axisNumberPower1 = myController.GetAxisMap().get(element.getAttribute("axisReference1"));
      else {
        axisNumberPower1 = myController.GetAxisMap().get(element.getAttribute("axisReference"));
      }

    }
    if (element.hasAttribute("axisReference2"))
      axisNumberPower2 = myController.GetAxisMap().get(element.getAttribute("axisReference2"));
    if (element.hasAttribute("inverted1"))
      inverted1 = Boolean.parseBoolean(element.getAttribute("inverted1"));
    if (element.hasAttribute("inverted2"))
      inverted2 = Boolean.parseBoolean(element.getAttribute("inverted2"));

  }

  @Override
    public void initialize(){
    }

  @Override
  public void execute() {

    if (!axis2BeingUsed) {
      if (axisNumberPower1 != -1) {
        double input1 = myController.getAxis(axisNumberPower1);
        if (Math.abs(input1) > deadzone) {
          if (axis1BeingUsed) // Sets it one time. Doesn't keep overriding buttons

          if (inverted1)
            motorSubsystem.setReference(-input1);
          else
            motorSubsystem.setReference(input1, CommandMode.PERCENTAGE);
          axis2BeingUsed = false;
        } else {
          
          axis1BeingUsed = true;
        }
      }
    }

    if (axisNumberPower2 != -1) {
      double input2 = myController.getAxis(axisNumberPower2);
      if (Math.abs(input2) > deadzone) {
        if (axis2BeingUsed) // Sets it one time. Doesn't keep overriding buttons
          motorSubsystem.setReference(input2, CommandMode.PERCENTAGE);
        axis2BeingUsed = false;
      } else {
        if (inverted2)
          motorSubsystem.setReference(-input2);
        else
          motorSubsystem.setReference(input2);
        axis2BeingUsed = true;
      }

    }
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