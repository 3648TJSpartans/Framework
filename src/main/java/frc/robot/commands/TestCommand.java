package frc.robot.commands;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.framework.robot.*;

public class TestCommand extends CommandBase implements RobotXML{
    
    private String text="noo";

    public TestCommand(Element element){
      ReadXML(element);
    }
    
    public void aaa(){
        System.out.println("testCommand aaa");
    }
    public void bbb(int x, int y){
        System.out.println("testCommand x+y="+(x+y));
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
      System.out.println("Createing Testcommand with XML - test is:"+text);
    }
    
    @Override
    public void ReloadConfig() {
      // TODO Auto-generated method stub
      
    }
}