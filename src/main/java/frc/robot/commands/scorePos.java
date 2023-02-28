package frc.robot.commands;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.subsystems.Motor.Motor;
import frc.robot.framework.util.CommandMode;

public class scorePos extends CommandBase implements RobotXML {

  private String text = "noo";
  private Element myElement;
  private String command;
  private Motor arm;
  private Motor arm_chain;

  public scorePos(Element element) {
    myElement = element;

    SubsystemBase temp_arm = RobotInit.GetSubsystem(element.getAttribute("armSubsystemID"));
    addRequirements(temp_arm);
    SubsystemBase temp_arm_chain = RobotInit.GetSubsystem(element.getAttribute("armChainSubsystemID"));
    addRequirements(temp_arm_chain);
    if (temp_arm == null || !(temp_arm instanceof Motor)
        || (temp_arm_chain == null || !(temp_arm_chain instanceof Motor))) {
      System.out.println(
          "Arm Chain or Arm could not find Motor subsystem with id:" + element.getAttribute("armSubsystemID")
              + element.getAttribute("armChainSubsystemID"));
      return;
    }
    arm = (Motor) temp_arm;
    this.addRequirements(arm);
    arm_chain = (Motor) temp_arm_chain;
    this.addRequirements(arm_chain);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {

    System.out.println("testCommand: " + text);
    if (myElement.hasAttribute("command")) {
      command = myElement.getAttribute("command");
    }
    switch (command) {
      case "stowed":

        break;
      case "score_high":
        // set chain to value set arm to value
        arm.setReference(55, CommandMode.POSITION);
        break;
      case "score_low":
        break;
      case "store_med":
        break;
      case "transport":
        break;
      case "pickup_double":
        break;
      case "pickup_single":
        break;
      case "unstow":
        break;
      default:
        break;

    }
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void ReadXML(Element element) {
    text = element.getAttribute("myParam");
  }

  @Override
  public void ReloadConfig() {

  }
}