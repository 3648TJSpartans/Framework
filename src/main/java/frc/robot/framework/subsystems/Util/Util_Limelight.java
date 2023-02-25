package frc.robot.framework.subsystems.Util;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.vision.Limelight;

public class Util_Limelight extends CommandBase implements RobotXML {

  private Element myElement;
  private ControllerBase myController;

  public Util_Limelight(Element element) {
    myElement = element;
  }

  @Override
    public void initialize(){
  }
  
  @Override
  public void execute() {

    System.out.println(Limelight.getLimelightX());
    
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void ReadXML(Element element) {
  }

  @Override
  public void ReloadConfig() {

  }
}