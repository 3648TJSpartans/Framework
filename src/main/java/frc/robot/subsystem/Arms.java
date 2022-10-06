package frc.robot.subsystem;

import org.w3c.dom.Node;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.Out;
import frc.robot.framework.robot.RobotXML;

public class Arms extends SubsystemBase implements RobotXML{
    private Out output = new Out(SubsystemID.ARMS);

    public Arms() {
        System.out.println("Arms init");
    }

    public void robotPeriodic() {

    }

    public void autonomousInit() {

    }

    public void autonomousPeriodic() {

    }

    public void teleopInit() {

    }

    public void teleopPeriodic() {
        // output.motors.setVoltage("CLIMBER", input.getAxis("EXTEND", "DRIVE") * -0.5);
        // output.motors.setVoltage("CLIMBER", input.getAxis("RETRACT", "DRIVE") * 0.5);
    }

    @Override
    public void ReadXML(Node node) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }
}