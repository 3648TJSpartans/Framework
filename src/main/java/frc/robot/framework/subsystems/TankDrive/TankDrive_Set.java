package frc.robot.framework.subsystems.TankDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.util.CommandMode;

public class TankDrive_Set extends CommandBase implements RobotXML {

    private TankDrive tankDrive;
    private Element myElement;
    private double startTime;
    private double delayLength = 0;
    // <command command="TankDrive_Default" scaleX="2" scaleY=".75"></axis>

    public TankDrive_Set(Element element) {
        myElement = element;

        SubsystemBase temp = RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
        addRequirements(temp);
        if (temp == null || !(temp instanceof TankDrive)) {
            System.out.println(
                    "TankDrive_SetPower could not find Motor subsystem with id:" + element.getAttribute("subSystemID"));
            return;
        }
        tankDrive = (TankDrive) temp;
    }

    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        delayLength = Double.parseDouble((myElement.getAttribute("delayLength")));
        

        if (myElement.hasAttribute("setInputForward")) {
            tankDrive.setInputForward(Double.parseDouble(myElement.getAttribute("setInputForward")));
        }
        if (myElement.hasAttribute("setInputTurn")) {
            tankDrive.setInputTurn(Double.parseDouble(myElement.getAttribute("setInputTurn")));
        }
    }

    @Override
    public boolean isFinished() {
        System.out.println("Time Complete: " + (System.currentTimeMillis() - startTime) + " " + (delayLength * 1000)
                + (System.currentTimeMillis() - startTime > delayLength * 1000));
        if (System.currentTimeMillis() - startTime > delayLength * 1000) {
            return true;
        }
        return false;
    }

    @Override
    public void ReadXML(Element element) {

    }

    @Override
    public void ReloadConfig() {

    }
}