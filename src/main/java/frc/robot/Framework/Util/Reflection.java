package frc.robot.framework.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.wpi.first.wpilibj.Filesystem;

import java.io.File;

public class Reflection {
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
  }