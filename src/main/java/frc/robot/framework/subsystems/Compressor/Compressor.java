package frc.robot.framework.subsystems.Compressor;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.util.ShuffleboardHandler;

public class Compressor extends SubsystemBase implements RobotXML{
    ShuffleboardHandler tab;
    edu.wpi.first.wpilibj.Compressor compressor;

    public Compressor(Element subsystem){
        tab= new ShuffleboardHandler(subsystem.getAttribute("id"));
        ReadXML(subsystem);
    }
    @Override
    public void simulationPeriodic() {
    }

    @Override
    public void ReadXML(Element element) {
        switch (element.getAttribute("moduleType").toLowerCase()) {
            case "ctrepcm":
                compressor=new edu.wpi.first.wpilibj.Compressor(PneumaticsModuleType.CTREPCM);
                break;
            case "revph":
                compressor=new edu.wpi.first.wpilibj.Compressor(PneumaticsModuleType.REVPH);
                break;
            default:
                System.out.println("Invalid compressor module name:"+element.getAttribute("moduleType"));
                break;
        }
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }
}