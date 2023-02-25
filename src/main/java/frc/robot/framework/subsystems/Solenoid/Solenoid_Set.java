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
    private boolean extended;

    public Solenoid_Set(Element element) {
        myElement = element;
        extended = Boolean.parseBoolean(myElement.getAttribute("extended"));

        SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
        if (temp == null || !(temp instanceof Solenoid)) {
            System.out.println("Solenoid could not find Solenoid subsystem with id:" + element.getAttribute("subsystemID"));
            return;
        }
        solenoid = (Solenoid) temp;
        this.addRequirements(solenoid);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute() {
            solenoid.setExtended(Boolean.parseBoolean(myElement.getAttribute("extended")));
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