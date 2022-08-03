package frc.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.sql.rowset.spi.XmlReader;

import org.w3c.dom.Document;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Framework.Subsystems;
import frc.robot.Subsystems.*;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;
import frc.robot.Framework.Util.XMLMerger;
import frc.robot.Framework.Util.XMLParser;

public class Robot extends TimedRobot {

  @Override
  public void robotInit() {
    In.Init("XML/Controls_IN/GarryChassis.xml", "XML/Controls_IN/GarryShooter.xml");
    Out.Init("XML/Config_OUT/CHASSIS.xml", "XML/Config_OUT/SHOOTER.xml");
    Subsystems.add(new Chassis(), SubsystemID.CHASSIS);
    Subsystems.add(new Shooter(), SubsystemID.SHOOTER);
    Subsystems.add(new Arms(), SubsystemID.ARMS);
    Subsystems.add(new Intake(), SubsystemID.INTAKE);
    
    Subsystems.robotInit();
  }

  @Override
  public void robotPeriodic() {
    Subsystems.robotPeriodic();
  }

  @Override
  public void autonomousInit() {
    Subsystems.autonomousInit();
  }

  @Override
  public void autonomousPeriodic() {
    Subsystems.autonomousPeriodic();
  }

  @Override
  public void teleopInit() {
    Subsystems.teleopInit();
  }

  @Override
  public void teleopPeriodic() {
    Subsystems.teleopPeriodic();
  }

  @Override
  public void testPeriodic() {
  }
}