package frc.robot.Subsystems;

import java.util.Set;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.ADIS16448_IMU.CalibrationTime;
import edu.wpi.first.wpilibj.ADIS16448_IMU.IMUAxis;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;
import frc.robot.Framework.Util.ShuffleboardHandler;

public class Chassis implements Subsystem{
    private In input = new In(SubsystemID.CHASSIS);
    private Out output = new Out(SubsystemID.CHASSIS);
    ShuffleboardHandler tab = new ShuffleboardHandler("CHASSIS");
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

        //SmartDashboard.putBoolean("test", output.sensors.getDIO("switch1"));
        
        // output.motors.setMotor("LEFT_LEAD", input.getAxis("LEFT_SPEED", "DRIVE") * -0.5);
        // output.motors.setMotor("LEFT_FOLLOW", input.getAxis("LEFT_SPEED", "DRIVE") * -0.5);
        // output.motors.setMotor("RIGHT_FOLLOW", input.getAxis("RIGHT_SPEED", "DRIVE") * 0.5);
        // output.motors.setMotor("RIGHT_LEAD", input.getAxis("RIGHT_SPEED", "DRIVE") * 0.5);
        
        output.motors.setMotor("LEFT_SIDE", input.getAxis("LEFT_SPEED", "DRIVE") * -0.5);
        output.motors.setMotor("RIGHT_SIDE", input.getAxis("RIGHT_SPEED", "DRIVE") * 0.5);
    }
}