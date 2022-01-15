package frc.robot.Framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frc.robot.Framework.Subsystem;
import frc.robot.Subsystems.SubsystemID;

public class Subsystems{
    private static Map<SubsystemID, Subsystem> subsystems = new HashMap<>();

    public static void add(Subsystem newSubsystem, SubsystemID id){
        subsystems.put(id, newSubsystem);
    }

    public static void robotInit(){
        for(Subsystem subsystem : subsystems.values()){
            subsystem.robotInit();
        }
    }
    
    public static void robotPeriodic(){
        for(Subsystem subsystem : subsystems.values()){
            subsystem.robotPeriodic();
        }
    }

    public static void autonomousInit(){
        for(Subsystem subsystem : subsystems.values()){
            subsystem.autonomousInit();
        }
    }

    public static void autonomousPeriodic(){
        for(Subsystem subsystem : subsystems.values()){
            subsystem.autonomousPeriodic();
        }
    }

    public static void teleopInit(){
        for(Subsystem subsystem : subsystems.values()){
            subsystem.teleopInit();
        }
    }

    public static void teleopPeriodic(){
        for(Subsystem subsystem : subsystems.values()){
            subsystem.teleopPeriodic();
        }
    }

    public static Subsystem getSubsystem(SubsystemID id){
        return subsystems.get(id);
    }
}