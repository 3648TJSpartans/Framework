package frc.robot.subsystem;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;

public class Arms extends SubsystemBase implements RobotXML{
    //private Out output = new Out(subsystemName.ARMS);

    public Arms() {
        System.out.println("Arms init");
    }

    @Override
    public void periodic(){
        //System.out.println("TankDrive periodic");
    }

    @Override
    public void simulationPeriodic() {
        
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