package frc.robot.framework.solenoid;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SolenoidDouble extends SolenoidController implements SolenoidBase{
    private DoubleSolenoid solenoid;

    public SolenoidDouble(PneumaticsModuleType moduleType, int portOne, int portTwo){
        solenoid = new DoubleSolenoid(moduleType, portOne, portTwo);
    }

    public void set(boolean extended){
        if(inverted)
            extended = !extended;
        if(extended){
            solenoid.set(Value.kForward);
        }else{
            solenoid.set(Value.kReverse);
        }
    }

    @Override
    public void setInverted(boolean invert) {
        super.setInverted(inverted);        
    }
}