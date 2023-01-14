package frc.robot.framework.subsystems.Solenoid;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotInit;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.util.ShuffleboardHandler;

public class Solenoid extends SubsystemBase implements RobotXML {
    ShuffleboardHandler tab;
    Element myElement;
    private SubsystemCollection subsystemColection;
    boolean exstended;

    public Solenoid(Element element) {
        tab = new ShuffleboardHandler(element.getAttribute("id"));
        ReadXML(element);
        myElement = element;

    }

    @Override
    public void periodic() {
        if (exstended) {
            subsystemColection.solenoids.setSolenoid(myElement.getAttribute("id"), true);
        } else {
            subsystemColection.solenoids.setSolenoid(myElement.getAttribute("id"), false);
        }
    }

    public void setExtended(boolean input_exstended) {
        input_exstended = exstended;
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
        // TODO Auto-generated method stub

    }
}