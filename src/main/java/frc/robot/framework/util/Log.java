package frc.robot.framework.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class Log {
    private String subsystem;
    private long startTime;
    private FileWriter outputfile;
    private String[] headers;
    private static final String path=Filesystem.getOperatingDirectory() + "/Logs/";

    public Log(String subsystem, String[] headers) {
        this.subsystem = subsystem;
        this.headers = headers;
        cleanUp(7,".csv");
        setupLog();
    }

    public void cleanUp(long nday, String extension) {
        File fold = new File(path);
        if (fold.exists()) {
            File[] listAllFiles = fold.listFiles();
            long Deletion = System.currentTimeMillis() -(nday * 24 * 60 * 60 * 1000);
            for (File listFile: listAllFiles) {
                if (listFile.getName().endsWith(extension) &&
                    listFile.lastModified() < Deletion) {
                    if (!listFile.delete()) {
                        System.out.println("Logging: Can't cleanup directory "+path+listFile.getName());
                    }
                }
            }
        }
    }

    public void RestartNewLog() {
        setupLog();
    }

    private void setupLog() {
        if (outputfile != null) {
            try {
                outputfile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            File folder = new File(Filesystem.getOperatingDirectory() + "/Logs/");
            folder.mkdirs(); //checks and if folder doesn't exist, creates it

            startTime = Instant.now().toEpochMilli();

            File file = new File(Filesystem.getOperatingDirectory() + "/Logs/"
                + java.time.LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + "-" + Instant.now().toEpochMilli() + "-" + subsystem + ".csv");
            file.createNewFile();
            outputfile = new FileWriter(file);

            outputfile.write(String.join(",", headers));
            outputfile.write("\r\n");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void Write(String subsystem, String[] data) {
        String mode;
        if(DriverStation.isAutonomous()){
            mode = "Auto";
        }else{
            mode = "Teleop";
        }

        try {
            double time = (Instant.now().toEpochMilli() - startTime) /1000.0;
            outputfile.write(time + "," + subsystem + "," + mode + "," +String.join(",", data) + "\r\n");
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void WriteHeaders() {
        try {
            outputfile.write(String.join(",", headers));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    protected void finalize() throws IOException {
        outputfile.close();
        //TODO: implement deletion of older logs
    }
    // File name locate in ./log/date-subsytsem
    // Delete logs more than 20
    // Functions for init create file
    // Flush everything in the file
    // When logging is disposed close file
    // creat methods for logging of a sring
    // Pass to subsystem every subsystem will get own logging
    // Support headers csv format
    // Method for
    // Time, mode, values
    // Everytime substem run call logging
}
