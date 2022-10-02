package frc.robot;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.io.in.In;
import frc.robot.framework.io.out.Out;
import frc.robot.subsystem.Arms;
import frc.robot.subsystem.Chassis;
import frc.robot.subsystem.Intake;
import frc.robot.subsystem.Shooter;

public class Robot extends TimedRobot {
  ArrayList<SubsystemBase> subsystems = new ArrayList<>();

  @Override
  public void robotInit() {
    In.Init("XML/Controls_IN/GarryChassis.xml", "XML/Controls_IN/GarryShooter.xml");
    Out.Init("XML/Config_OUT/CHASSIS.xml", "XML/Config_OUT/SHOOTER.xml");
    subsystems.add(new Chassis());
    subsystems.add(new Shooter());
    subsystems.add(new Arms());
    subsystems.add(new Intake());
  }

  public void CommandThings(){
    HashMap<String,Class<?>> commandClasses = new HashMap<>();
    var packageName = "frc.robot.subsystems.commands";
    try {
      var classes = frc.robot.framework.util.ReflectionUtil.findAllClassesUsingClassLoader(packageName);
      for (Class<?> myClass : classes) {
        commandClasses.put(myClass.getName(), myClass);
        System.out.println(myClass.getName());
        Class<?>[] nullClass=null;
        //myClass.getDeclaredConstructor( new Class<?>[]{int.class, int.class, int.class}).newInstance(1,2,3)
        Object[] nullParameters=null;
        var temp = (Command)(myClass.getDeclaredConstructor(nullClass).newInstance(nullParameters));
        for (Method m : myClass.getDeclaredMethods()){
            if (Modifier.isPublic(m.getModifiers()) && m.getName().contains("execute")){
              m.invoke(temp,nullParameters);
            }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testPeriodic() {
  }
}