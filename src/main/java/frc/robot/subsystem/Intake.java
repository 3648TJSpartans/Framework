package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.io.in.In;
import frc.robot.framework.io.out.Out;

public class Intake extends SubsystemBase{
    private In input = new In(SubsystemID.INTAKE);
    private Out output = new Out(SubsystemID.INTAKE);

    public Intake(){
        System.out.println("Intake init");
    }

    public void robotPeriodic(){

    }

    public void autonomousInit(){

    }
    public void autonomousPeriodic(){
        
    }

    public void teleopInit(){

    }

    public void teleopPeriodic(){
        
    }
}