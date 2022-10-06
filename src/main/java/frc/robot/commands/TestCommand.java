package frc.robot.commands;

import org.w3c.dom.Node;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.framework.robot.*;

public class TestCommand extends CommandBase implements RobotXML{
    
    private long start = System.currentTimeMillis();
    private boolean finished = false;

    public TestCommand(Node node){
    }
    
    public TestCommand(int a, int b, int c){
      System.out.println("testCommand Ans = "+(a+b+c));
    }
  
    public void aaa(){
        System.out.println("testCommand aaa");
    }
    public void bbb(int x, int y){
        System.out.println("testCommand x+y="+(x+y));
    }
    
    @Override
    public void execute() {
      var diff = System.currentTimeMillis()-start;
      System.out.println("testCommand Time taken:"+diff);
  
      start=System.currentTimeMillis();
      finished=true;
    }
  
    @Override
    public boolean isFinished() {
      return true;
    }

    @Override
    public void ReadXML(Node node) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void ReloadConfig() {
      // TODO Auto-generated method stub
      
    }
}