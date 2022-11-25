package frc.robot.framework.subsystems.Motor;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.util.CommandMode;
import frc.robot.framework.util.ShuffleboardHandler;

public class Motor extends SubsystemBase implements RobotXML{
    ShuffleboardHandler tab;
    private SubsystemCollection subsystemColection;
    private double output=.03;
    private CommandMode mode = CommandMode.PERCENTAGE;

    public Motor(Element subsystem){
        tab= new ShuffleboardHandler(subsystem.getAttribute("id"));
        ReadXML(subsystem);
    }

    public CommandMode getMode(){
        return mode;
    }

    public void setReference(double reference, CommandMode mode){
        this.output=output;
        this.mode=mode;
    }

    public void setReference(double reference){
        this.output=output;
    }

    public double getOutput(){
        return output;
    }
    
    @Override
    public void periodic(){
        if (subsystemColection.encoders.GetAllEncoderIDs().size()>0 && Math.random()>.9){
            String motorID = subsystemColection.motors.GetAllMotorIDs().iterator().next();
            System.out.println("Position:"+subsystemColection.motors.getPosition(motorID)+
                " Velocity:"+subsystemColection.motors.getVelocity(motorID));
        }

        for (String motorId: subsystemColection.motors.GetAllMotorIDs()){
            subsystemColection.motors.setOutput(motorId, output, mode);
        }
    }

    @Override
    public void simulationPeriodic() {
    }

    @Override
    public void ReadXML(Element element) {
        //XMLUtil.prettyPrint(element);
        subsystemColection = new SubsystemCollection(element);
        if (element.hasAttribute("commandMode")){
            switch (element.getAttribute("commandMode")) {
                case "power":
                    mode=CommandMode.PERCENTAGE;
                    break;
                case "velocity":
                    mode=CommandMode.VELOCITY;
                    break;
                case "position":
                    mode=CommandMode.POSITION;
                default:
                    System.out.println("Motor subsystem does not support commandMode:"+ element.getAttribute("commandMode"));
                    break;
            }
            if (element.hasAttribute("output")){
                output=Double.parseDouble(element.getAttribute("output"));
            }
        }
        else{
            mode=CommandMode.PERCENTAGE;
        }
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }
}