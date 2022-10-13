package frc.robot.framework.subsystems.TankDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.util.CommandMode;
import frc.robot.framework.util.ShuffleboardHandler;
import frc.robot.framework.util.XMLUtil;
import frc.robot.framework.robot.RobotInit;

public class TankDrive extends SubsystemBase implements RobotXML{
    ShuffleboardHandler tab = new ShuffleboardHandler("TankDrive");
    private SubsystemCollection subsystemColection;
    String[] headers = {"Left Encoder", "Right Encoder", "Left Speed", "Right Speed", "Left Voltage", "Right Voltage", "Left Current", "Right Current"};

    public TankDrive(Element subsystem){
        ReadXML(subsystem);
    }
    
    @Override
    public void periodic(){
        System.out.println("TankDrive periodic" /*+ RobotInit.getAxis("","")*/);
    }

    @Override
    public void simulationPeriodic() {
    }

    @Override
    public void ReadXML(Element element) {
        XMLUtil.prettyPrint(element);
        subsystemColection = new SubsystemCollection(element);
        subsystemColection.motors.setMotor("left1", Math.random(), CommandMode.PERCENTAGE);
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }
}