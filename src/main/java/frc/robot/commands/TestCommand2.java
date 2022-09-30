package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;

public class TestCommand2 extends CommandBase{
    
    private long start = System.currentTimeMillis();
    public TestCommand2(){
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
  
      start=System.currentTimeMillis();
    }
  
    @Override
    public boolean isFinished() {
      return true;
    }
}