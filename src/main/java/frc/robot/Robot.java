package frc.robot;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.util.XMLParser;
import frc.robot.subsystem.*;

public class Robot extends TimedRobot implements RobotXML{
  ArrayList<SubsystemBase> subsystems;

  @Override
  public void robotInit() {
    XMLParser parser = new XMLParser(Filesystem.getDeployDirectory()+"/robot.xml");
    Element root = parser.getRootElement();
    if (root==null){
      System.out.println("Could not read robot.xml!");
      return;
    }
    ReadXML(root);



    //In.Init("XML/Controls_IN/GarryChassis.xml", "XML/Controls_IN/GarryShooter.xml");
    //Out.Init("XML/Config_OUT/CHASSIS.xml", "XML/Config_OUT/SHOOTER.xml");
    //subsystems.add(new Chassis());
    //subsystems.add(new Shooter());
    //subsystems.add(new Arms());
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

  @Override
  public void ReloadConfig() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ReadXML(Element robot) {
    subsystems = new ArrayList<SubsystemBase>();
    GetAllSubSystems();
    //ShuffleboardHandler tab = ShuffleboardCollections.get(systemElement.getTagName());
    NodeList children = robot.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node currentChild = children.item(i);
      if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
        Element childElement = (Element) currentChild;
        if (childElement.getTagName().equals("subsystem")) {
          System.out.println("LoadingXML - Processing System:"+currentChild.getAttributes().getNamedItem("type"));
        }
      }
    }
  }

  public HashMap<String,Class<?>> GetAllCommands(){
    HashMap<String,Class<?>> commandClasses = new HashMap<>();
   
    String[] packageNames = {"frc.robot.subsystems.commands"};
    Set<Class<?>> classes = new HashSet<Class<?>>();
    try {
      for(String packageName : packageNames){
        var classesInPackage=frc.robot.framework.util.Reflection.findAllClassesUsingClassLoader(packageName);
        if (classesInPackage != null)
         classes.addAll(classesInPackage);
      }
      for (Class<?> myClass : classes) {
        // TODO filter classes for subsystems
        commandClasses.put(myClass.getName(), myClass);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return commandClasses;
  }

  

  public HashMap<String,Class<?>> GetAllSubSystems(){
    HashMap<String,Class<?>> subsystemsReflection = new HashMap<>();
    String[] packageNames = {"frc.robot.subsystem", "frc.robot.framework.subsystems"};
    Set<Class<?>> classes = new HashSet<Class<?>>();
    try {
      for(String packageName : packageNames){
        var classesInPackage=frc.robot.framework.util.Reflection.findAllClassesUsingClassLoader(packageName);
        if (classesInPackage != null)
         classes.addAll(classesInPackage);
      }
      for (Class<?> myClass : classes) {
        // TODO filter classes for subsystems
        subsystemsReflection.put(myClass.getName(), myClass);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } 

    return subsystemsReflection;
  }
}
