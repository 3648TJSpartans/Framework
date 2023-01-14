package frc.robot.framework.subsystems.Solenoid;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.util.CommandMode;

public class Solenoid_Set extends CommandBase implements RobotXML {

    private Solenoid solenoid;
    private Element myElement;
    private double startTime;
    private double delayLength = 0;
    // <command command="TankDrive_Default" scaleX="2" scaleY=".75"></axis>

    public Solenoid_Set(Element element) {
        myElement = element;
        SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
        if (temp == null || !(temp instanceof Solenoid)) {
            System.out
                    .println("Solenoid could not find Motor subsystem with id:" + element.getAttribute("subSystemID"));
            return;
        }
    }

    @Override
    public void execute() {
        if (myElement.hasAttribute("extended")) {
            solenoid.setExtended(Boolean.parseBoolean(myElement.getAttribute("extended")));
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void ReadXML(Element element) {

    }

    @Override
    public void ReloadConfig() {

    }
}