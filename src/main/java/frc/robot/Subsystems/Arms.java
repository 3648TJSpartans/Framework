package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Arms extends SubsystemBase {
    private In input = new In(SubsystemID.ARMS);
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
}