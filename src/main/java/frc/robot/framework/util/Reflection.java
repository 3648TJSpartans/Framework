package frc.robot.framework.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.Filesystem;

import java.io.File;

public class Reflection {
    private static HashMap<String,Class<?>> allCommands = new HashMap<>();
    private static HashMap<String,Class<?>> allSubsystems = new HashMap<>();

    public Reflection(){};
    public static Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {

      Set<Class<?>> classes = new HashSet<Class<?>>();
      try{
        File folder = new File(ClassLoader.getSystemClassLoader().getResource(packageName.replaceAll("[.]", "/")).getPath());
        for (File file : listOfFiles(folder)) {
          try {
            classes.add(Class.forName(file.getPath().substring(Filesystem.getDeployDirectory().toString().length()-6,file.getPath().length()-6).replace(File.separator.charAt(0),'.')));
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
        }
        //Old way
        // InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        // BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        // return reader.lines()
        //   .filter(line -> line.endsWith(".class"))
        //   .map(line -> getClass(line, packageName))
        //   .collect(Collectors.toSet());
      }
      catch(NullPointerException exception){
        return null;
      }
      return classes;
    }
  
    // private static Class<?> getClass(String className, String packageName) {
    //   try {
    //       return Class.forName(packageName + "."
    //         + className.substring(0, className.lastIndexOf('.')));
    //   } catch (ClassNotFoundException e) {
    //       System.out.println(e);
    //   }
    //   return null;
    // }

    private static LinkedList<File> listOfFiles(File dirPath){
      File filesList[] = dirPath.listFiles();
      LinkedList<File> myFiles = new LinkedList<>();
      for(File file : filesList) {
         if(file.isFile()) {
            myFiles.add(file);
         } else {
            myFiles.addAll(listOfFiles(file));
         }
      }
      return myFiles;
   }

   public static <T> T CreateObjectFromXML(Class<T> myClass, Element element){
    try {
      System.out.println("Creating object: "+myClass.getName());
      // Class<?>[] nullClass=null;
      //myClass.getDeclaredConstructor( new Class<?>[]{int.class, int.class, int.class}).newInstance(1,2,3)
      Object[] parameters={element};
      var temp = (T)(myClass.getDeclaredConstructor(Class.forName("org.w3c.dom.Element")).newInstance(parameters));
      // for (Method m : myClass.getDeclaredMethods()){
      //     if (Modifier.isPublic(m.getModifiers()) && m.getName().contains("execute")){
      //       m.invoke(temp,nullParameters);
      //     }
      // }
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
          allCommands.put(myClass.getSimpleName().toLowerCase(), myClass);
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
          allSubsystems.put(myClass.getSimpleName().toLowerCase(), myClass);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } 

    return allSubsystems;
  }
}