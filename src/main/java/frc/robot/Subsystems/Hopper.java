package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Hopper implements Subsystem{
    private In input = new In(SubsystemID.HOPPER);
    private Out output = new Out(SubsystemID.HOPPER);

    public void robotInit(){
        System.out.println("Hopper init");
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