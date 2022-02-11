package frc.robot.Framework.IO.Out.Sensors.SensorTypes.Ultrasonic;

import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.Framework.IO.Out.Sensors.SensorBase;

public class Ping_Ultrasonic implements SensorBase, UltrasonicBase{
    Ultrasonic m_ultrasonic;
    public Ping_Ultrasonic(int port1, int port2){
        m_ultrasonic = new Ultrasonic(port1, port2);
    }
    public void test(){
        //m_ultrasonic.
    }
    public double getRangeInches(){
        return m_ultrasonic.getRangeInches();
    }
    public double getRangeMM(){
        return m_ultrasonic.getRangeMM();
    }
    public int getEchoChannel(){
        return m_ultrasonic.getEchoChannel();
    }

}
