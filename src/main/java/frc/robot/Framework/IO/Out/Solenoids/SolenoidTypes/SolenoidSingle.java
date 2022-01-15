package frc.robot.Framework.IO.Out.Solenoids.SolenoidTypes;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Framework.IO.Out.Solenoids.SolenoidBase;

public class SolenoidSingle implements SolenoidBase{
    private Solenoid solenoid;

    public SolenoidSingle(int port){
        solenoid = new Solenoid(port);
    }

    public void set(boolean extended){
        solenoid.set(extended);
    }
}