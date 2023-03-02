package frc.robot.commands;

import java.util.Map;
import org.w3c.dom.Element;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.subsystems.Motor.Motor;
import frc.robot.framework.util.CommandMode;
import frc.robot.framework.util.readTextFile;

public class scorePos extends CommandBase implements RobotXML {

  private String text = "noo";
  private Element myElement;
  private String command;
  private Motor arm;
  private Motor arm_chain;
  private Motor wrist;
  private Motor rotation;
  private Map<String, Double> values;

  public scorePos(Element element) {
    myElement = element;
    values = readTextFile.HashMapFromTextFile();
    SubsystemBase temp_arm = RobotInit.GetSubsystem(element.getAttribute("armSubsystemID"));
    SubsystemBase temp_arm_chain = RobotInit.GetSubsystem(element.getAttribute("armChainSubsystemID"));
    SubsystemBase temp_wrist = RobotInit.GetSubsystem(element.getAttribute("wristSubsystemID"));
    SubsystemBase temp_rotation = RobotInit.GetSubsystem(element.getAttribute("wristSubsystemID"));
    if (temp_arm == null || !(temp_arm instanceof Motor)
        || temp_arm_chain == null || !(temp_arm_chain instanceof Motor)
        || temp_wrist == null || !(temp_wrist instanceof Motor)
        || temp_rotation == null || !(temp_rotation instanceof Motor)) {
      System.out.println(
          "Arm Chain, Arm, Wrist, or Rotation could not find Motor subsystem with id:"
              + element.getAttribute("armChainSubsystemID") + ","+
              element.getAttribute("armSubsystemID")+","+
              element.getAttribute("wristSubsystemID")+","+
              element.getAttribute("rotationSubsystemID") );
      return;
    }
    arm = (Motor) temp_arm;
    this.addRequirements(arm);
    arm_chain = (Motor) temp_arm_chain;
    this.addRequirements(arm_chain);
    wrist = (Motor) temp_wrist;
    this.addRequirements(wrist);
    rotation = (Motor) temp_rotation;
    this.addRequirements(rotation);

  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {

    System.out.println("scorePosCommand: " + text);
    if (myElement.hasAttribute("command")) {
      command = myElement.getAttribute("command");
    }
    switch (command) {
      case "stowed":
        wrist.setReference(values.get("wrist_up"), CommandMode.POSITION);
        arm_chain.setReference(values.get("stowed_armChain"), CommandMode.POSITION);
        arm.setReference(values.get("stowed_arm"), CommandMode.POSITION);

        break;
      case "score_high":
        arm.setReference(values.get("score_high_arm"), CommandMode.POSITION);
        arm_chain.setReference(values.get("score_high_armChain"), CommandMode.POSITION);
        break;
      case "score_low":
        arm.setReference(values.get("score_low_armChain"), CommandMode.POSITION);
        arm_chain.setReference(values.get("score_low_arm"), CommandMode.POSITION);
        break;
      case "store_med":
        arm.setReference(values.get("score_medium_arm"), CommandMode.POSITION);
        arm_chain.setReference(values.get("score_medium_armChain"),
            CommandMode.POSITION);
        break;
      case "transport":
        arm.setReference(values.get("transport_arm"), CommandMode.POSITION);
        arm_chain.setReference(values.get("transport_armChain"),
            CommandMode.POSITION);
        break;
      case "pickup_double":
        arm.setReference(values.get("pickup_double_arm"), CommandMode.POSITION);
        arm_chain.setReference(values.get("pickup_double_armChain"),
            CommandMode.POSITION);
        break;
      case "pickup_single":
        arm.setReference(values.get("pickup_single_arm"), CommandMode.POSITION);
        arm_chain.setReference(values.get("pickup_single_armChain"),
            CommandMode.POSITION);
        break;
      case "unstow":
        arm.setReference(values.get("unstowed_arm"), CommandMode.POSITION);
        arm_chain.setReference(values.get("unstowed_armChain"), CommandMode.POSITION);
        break;
      default:
        break;

    }
    System.out.println(arm.getMotor().getPosition());
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