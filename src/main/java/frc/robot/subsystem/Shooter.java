package frc.robot.subsystem;

import org.w3c.dom.Node;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.Out;
import frc.robot.framework.robot.RobotXML;

public class Shooter extends SubsystemBase implements RobotXML{
    private Out output = new Out(SubsystemID.SHOOTER);

    public Shooter(){
        System.out.println("Shooter init");
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
    public void ReadXML(Node node) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }
}