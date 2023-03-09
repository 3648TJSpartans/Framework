package frc.robot.commands;

import java.util.Map;
import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.Timer;
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
  private final Timer m_timer = new Timer();

  // private Motor rotation;
  private Map<String, Double> values;

  public scorePos(Element element) {
    myElement = element;
    values = readTextFile.HashMapFromTextFile();
    SubsystemBase temp_arm = RobotInit.GetSubsystem(element.getAttribute("armSubsystemID"));
    SubsystemBase temp_arm_chain = RobotInit.GetSubsystem(element.getAttribute("armChainSubsystemID"));
    SubsystemBase temp_wrist = RobotInit.GetSubsystem(element.getAttribute("wristSubsystemID"));
    // SubsystemBase temp_rotation =
    // RobotInit.GetSubsystem(element.getAttribute("wristSubsystemID"));
    if (temp_arm == null || !(temp_arm instanceof Motor)
        || temp_arm_chain == null || !(temp_arm_chain instanceof Motor)
        || temp_wrist == null || !(temp_wrist instanceof Motor)) {
      System.out.println(
          "Arm Chain, Arm, Wrist, or Rotation could not find Motor subsystem with id:"
              + element.getAttribute("armChainSubsystemID") + "," +
              element.getAttribute("armSubsystemID") + "," +
              element.getAttribute("wristSubsystemID"));
      return;
    }
    arm = (Motor) temp_arm;
    this.addRequirements(arm);
    arm_chain = (Motor) temp_arm_chain;
    this.addRequirements(arm_chain);
    wrist = (Motor) temp_wrist;
    this.addRequirements(wrist);
  }

  @Override
  public void initialize() {
    m_timer.reset();
    m_timer.start();
  }

  @Override
  public void execute() {

    System.out.println("scorePosCommand: " + text);
    if (myElement.hasAttribute("type")) {
      command = myElement.getAttribute("type");
    }
    switch (command) {
      case "stowed":
        wrist.setReference(values.get("wrist_up"), CommandMode.POSITION);
        Timer.delay(.2);
        arm_chain.setReference(values.get("stowed_armChain"), CommandMode.POSITION);
        Timer.delay(.2);
        arm.setReference(values.get("stowed_arm"), CommandMode.POSITION);

        break;
      case "score_high":
        arm.setReference(values.get("score_high_arm"), CommandMode.POSITION);
        Timer.delay(.2);
        arm_chain.setReference(values.get("score_high_armChain"), CommandMode.POSITION);
        Timer.delay(.2);
        wrist.setReference(values.get("wrist_down"), CommandMode.POSITION);
        break;
      case "score_low":
        arm.setReference(values.get("score_low_armChain"), CommandMode.POSITION);
        Timer.delay(.2);
        arm_chain.setReference(values.get("score_low_arm"), CommandMode.POSITION);
        break;
      case "store_med":
        arm.setReference(values.get("score_medium_arm"), CommandMode.POSITION);
        Timer.delay(.2);
        arm_chain.setReference(values.get("score_medium_armChain"), CommandMode.POSITION);
        break;
      case "transport":
        arm.setReference(values.get("transport_arm"), CommandMode.POSITION);
        Timer.delay(.2);
        arm_chain.setReference(values.get("transport_armChain"), CommandMode.POSITION);

        break;
      case "pickup_double":
        arm.setReference(values.get("pickup_double_arm"), CommandMode.POSITION);
        Timer.delay(.2);
        arm_chain.setReference(values.get("pickup_double_armChain"),
            CommandMode.POSITION);
        break;
      case "pickup_single":
        arm.setReference(values.get("pickup_single_arm"), CommandMode.POSITION);
        Timer.delay(.2);
        arm_chain.setReference(values.get("pickup_single_armChain"),
            CommandMode.POSITION);
        break;
      case "unstow":
        arm.setReference(values.get("unstowed_arm"), CommandMode.POSITION);
        Timer.delay(.2);
        arm_chain.setReference(values.get("unstowed_armChain"), CommandMode.POSITION);
        break;
      default:
        break;

    }
  }

  @Override
  public void end(boolean interrupted) {
    m_timer.stop();
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