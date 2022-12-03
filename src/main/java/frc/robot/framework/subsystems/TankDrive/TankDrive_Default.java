package frc.robot.framework.subsystems.TankDrive;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;

public class TankDrive_Default extends CommandBase implements RobotXML{
    
  private Element myElement;
  private TankDrive tankDrivSubsystem;
  private ControllerBase myController;

  private int axisNumberTurn=-1;
  private int axisNumberForward=-1;
  private int axisNumberLeft=-1;
  private int axisNumberRight=-1;
  
//<defaultCommand subsystem="TankDrive" axisForward="LEFT_JOYSTICK_Y" axisTurn="RIGHT_JOYSTICK_X" command="TankDrive_Default" scaleX="2" scaleY=".75"></axis>

    public TankDrive_Default(Element element, ControllerBase controller){
      myElement=element;
      myController=controller;
      
      SubsystemBase temp=RobotInit.GetSubsystem(element.getAttribute("subsystemID"));
      if (temp==null || !(temp instanceof TankDrive)){
        System.out.println("TankDrive_Default could not find TankDrive subsystem with id:"+ element.getAttribute("subSystemID"));
        return;
      }
      tankDrivSubsystem = (TankDrive) temp;
      this.addRequirements(tankDrivSubsystem);

      CommandScheduler.getInstance().setDefaultCommand(tankDrivSubsystem, this);
      if (myElement.getAttribute("axisForward")!="")
        axisNumberForward= myController.GetAxisMap().get(myElement.getAttribute("axisForward"));
      if (myElement.getAttribute("axisTurn")!="")
        axisNumberTurn= myController.GetAxisMap().get(myElement.getAttribute("axisTurn"));
      if (myElement.getAttribute("axisLeft")!="")
        axisNumberLeft= myController.GetAxisMap().get(myElement.getAttribute("axisLeft"));
      if (myElement.getAttribute("axisRight")!="")
        axisNumberRight= myController.GetAxisMap().get(myElement.getAttribute("axisRight"));
    }
    
    
    @Override
    public void execute() {
      if (axisNumberTurn!=-1)
        tankDrivSubsystem.setInputTurn(myController.getAxis(axisNumberTurn));
      if (axisNumberForward!=-1)
        tankDrivSubsystem.setInputForward(myController.getAxis(axisNumberForward));
      if (axisNumberLeft!=-1)
        tankDrivSubsystem.setInputLeft(myController.getAxis(axisNumberLeft));
      if (axisNumberRight!=-1)
          tankDrivSubsystem.setInputRight(myController.getAxis(axisNumberRight));
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