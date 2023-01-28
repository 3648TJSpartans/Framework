package frc.robot.framework.subsystems.Util;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.*;

import frc.robot.framework.robot.*;

public class Util_Delay extends CommandBase implements RobotXML {

  private Element myElement;
  private double startTime;
  private double delayLength = 0;

  public Util_Delay(Element element) {
    myElement = element;

  }

  public void initialize() {
    startTime = System.currentTimeMillis();
    delayLength = Double.parseDouble((myElement.getAttribute("delayLength")));
  }

  @Override
  public void execute() {

  }

  @Override
  public boolean isFinished() {
    System.out.println("Time Complete: " + (System.currentTimeMillis() - startTime) + " " + (delayLength * 1000)
        + (System.currentTimeMillis() - startTime > delayLength * 1000));
    if (System.currentTimeMillis() - startTime > delayLength * 1000) {
      return true;
    }
    return false;
  }

  @Override
  public void ReadXML(Element node) {
    // TODO Auto-generated method stub

  }

  @Override
  public void ReloadConfig() {
    // TODO Auto-generated method stub

  }
}