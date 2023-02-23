package frc.robot.commands;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.*;

import frc.robot.framework.robot.*;

public class TestCommand2 extends CommandBase implements RobotXML{
    
    private long start = System.currentTimeMillis();
    public TestCommand2(Element element){
      ReadXML(element);
    }

    
    public void aaa(){
        System.out.println("testCommand aaa");
    }
    public void bbb(int x){
        System.out.println("testCommand x ="+x);
    }
    
    @Override
    public void execute() {
      var diff = System.currentTimeMillis()-start;
      System.out.println("testCommand Time taken:"+diff);
      aaa();
      start=System.currentTimeMillis();
    }
  
    @Override
    public boolean isFinished() {
      return true;
    }


    @Override
    public void ReadXML(Element element) {
      // TODO Auto-generated method stub
      
    }


    @Override
    public void ReloadConfig() {
      // TODO Auto-generated method stub
      
    }
}