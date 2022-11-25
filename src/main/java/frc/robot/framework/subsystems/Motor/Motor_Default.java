package frc.robot.framework.subsystems.Motor;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;

public class Motor_Default extends CommandBase implements RobotXML{
    
  private Element myElement;
  private Motor motorSubsystem;
  private ControllerBase myController;
  private double deadzone=.05;
  
  private int axisNumberPower=-1;

    public Motor_Default(Element element, ControllerBase controller){
        myElement=element;
        myController=controller;
        
            
        SubsystemBase temp=RobotInit.GetSubsystem(myElement.getAttribute("subsystemID"));
        if (temp==null || !(temp instanceof Motor)){
            System.out.println("Motor_Default could not find Motor subsystem with id:"+ myElement.getAttribute("subSystemID"));
            return;
        }
        motorSubsystem = (Motor)temp;

        this.addRequirements(motorSubsystem);
        CommandScheduler.getInstance().setDefaultCommand(motorSubsystem, this);

       if (element.hasAttribute("deadzone"))
        deadzone= Double.parseDouble(element.getAttribute("deadzone"));
      if (element.hasAttribute("axisReference"))
        axisNumberPower= myController.GetAxisMap().get(element.getAttribute("axisReference"));
        
    }
    
    
    @Override
    public void execute() {
      double input=myController.getAxis(axisNumberPower);
      if (Math.abs(input)<deadzone)
        motorSubsystem.setReference(input);
      else
        motorSubsystem.setReference(input);
    }
  
    @Override
    public boolean isFinished() {
      return false;
    }

    @Override
    public void ReadXML(Element element) {
      
    }
    
    @Override
    public void ReloadConfig() {
      
    }
}