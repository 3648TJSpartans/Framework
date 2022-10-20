package frc.robot.commands;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.framework.robot.*;

public class TestCommand extends CommandBase implements RobotXML{
    
    private String text="noo";

    public TestCommand(Element element){
      ReadXML(element);
    }
    
    @Override
    public void execute() {
      System.out.println("testCommand: "+text);
    }
  
    @Override
    public boolean isFinished() {
      return true;
    }

    @Override
    public void ReadXML(Element element) {
      text=element.getAttribute("myParam");
    }
    
    @Override
    public void ReloadConfig() {
      // TODO Auto-generated method stub
      
    }
}