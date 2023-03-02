package frc.robot.framework.subsystems.Motor;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.util.CommandMode;

public class Motor_Set extends CommandBase implements RobotXML{
    
  private Motor motor;
  private Element myElement;
//<command  command="TankDrive_Default" scaleX="2" scaleY=".75"></axis>

    public Motor_Set(Element element){
        myElement=element;
            
        SubsystemBase temp=RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
        if (temp==null || !(temp instanceof Motor)){
            System.out.println("Motor_SetPower could not find Motor subsystem with id:"+ element.getAttribute("subSystemID"));
            return;
        }
        motor = (Motor)temp;
        this.addRequirements(motor);
    }
    
    @Override
    public void initialize(){
    }

    @Override
    public void execute() {

        if (myElement.hasAttribute("incrementPower") && motor.getMode()==CommandMode.PERCENTAGE){
            motor.setReference(motor.getReference()+Double.parseDouble(myElement.getAttribute("incrementPower")));
            return;
        }
        if (myElement.hasAttribute("setPower")){
            motor.setReference(Double.parseDouble(myElement.getAttribute("setPower")), CommandMode.PERCENTAGE);
            return;
        }
        if (myElement.hasAttribute("setVelocity")){
            motor.setReference(Double.parseDouble(myElement.getAttribute("setVelocity")), CommandMode.VELOCITY);
            return;
        }
        if (myElement.hasAttribute("setPosition")){
            motor.setReference(Double.parseDouble(myElement.getAttribute("setPosition")), CommandMode.POSITION);
            return;
        }
        if (myElement.hasAttribute("incrementPosition")){
            motor.setReference(motor.getReference()+Double.parseDouble(myElement.getAttribute("incrementPosition")), CommandMode.POSITION);
            return;
            
        }
    }
  
    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void ReadXML(Element element) {
      
    }
    
    @Override
    public void ReloadConfig() {
      
    }
}