package frc.robot.framework.io.out.solenoid;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class SolenoidSingle implements SolenoidBase{
    private Solenoid solenoid;

    public SolenoidSingle(PneumaticsModuleType moduleType, int port){

        solenoid = new Solenoid(moduleType, port);
    }

    public void set(boolean extended){
        solenoid.set(extended);
    }
}