package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.ADIS16448_IMU.CalibrationTime;
import edu.wpi.first.wpilibj.ADIS16448_IMU.IMUAxis;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        //output
        IMUAxis yaw_axis;
        Port port;
        CalibrationTime cal_time;
        //ADIS16448_IMU gyro = new ADIS16448_IMU(yaw_axis, port, cal_time);
        //output.setMotor("shooter", input.getAxis("SHOOT", "OPERATOR"));
        output.setMotor("LEFT_SIDE", input.getAxis("LEFT_SPEED", "DRIVE") * -0.5);
        output.setMotor("RIGHT_SIDE", input.getAxis("RIGHT_SPEED", "DRIVE") * 0.5);
        //System.out.print("yes");
        //System.out.print(output.getDioPressed("switch1").toString());
        // SmartDashboard.putBoolean("test", output.getDioPressed("switch1"));
        // if(output.getDioPressed("switch1")){
        //     output.setMotor("LEFT_SIDE", input.getAxis("LEFT_SPEED", "DRIVE") * -0.5);
        //     output.setMotor("RIGHT_SIDE", input.getAxis("RIGHT_SPEED", "DRIVE") * 0.5);
        //     System.out.print("yes");
        // }
    }
}