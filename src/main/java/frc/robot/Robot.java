package frc.robot;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotInit;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.util.XMLUtil;
import frc.robot.subsystem.*;

public class Robot extends TimedRobot{

  @Override
  public void robotInit() {
    RobotInit.Init();
    //In.Init("XML/Controls_IN/GarryChassis.xml", "XML/Controls_IN/GarryShooter.xml");
    //Out.Init("XML/Config_OUT/CHASSIS.xml", "XML/Config_OUT/SHOOTER.xml");
    //subsystems.add(new Chassis());
    //subsystems.add(new Shooter());
    //subsystems.add(new Arms());
  }
  
  

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testPeriodic() {
  }

}
