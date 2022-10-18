package frc.robot.framework.subsystems.TankDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.util.ShuffleboardHandler;

public class TankDrive extends SubsystemBase implements RobotXML{
    ShuffleboardHandler tab;
    private SubsystemCollection subsystemColection;
    private double input_forward=0;
    private double input_turn=0;
    private double input_left=0;
    private double input_right=0;

    String[] headers = {"Left Encoder", "Right Encoder", "Left Speed", "Right Speed", "Left Voltage", "Right Voltage", "Left Current", "Right Current"};

    public TankDrive(Element subsystem){
        tab= new ShuffleboardHandler(subsystem.getAttribute("id"));
        ReadXML(subsystem);
    }
    
    @Override
    public void periodic(){
        if(Math.random()>.9){
            System.out.println("Forward:"+ input_forward+" turn:"+ input_turn + " left:"+input_left+ " right:"+input_right);
        }
    }

    public void setInputForward(double forward){
        input_forward=forward;
    }

    public void setInputTurn(double turn){
        input_turn=turn;
    }

    public void setInputLeft(double left){
        input_left=left;
    }

    public void setInputRight(double right){
        input_right=right;
    }

    @Override
    public void simulationPeriodic() {
    }

    @Override
    public void ReadXML(Element element) {
        //XMLUtil.prettyPrint(element);
        subsystemColection = new SubsystemCollection(element);
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }
}