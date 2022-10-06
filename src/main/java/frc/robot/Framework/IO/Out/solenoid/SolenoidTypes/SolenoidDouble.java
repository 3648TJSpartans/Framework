package frc.robot.framework.io.out.solenoid.SolenoidTypes;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.framework.io.out.solenoid.SolenoidBase;

public class SolenoidDouble implements SolenoidBase{
    private DoubleSolenoid solenoid;

    public SolenoidDouble(PneumaticsModuleType moduleType, int portOne, int portTwo){
        solenoid = new DoubleSolenoid(moduleType, portOne, portTwo);
    }

    public void set(boolean extended){
        if(extended){
            solenoid.set(Value.kForward);
        }else{
            solenoid.set(Value.kReverse);
        }
    }
}