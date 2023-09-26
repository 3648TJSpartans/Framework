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
    private static final String logBaseDirectory=Filesystem.getOperatingDirectory() + "/Logs/";

    public Log(String subsystem, String[] headers) {
        this.subsystem = subsystem;
        this.headers = headers;
        cleanUpOldFiles(new File(logBaseDirectory), System.currentTimeMillis() -(7 * 24 * 60 * 60 * 1000));
        setupLog();
    }

    private void cleanUpOldFiles(File filePath, long deletionTime) {
        if (filePath.isDirectory()) {
            File[] listFiles = filePath.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    cleanUpOldFiles(file, deletionTime);
                }
            }
            String[] folderContent = filePath.list();
            if (folderContent != null && folderContent.length == 0) {
                filePath.delete();
            }
        }
        else { //is file
            if (filePath.lastModified() < deletionTime) {
                if (!filePath.delete()) {
                    System.out.println("LoggingCleanup: Can't cleanup file "+logBaseDirectory+filePath.getName());
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
            String folderLocation;
            if (DriverStation.getEventName().isEmpty() || DriverStation.getMatchNumber()<1){
                folderLocation=logBaseDirectory + (DriverStation.getEventName().isEmpty()? "Testing" : DriverStation.getEventName()+'-'+DriverStation.getMatchNumber()) +"/";
            }
            else{
                //TODO: format as 12-12-2023_15:15:15 or something like that
                folderLocation=logBaseDirectory + java.time.LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + "-" + Instant.now().toEpochMilli()+"/";
            }
            
            File folder = new File(folderLocation);
            folder.mkdirs(); //checks and if folder doesn't exist, creates it

            startTime = Instant.now().toEpochMilli();

            File file = new File(folderLocation + subsystem + ".csv");
            file.createNewFile();
            outputfile = new FileWriter(file);

            outputfile.write(String.join(",", headers));
            outputfile.write("\r\n");

        } catch (IOException e) {
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
            e.printStackTrace();
        }

    }

    public void WriteHeaders() {
        try {
            outputfile.write(String.join(",", headers));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void finalize() throws IOException {
        outputfile.close();
    }
    // File name locate in ./log/date-subsytsem
    // Everytime substem run call logging
}
