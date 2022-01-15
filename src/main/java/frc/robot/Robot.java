package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Framework.Subsystems;
import frc.robot.Subsystems.*;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Robot extends TimedRobot {

  @Override
  public void robotInit() {
    In.Init("XML/Controls/John.xml");
    Out.Init("XML/Configurations/Prodbot.xml");
    Subsystems.add(new Chassis(), SubsystemID.CHASSIS);
    Subsystems.add(new Shooter(), SubsystemID.SHOOTER);
    Subsystems.add(new Arms(), SubsystemID.ARMS);
    Subsystems.add(new Intake(), SubsystemID.INTAKE);
    Subsystems.add(new Hopper(), SubsystemID.HOPPER);
    //Subsystems.add(new ColorWheel(), SubsystemID.PANEL);
    
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