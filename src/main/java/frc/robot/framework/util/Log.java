package frc.robot.framework.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import edu.wpi.first.wpilibj.Filesystem;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class Log {
    private String subsystem;
    private long startTime;
    private FileWriter outputfile;
    private String[] headers;

    public Log(String subsystem, String[] headers) {
        this.subsystem = subsystem;
        this.headers = headers;
        setupLog();
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

        startTime = Instant.now().toEpochMilli();
        File file = new File(Filesystem.getOperatingDirectory() + "/Logs/"
                + java.time.LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + "-" + Instant.now().toEpochMilli() + "-" + subsystem + ".csv");

        try {
            outputfile = new FileWriter(file);

            outputfile.write(String.join(",", headers));
            outputfile.write("\r\n");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void Write(String mode, String[] data) {
        try {
            double time = (Instant.now().toEpochMilli() - startTime) /1000.0;
            outputfile.write(time + "," + mode + "," + String.join(",", data) + "\r\n");
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
