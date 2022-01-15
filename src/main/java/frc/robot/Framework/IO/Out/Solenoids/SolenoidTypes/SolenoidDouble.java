package frc.robot.Framework.IO.Out.Solenoids.SolenoidTypes;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Framework.IO.Out.Solenoids.SolenoidBase;

public class SolenoidDouble implements SolenoidBase{
    private DoubleSolenoid solenoid;

    public SolenoidDouble(int portOne, int portTwo){
        solenoid = new DoubleSolenoid(portOne, portTwo);
    }

    public void set(boolean extended){
        if(extended){
            solenoid.set(Value.kForward);
        }else{
            solenoid.set(Value.kReverse);
        }
    }
}