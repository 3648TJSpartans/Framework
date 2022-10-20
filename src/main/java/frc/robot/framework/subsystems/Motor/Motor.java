package frc.robot.framework.subsystems.Motor;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.util.ShuffleboardHandler;

public class Motor extends SubsystemBase implements RobotXML{
    ShuffleboardHandler tab;

    public Motor(Element subsystem){
        tab= new ShuffleboardHandler(subsystem.getAttribute("id"));
        ReadXML(subsystem);
    }
    
    @Override
    public void periodic(){
        //System.out.println("Motor periodic");
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