package frc.robot.framework.subsystems.Motor;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.algorithm.PIDBase;
import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.encoder.EncoderWrapper;
import frc.robot.framework.robot.RobotXML;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.util.CommandMode;
import frc.robot.framework.util.ShuffleboardFramework;
import frc.robot.framework.util.ShuffleboardFramework.ShuffleboardBase;

public class Motor extends SubsystemBase implements RobotXML {
    private ShuffleboardBase tab;
    
    private SubsystemCollection subsystemColection;
    private double reference = .00;
    private CommandMode mode = CommandMode.PERCENTAGE;
    private double maxVelocity = 0;
    private double minVelocity = 0;
    private double maxposition = 0;
    private double minposition = 0;
    private double maxPower = 0;
    private Element element;
    private EncoderBase encoder;
    private PIDBase pid;


    public Motor(Element subsystem) {
        element = subsystem;
        tab = ShuffleboardFramework.getSubsystem(subsystem.getAttribute("id"));
        ReadXML(subsystem);
        if (subsystemColection.encoders.GetAllEncoderIDs().size() > 0) {
            encoder = subsystemColection.encoders.getEncoder(subsystemColection.encoders.GetAllEncoderIDs().iterator().next());
        }
        if (subsystemColection.pids.GetAllPIDsIDs().size() > 0) {
            pid = subsystemColection.pids.getPID(subsystemColection.pids.GetAllPIDsIDs().iterator().next());
        }
    }

    public CommandMode getMode() {
        return mode;
    }

    public void setReference(double reference, CommandMode mode) {
        this.reference = reference;
        this.mode = mode;
    }

    public void setReference(double reference) {
        this.reference = reference;
    }

    public double getReference() {
        return reference;
    }

    @Override
    public void periodic() {
        //This gets the first motor. This is a generic subsystem so we don't control more than one motor. If you need more than one, use a motor group
        for (String motorId : subsystemColection.motors.GetAllMotorIDs()) {
            if (subsystemColection.encoders.GetAllEncoderIDs().size()>0){

                //Makes sure we haven't gone past max/min positions. If we have, go back to that position.
                if(encoder.getPosition() > maxposition && element.hasAttribute("maxPosition")) {
                    subsystemColection.motors.setOutput(motorId, maxposition*.98, CommandMode.POSITION);
                    return;
                    //System.out.println("Max position reached, setting to max position");
                }else if(encoder.getPosition() < minposition && element.hasAttribute("minPosition")) {
                    subsystemColection.motors.setOutput(motorId, minposition*1.02, CommandMode.POSITION);
                    return;
                    //System.out.println("Min position reached, setting to min position");
                }

                //Clamp the values.
                if(mode == CommandMode.VELOCITY && maxVelocity != 0) {
                    reference = Math.min(reference, maxVelocity);
                }
                if(mode == CommandMode.VELOCITY && minVelocity != 0) {
                    reference = Math.max(reference, minVelocity);
                }
                if(mode == CommandMode.PERCENTAGE && maxPower != 0) {
                    reference = Math.min(reference, maxPower);
                }
            }

            switch (mode) {
                case PERCENTAGE:
                    break;
                case POSITION:
                    if (pid==null || encoder==null)
                        reference=pid.getPowerOutput(encoder.getPosition(), reference, mode);
                    break;
                case VELOCITY:
                    if (pid==null || encoder==null)
                        reference=pid.getPowerOutput(encoder.getVelocity(), reference, mode);
                    break;
            }
            subsystemColection.motors.setOutput(motorId, reference, mode);
        }
    }

    @Override
    public void simulationPeriodic() {
    }

    @Override
    public void ReadXML(Element element) {
        // XMLUtil.prettyPrint(element);
        subsystemColection = new SubsystemCollection(element);
        if (element.hasAttribute("commandMode")) {
            switch (element.getAttribute("commandMode")) {
                case "power":
                    mode = CommandMode.PERCENTAGE;
                    break;
                case "velocity":
                    mode = CommandMode.VELOCITY;
                    break;
                case "position":
                    mode = CommandMode.POSITION;
                default:
                    System.out.println(
                            "Motor subsystem does not support commandMode:" + element.getAttribute("commandMode"));
                    break;
            }
            if (element.hasAttribute("reference")) {
                reference = Double.parseDouble(element.getAttribute("reference"));
            }
        } else {
            mode = CommandMode.PERCENTAGE;
        }
        if (element.hasAttribute("maxVelocity")) {
            maxVelocity = Double.parseDouble(element.getAttribute("maxVelocity"));
        }
        if (element.hasAttribute("minVelocity")) {
            minVelocity = Double.parseDouble(element.getAttribute("minVelocity"));
        }
        if (element.hasAttribute("maxPosition")) {
            maxposition = Double.parseDouble(element.getAttribute("maxPosition"));
        }
        if (element.hasAttribute("minPosition")) {
            minposition = Double.parseDouble(element.getAttribute("minPosition"));
        }
        if (element.hasAttribute("maxPower")) {
            maxPower = Double.parseDouble(element.getAttribute("maxPower"));
        }
    }

    @Override
    public void ReloadConfig() {
        

    }
}