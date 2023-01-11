package frc.robot.framework.subsystems.TankDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.util.CommandMode;

public class TankDrive_Set extends CommandBase implements RobotXML {

    private TankDrive tankDrive;
    private Element myElement;
    // <command command="TankDrive_Default" scaleX="2" scaleY=".75"></axis>

    public TankDrive_Set(Element element) {
        myElement = element;

        SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
        if (temp == null || !(temp instanceof TankDrive)) {
            System.out.println(
                    "TankDrive_SetPower could not find Motor subsystem with id:" + element.getAttribute("subSystemID"));
            return;
        }
        tankDrive = (TankDrive) temp;
    }

    @Override
    public void execute() {
        if (myElement.hasAttribute("setInputForward")) {
            tankDrive.setInputForward(Double.parseDouble(myElement.getAttribute("setInputForward")));
            return;
        }
        if (myElement.hasAttribute("setInputTurn")) {
            tankDrive.setInputForward(Double.parseDouble(myElement.getAttribute("setInputTurn")));
            return;
        }
        if (myElement.hasAttribute("setInputLeft")) {
            tankDrive.setInputForward(Double.parseDouble(myElement.getAttribute("setInputLeft")));
            return;
        }
        if (myElement.hasAttribute("setInputRight")) {
            tankDrive.setInputForward(Double.parseDouble(myElement.getAttribute("setInputRight")));
            return;
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