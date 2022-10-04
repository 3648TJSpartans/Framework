package frc.robot.framework.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

public class Reflection {
    public Reflection(){};
    public static Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
      InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      return reader.lines()
        .filter(line -> line.endsWith(".class"))
        .map(line -> getClass(line, packageName))
        .collect(Collectors.toSet());
    }
  
    private static Class<?> getClass(String className, String packageName) {
      try {
          return Class.forName(packageName + "."
            + className.substring(0, className.lastIndexOf('.')));
      } catch (ClassNotFoundException e) {
          System.out.println(e);
      }
      return null;
    }
  }