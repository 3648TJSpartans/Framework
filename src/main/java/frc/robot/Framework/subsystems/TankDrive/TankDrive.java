package frc.robot.framework.subsystems.TankDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.util.ShuffleboardHandler;

public class TankDrive extends SubsystemBase implements RobotXML{
    ShuffleboardHandler tab = new ShuffleboardHandler("TankDrive");

    public TankDrive(){
        System.out.println("TankDrive init");
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

    @Override
    public void ReadXML(Element element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }
}