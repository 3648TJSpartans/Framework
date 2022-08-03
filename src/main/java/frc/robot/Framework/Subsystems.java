package frc.robot.Framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frc.robot.Subsystems.SubsystemID;

/**
 * [Subsystems] is a class containing static methods to update all subsystems.
 */

public class Subsystems {
    private static Map<SubsystemID, Subsystem> subsystems = new HashMap<>();

    /**
     * [add] adds a subsystem to the robot
     * 
     * @param newSubsystem the subsystem to be added
     * @param id           identifier for the subsystem
     * 
     * @note Using an enum for SubsystemID the way I do breaks encapsulation of the
     *       framework (note the import, it comes from outside of the framework).
     *       Might be possible to fix using generics? Otherwise could be good to
     *       switch to string id's.
     */

    public static void add(Subsystem newSubsystem, SubsystemID id) {
        subsystems.put(id, newSubsystem);
    }

    public static void robotInit() {
        for (Subsystem subsystem : subsystems.values()) {
            subsystem.robotInit();
        }
    }

    public static void robotPeriodic() {
        for (Subsystem subsystem : subsystems.values()) {
            subsystem.robotPeriodic();
        }
    }

    public static void autonomousInit() {
        for (Subsystem subsystem : subsystems.values()) {
            subsystem.autonomousInit();
        }
    }

    public static void autonomousPeriodic() {
        for (Subsystem subsystem : subsystems.values()) {
            subsystem.autonomousPeriodic();
        }
    }

    public static void teleopInit() {
        for (Subsystem subsystem : subsystems.values()) {
            subsystem.teleopInit();
        }
    }

    public static void teleopPeriodic() {
        for (Subsystem subsystem : subsystems.values()) {
            subsystem.teleopPeriodic();
        }
    }

    /**
     * [getSubsystem] returns the subsystem associated with the given id.
     * Useful for inter-subsystem communication (ie calling another subsystems
     * method)
     * 
     * @param id id of the subsystem to retrieve
     * @return the subsystem mapped the id
     */

    public static Subsystem getSubsystem(SubsystemID id) {
        return subsystems.get(id);
    }
}