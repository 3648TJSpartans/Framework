package frc.robot.Framework.IO.Out.Servos;

import java.util.ArrayList;

import frc.robot.Framework.IO.Out.Servos.ServoBase;
import frc.robot.Framework.IO.Out.Servos.ServoWrapper;
import frc.robot.Framework.Util.CommandMode;

public class ServoGroup implements ServoBase {
    private ArrayList<ServoWrapper> servos = new ArrayList<>();

    public ServoGroup() {
    };

    public void addMotor(ServoWrapper newMotor) {
        servos.add(newMotor);
    }

    public void setAngle(int angle) {
        for (int i = 0; i < servos.size(); i++) {
            servos.get(i).setAngle(angle);
        }
    }

    public void set(double position) {
        for (int i = 0; i < servos.size(); i++) {
            servos.get(i).set(position);
        }
    }

    
}