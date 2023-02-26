package frc.robot.framework.motor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;

import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import frc.robot.framework.util.CommandMode;
import frc.robot.framework.util.ShuffleboardFramework;
import frc.robot.framework.util.ShuffleboardFramework.ShuffleboardBase;

public class Motors {
    private Map<String, MotorBase> motors = new HashMap<>();
    private String subsystemName;
    public Element sensorElement;
    private ShuffleboardBase tab;

    public Motors(String subsystemName) {
        this.subsystemName = subsystemName;
        tab = ShuffleboardFramework.getSubsystem(subsystemName);
    }

    /**
     * [getMotor] returns the motor associated with the id
     * 
     * @param id the id of the motor or motor group (ie "SHOOTER_WHEEL" or
     *           "LEFT_SIDE")
     */
    public MotorBase getMotor(String id) {
        MotorBase requestedMotor = motors.get(id);
        if (requestedMotor == null) {
            motorError(id, subsystemName);
            return null;
        }

        return requestedMotor;
    }

    public Set<String> GetAllMotorIDs() {
        return motors.keySet();
    }

    public void put(String id, MotorBase motor) {
        // tab = new ShuffleboardHandler(subsystemName.toString());
        // SimpleWidget widget = tab.add
        // NetworkTableEntry entry = widget.getEntry();
        // Widgets.put(title, entry);
        // SimpleWidget liveWindowWidget = liveWindow.add(id, enabled);
        // NetworkTableEntry liveWindowEntry = liveWindowWidget.getEntry();
        // liveWindowWidgets.put(title, liveWindowEntry);
        motors.put(id, motor);
    }

    private void motorError(String id, String subsystemName) {
        System.out.println("Motor:" + id + " not found. Subsystem: " + subsystemName + " not registered for output.");
    }

    /**
     * [setMotor] sets the speed of the requested motor or motor group
     * 
     * @param id    the id of the motor or motor group (ie "SHOOTER_WHEEL" or
     *              "LEFT_SIDE")
     * @param speed the speed of the motor
     */
    public void setOutput(String id, double reference, CommandMode mode) {
        MotorBase requestedMotor = getMotor(id);
        if (tab.getEnabled(id))
            requestedMotor.setReference(reference, mode);
    }


}
