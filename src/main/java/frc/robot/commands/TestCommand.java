package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TestCommand extends CommandBase{
    
    private long start = System.currentTimeMillis();
    private boolean finished = false;

    public TestCommand(){
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
}