package frc.robot.subsystem;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.util.ShuffleboardHandler;

public class Chassis extends SubsystemBase implements RobotXML{
    //private Out output = new Out(subsystemName.CHASSIS);
    ShuffleboardHandler tab = new ShuffleboardHandler("CHASSIS");
    public Chassis(){
        System.out.println("Chassis init");
    }
    
    @Override
    public void periodic(){
        //System.out.println("TankDrive periodic");
    }

    @Override
    public void simulationPeriodic() {
    }
        //SmartDashboard.putBoolean("test", output.sensors.getDIO("switch1"));
        
        // output.motors.setMotor("LEFT_LEAD", input.getAxis("LEFT_SPEED", "DRIVE") * -0.5);
        // output.motors.setMotor("LEFT_FOLLOW", input.getAxis("LEFT_SPEED", "DRIVE") * -0.5);
        // output.motors.setMotor("RIGHT_FOLLOW", input.getAxis("RIGHT_SPEED", "DRIVE") * 0.5);
        // output.motors.setMotor("RIGHT_LEAD", input.getAxis("RIGHT_SPEED", "DRIVE") * 0.5);
        
        //output.motors.setMotor("LEFT_SIDE", input.getAxis("LEFT_SPEED", "DRIVE") * -1);
        //output.motors.setMotor("RIGHT_SIDE", input.getAxis("RIGHT_SPEED", "DRIVE"));

    @Override
    public void ReadXML(Element element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }
}