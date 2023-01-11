package frc.robot.commands;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.*;

import frc.robot.framework.robot.*;

public class TestCommand3 extends CommandBase implements RobotXML{
    
    private long start = System.currentTimeMillis();
    private Element myElement;
    private int motorID;
    public TestCommand3(Element element){
      myElement = element;
      ReadXML(element);
      if (element.hasAttribute("motor"))
        motorID = Integer.parseInt(element.getAttribute("motor"));
    }

    
    public void aaa(){
        if (myElement.hasAttribute("motor"))
          myElement.getAttribute("motor");
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


    @Override
    public void ReadXML(Element element) {
      // TODO Auto-generated method stub
      
    }


    @Override
    public void ReloadConfig() {
      // TODO Auto-generated method stub
      
    }
}