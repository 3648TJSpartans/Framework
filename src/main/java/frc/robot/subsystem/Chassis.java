package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.io.in.In;
import frc.robot.framework.io.out.Out;
import frc.robot.framework.util.ShuffleboardHandler;

public class Chassis extends SubsystemBase{
    private In input = new In(SubsystemID.CHASSIS);
    private Out output = new Out(SubsystemID.CHASSIS);
    ShuffleboardHandler tab = new ShuffleboardHandler("CHASSIS");
    public Chassis(){
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

        //SmartDashboard.putBoolean("test", output.sensors.getDIO("switch1"));
        
        // output.motors.setMotor("LEFT_LEAD", input.getAxis("LEFT_SPEED", "DRIVE") * -0.5);
        // output.motors.setMotor("LEFT_FOLLOW", input.getAxis("LEFT_SPEED", "DRIVE") * -0.5);
        // output.motors.setMotor("RIGHT_FOLLOW", input.getAxis("RIGHT_SPEED", "DRIVE") * 0.5);
        // output.motors.setMotor("RIGHT_LEAD", input.getAxis("RIGHT_SPEED", "DRIVE") * 0.5);
        
        output.motors.setMotor("LEFT_SIDE", input.getAxis("LEFT_SPEED", "DRIVE") * -1);
        output.motors.setMotor("RIGHT_SIDE", input.getAxis("RIGHT_SPEED", "DRIVE"));
    }
}