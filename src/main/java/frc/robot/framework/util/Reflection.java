package frc.robot.framework.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;

public class Reflection {
    private static HashMap<String,Class<?>> allCommands = new HashMap<>();
    private static HashMap<String,Class<?>> allSubsystems = new HashMap<>();
    private static LinkedList<String> allClasses = new LinkedList<>();

    public Reflection(){};

    public static Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
      if (allClasses.size()==0){
        if (RobotBase.isReal()) {
          try {
            allClasses=getAllClassesReal(Reflection.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()); //Debugging location in simulation -- "C:\\src\\Framework\\build\\libs\\Framework.Jar"
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        else{
          allClasses=getAllClassesSimulation(ClassLoader.getSystemClassLoader().getResource("frc/robot/").getPath());
        }
      }

      

      Set<Class<?>> classes = new HashSet<Class<?>>();
      try{
        for (String className : allClasses.stream().filter(name -> name.startsWith(packageName)).collect(Collectors.toList())) {
          try {
            classes.add(Class.forName(className));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
      catch(Exception exception){
        return null;
      }
      return classes;
    }
  

   private static LinkedList<String> getAllClassesReal(String dirPath){
    
    try{
      LinkedList<String> classNames = new LinkedList<String>();
      ZipInputStream zip = new ZipInputStream(new FileInputStream(dirPath));
      for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
          if (!entry.isDirectory() && entry.getName().endsWith(".class") && entry.getName().startsWith("frc")) {
              // This ZipEntry represents a class. Now, what class does it represent?
              String className = entry.getName().replace('/', '.'); // including ".class"
              classNames.add(className.substring(0, className.length() - ".class".length()).replace(File.separator.charAt(0),'.')) ;
          }
      }
      zip.close();
      return classNames;
    }
    catch(Exception e){
      
      System.out.println(e);
    }
    return null;
  }
  
  private static LinkedList<String> getAllClassesSimulation(String dirPath){
    File filesList[] = new File(dirPath).listFiles();
    LinkedList<String> classNames = new LinkedList<>();
    for(File file : filesList) {
        if(file.isFile()) {
          if (file.getName().endsWith(".class")){
            classNames.add(file.getPath().substring(Filesystem.getDeployDirectory().toString().length()-".class".length(), file.getPath().length()- ".class".length()).replace(File.separator.charAt(0),'.'));
          }
        } else {
          classNames.addAll(getAllClassesSimulation(file.getPath()));
        }
    }
    return classNames;
 }

   public static <T> T CreateObjectFromXML(Class<T> myClass, Element element){
    Object[] params= new Object[]{element};
    Constructor<T> constructor;
    try {
        constructor=myClass.getDeclaredConstructor(Class.forName("org.w3c.dom.Element"));
    } catch (Exception e) {
      System.out.println(myClass.getName() + ": does not have a 'org.w3c.dom.Element' constructor");
      throw new UnsupportedOperationException();
    } 
    try {
      var temp=(T)(constructor.newInstance(params));
      return temp;
    } catch (Exception e){
      System.out.println();
      System.out.println();
      System.out.println(myClass.getName() + ": Error creating object. An error occured in the initalization of the object");
      e.getCause().printStackTrace();
      System.out.println();
      System.out.println();
      // for (var errorLine : e.getStackTrace()) {
      //   System.out.println(errorLine.toString());
      // }
    }
    return null;
  }

  public static <T> T CreateObject(Class<T> myClass, Class<?>[] parametersClasses, Object[] params ){
    try {
      var temp = (T)(myClass.getDeclaredConstructor(parametersClasses).newInstance(params));
      return temp;
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return null;
  }

  public static HashMap<String,Class<?>> GetAllCommands(){
    return GetAllCommands(false);
  }
  public static HashMap<String,Class<?>> GetAllCommands(boolean reload){
    if (!reload && allCommands.size()>0){
      return allCommands;
    }
   
    String[] packageNames = {"frc.robot.commands", "frc.robot.framework.subsystems"};
    Set<Class<?>> classes = new HashSet<Class<?>>();
    try {
      for(String packageName : packageNames){
        var classesInPackage=frc.robot.framework.util.Reflection.findAllClassesUsingClassLoader(packageName);
        if (classesInPackage != null)
         classes.addAll(classesInPackage);
      }
      Class<?> commandBase = Class.forName("edu.wpi.first.wpilibj2.command.CommandBase");
      for (Class<?> myClass : classes) {
        if(commandBase.isAssignableFrom(myClass)){
          allCommands.put(myClass.getSimpleName(), myClass);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return allCommands;
  }

  public static HashMap<String,Class<?>> GetAllSubSystems(){
    return GetAllSubSystems(false);
  }

  public static HashMap<String,Class<?>> GetAllSubSystems(boolean reload){
    if (!reload && allSubsystems.size()>0){
      return allSubsystems;
    }

    String[] packageNames = {"frc.robot.subsystem", "frc.robot.framework.subsystems"};
    Set<Class<?>> classes = new HashSet<Class<?>>();
    try {
      for(String packageName : packageNames){
        var classesInPackage=frc.robot.framework.util.Reflection.findAllClassesUsingClassLoader(packageName);
        if (classesInPackage != null)
         classes.addAll(classesInPackage);
      }
      Class<?> subsystemBase = Class.forName("edu.wpi.first.wpilibj2.command.SubsystemBase");
      for (Class<?> myClass : classes) {
        // TODO filter classes for subsystems
        if(subsystemBase.isAssignableFrom(myClass)){
          allSubsystems.put(myClass.getSimpleName(), myClass);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } 

    return allSubsystems;
  }
}