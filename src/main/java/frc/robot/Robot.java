package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.framework.robot.RobotInit;

public class Robot extends TimedRobot{

  @Override
  public void robotInit() {
    RobotInit.Init();
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
    CommandScheduler.getInstance().run();
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

  @Override
  public void disabledPeriodic(){

  }
  
  @Override
  public void simulationPeriodic(){
    
  }
}
