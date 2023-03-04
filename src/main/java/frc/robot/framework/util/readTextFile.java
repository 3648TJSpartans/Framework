package frc.robot.framework.util;

import java.io.*;
import java.util.*;

import edu.wpi.first.wpilibj.Filesystem;

public class readTextFile {
    final static String filePath = Filesystem.getDeployDirectory() + "/values.txt";

    public static Map<String, Double> HashMapFromTextFile() {

        Map<String, Double> map = new HashMap<String, Double>();
        BufferedReader br = null;

        try {

            // create file object
            File file = new File(filePath);

            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));

            String line = null;

            // read file line by line
            while ((line = br.readLine()) != null) {

                // split the line by :
                String[] parts = line.split("=");

                // first part is name, second is number
                String name = parts[0].trim();
                Double value = Double.parseDouble(parts[1].trim());

                // put name, number in HashMap if they are
                // not empty
                if (!name.equals("") && !value.equals(""))
                    map.put(name, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
                ;
            }
        }
        return map;
    }
}
