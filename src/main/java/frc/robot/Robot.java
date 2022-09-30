package frc.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.sql.rowset.spi.XmlReader;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;

import java.util.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;
import frc.robot.Framework.Util.XMLMerger;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.subsystems.*;

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
    HashMap<String,Class> commandClasses = new HashMap<>();
    var packageName = "frc.robot.subsystems.commands";
    try {
      var classes = frc.robot.Framework.Util.ReflectionUtil.findAllClassesUsingClassLoader(packageName);
      for (Class<?> myClass : classes) {
        commandClasses.put(myClass.getName(), myClass);
        System.out.println(myClass.getName());
        Class<?>[] nullClass=null;
        //myClass.getDeclaredConstructor( new Class<?>[]{int.class, int.class, int.class}).newInstance(1,2,3)
        var temp = (Command)(myClass.getDeclaredConstructor(nullClass).newInstance(nullClass));
        for (Method m : myClass.getDeclaredMethods()){
            if (Modifier.isPublic(m.getModifiers()) && m.getName().contains("execute")){
              m.invoke(temp,null);
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