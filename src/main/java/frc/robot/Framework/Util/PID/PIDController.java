package frc.robot.Framework.Util.PID;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import frc.robot.Framework.IO.In.Sensors.Encoders.EncoderWrapper;

public class PIDController{
    private String currentProfile;
    private Map<String, PIDProfile> profiles = new HashMap<>();

    public double setpoint = 0;
    public EncoderWrapper source;

    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private static class PIDProfile implements Runnable{
        private double kP, kI, kD, kF = 0;
        private double output = 0;
        private PIDController parent;
        public double measuredValue;
        private String type;
        private double setpoint;
        private double previousError = 0;

        public PIDProfile(String type, double kP, double kI, double kD, double kF, PIDController parent){
            this.type = type;
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
            this.kF = kF;
            this.parent = parent;
        }

        public void run(){
            updateMeasuredValue();
            double error = parent.setpoint - measuredValue;

            double p = error * kP;

            double d = (error - previousError);
            previousError = error;
            d *= kD;

            double f = setpoint * kF;

            output = p + d + f;
        }

        private void updateMeasuredValue(){
            if(type.equals("VELOCITY")){
                measuredValue = parent.source.getVelocity();
            }else if(type.equals("POSITION")){
                measuredValue = parent.source.getPosition();
            }
        }

        public double getOutput(){
            return output;
        }
    }

    public PIDController(EncoderWrapper source){
        this.source = source;
    }

    public void addProfile(String name, double kP, double kI, double kD, double kF){
        profiles.put(name, new PIDProfile(name, kP, kI, kD, kF, this));
        if(profiles.size() == 1){
            currentProfile = name;
            scheduler.scheduleWithFixedDelay(profiles.get(name), 0, 1, TimeUnit.MILLISECONDS);
        }
    }

    public void setSetpoint(double setpoint){
        this.setpoint = setpoint;
    }

    public void setProfile(String name){
        currentProfile = name;
        scheduler.scheduleWithFixedDelay(profiles.get(name), 0, 1, TimeUnit.MILLISECONDS);
    }

    public double getPIDOutput(){
        return profiles.get(currentProfile).getOutput();
    }
}