package frc.robot.Framework;

public interface Subsystem{

    public void robotInit();
    public void robotPeriodic();

    public void autonomousInit();
    public void autonomousPeriodic();

    public void teleopInit();
    public void teleopPeriodic();
}