package frc.robot.framework.solenoid;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class SolenoidSingle extends SolenoidController implements SolenoidBase{
    private Solenoid solenoid;

    public SolenoidSingle(PneumaticsModuleType moduleType, int port){

        solenoid = new Solenoid(moduleType, port);
    }

    public void set(boolean extended){
        if (inverted)
            extended = !extended;
        solenoid.set(extended);
    }

    @Override
    public void setInverted(boolean invert) {
        super.setInverted(invert);
        
    }
}