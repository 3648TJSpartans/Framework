package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Intake implements Subsystem{
    private In input = new In(SubsystemID.INTAKE);
    private Out output = new Out(SubsystemID.INTAKE);

    public void robotInit(){
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