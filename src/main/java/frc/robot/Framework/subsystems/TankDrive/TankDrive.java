package frc.robot.framework.subsystems.TankDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.util.ShuffleboardHandler;

public class TankDrive extends SubsystemBase implements RobotXML{
    ShuffleboardHandler tab = new ShuffleboardHandler("TankDrive");

    public TankDrive(Element subsystem){
        ReadXML(subsystem);
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
        System.out.println(element);
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }
}