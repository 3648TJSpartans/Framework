package frc.robot.framework.subsystems.Solenoid;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotInit;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.util.ShuffleboardFramework;
import frc.robot.framework.util.ShuffleboardFramework.ShuffleboardBase;

public class Solenoid extends SubsystemBase implements RobotXML {
    private ShuffleboardBase tab;
    private Element myElement;
    private SubsystemCollection subsystemColection;
    private boolean extended;

    public Solenoid(Element element, String subsystem) {
        tab = ShuffleboardFramework.getSubsystem(subsystem);
        ReadXML(element);
        myElement = element;

    }

    @Override
    public void periodic() {
        for (String solenoidId : subsystemColection.solenoids.GetAllSolenoidIDs()) {
            subsystemColection.solenoids.setSolenoid(solenoidId, extended);
        }
    }

    public void setExtended(boolean input_extended) {
         extended= input_extended;
    }

    @Override
    public void simulationPeriodic() {
    }

    @Override
    public void ReadXML(Element element) {
        System.out.println(element);
        subsystemColection = new SubsystemCollection(element);
    }

    @Override
    public void ReloadConfig() {
        

    }
}