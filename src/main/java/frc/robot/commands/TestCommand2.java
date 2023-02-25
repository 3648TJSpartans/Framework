package frc.robot.commands;

import org.w3c.dom.Element;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.*;

import frc.robot.framework.robot.*;

public class TestCommand2 extends CommandBase implements RobotXML{
  private long start;
    
    public TestCommand2(Element element){
      ReadXML(element);
    }

    @Override
    public void initialize(){
      start = System.currentTimeMillis();

    }

    
    public void aaa(){
        System.out.println("testCommand aaa");
    }
    public void bbb(int x){
        System.out.println("testCommand x ="+x);
    }

    String ccc(){
      return "a"+Math.random();
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

    @Override
    public void initSendable(SendableBuilder builder){
      builder.addStringProperty("zxvc", this::ccc, null);
      builder.addStringProperty(".name", this::getName, null);
    }


    @Override
    public void ReadXML(Element element) {
      
      
    }


    @Override
    public void ReloadConfig() {
      
      
    }
}