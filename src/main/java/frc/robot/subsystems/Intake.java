package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

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