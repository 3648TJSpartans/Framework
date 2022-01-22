package frc.robot.Framework;

/**
 * [Subsystem] is an interface for robot subsystems. Enforces implementation of
 * all robot lifecycle methods.
 */

public interface Subsystem {

    public void robotInit();

    public void robotPeriodic();

    public void autonomousInit();

    public void autonomousPeriodic();

    public void teleopInit();

    public void teleopPeriodic();
}