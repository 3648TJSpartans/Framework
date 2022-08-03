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
        output.motors.setMotor("uptake_lower", input.getAxis("UPTAKE_LOWER", "OPERATOR") * -0.5);
        output.motors.setMotor("uptake_upper", input.getAxis("UPTAKE_UPPER", "OPERATOR") * -0.5);
    }
}