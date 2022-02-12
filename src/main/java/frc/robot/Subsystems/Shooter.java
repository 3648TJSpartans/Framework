package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Shooter implements Subsystem{
    private In input = new In(SubsystemID.SHOOTER);
    private Out output = new Out(SubsystemID.SHOOTER);
    //private Sensor sensor = new Sensor()

    public void robotInit(){
        System.out.println("Shooter init");
    }

    public void robotPeriodic(){

    }

    public void autonomousInit(){
        
    }
    public void autonomousPeriodic(){
        // if(Timer.getMatchTime()<3){
        //     System.out.print("testing");
        //     output.setMotor("shooter", -0.5);
        // }else{
        //     System.out.print("done");
        // }
    }

    public void teleopInit(){

    }

    public void teleopPeriodic(){
        //output.setMotor("shooter", input.getAxis("SHOOT", "OPERATOR") *  -0.55);
        //output.setMotor("TURRET_AIM", input.getAxis("TURRET_AIM", "OPERATOR")* 0.5);
    }
}