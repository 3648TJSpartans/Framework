package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Chassis implements Subsystem{
    private In input = new In(SubsystemID.CHASSIS);
    private Out output = new Out(SubsystemID.CHASSIS);

    public void robotInit(){
        System.out.println("Chassis init");
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
        //output.setMotor("shooter", input.getAxis("SHOOT", "OPERATOR"));
        //output.setMotor("LEFT_SIDE", input.getAxis("LEFT_SPEED", "DRIVE") * -0.5);
        //output.setMotor("RIGHT_SIDE", input.getAxis("RIGHT_SPEED", "DRIVE") * 0.5);
    }
}