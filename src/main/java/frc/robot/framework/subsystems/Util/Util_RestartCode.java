package frc.robot.framework.subsystems.Util;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;

public class Util_RestartCode extends CommandBase implements RobotXML {

  private Element myElement;
  private ControllerBase myController;

  public Util_RestartCode(Element element) {
    myElement = element;
  }

  @Override
    public void initialize(){
  }

  @Override
  public void execute() {
      System.out.println("Restarting Code");
      System.exit(0);
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