package frc.robot.framework.motor;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.framework.algorithm.PIDBase;
import frc.robot.framework.algorithm.PIDWrapper;
import frc.robot.framework.encoder.EncoderWrapper;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.util.CommandMode;
import frc.robot.framework.util.ShuffleboardFramework;

public class SparkController extends MotorController {
    private Spark controller;

    public SparkController(Element element, SubsystemCollection collection) {
        int port = Integer.parseInt(element.getAttribute("port"));
        controller = new Spark(port);
        //Parse nested PID/ENCODERS

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node currentChild = children.item(i);
            if (currentChild.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element childElement = (Element) currentChild;
            String id = childElement.getAttribute("id");

            if (childElement.getTagName().toLowerCase().equals("encoder")) {
                encoder = new EncoderWrapper(childElement);
                collection.encoders.put(id, encoder);
                ShuffleboardFramework.subsystems.get(collection.subsystemName).addSendableToTab(id, encoder);
            }
            if (childElement.getTagName().toLowerCase().equals("pid")){
                pid=new PIDWrapper(childElement);
                collection.pids.put(id, pid);
            }
        }
    }

    @Override
    public void setReference(double reference, CommandMode mode) {
        if (inverted)
            reference*=-1;

        //TODO: SparmPWM does not support position/velocity yet! If you're using PID, set mode to PRECENTAGE and pass over your desired power
        //if (mode == CommandMode.PERCENTAGE)
        controller.set(reference);
    }

    @Override
    public PIDBase getPID(){
        if (pid !=null)
           return pid;
        else
            throw new UnsupportedOperationException("SparkController: pid is null. Can't getPID");
    }

    @Override
    public void set(double speed){
        controller.set(speed);
    }

    @Override
    public double get() {
        return controller.get();
    }

    @Override
    public void disable() {
        controller.disable();
    }

    @Override
    public void stopMotor() {
        controller.stopMotor();
    }


    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Motor Controller");
        builder.setActuator(true);
        builder.setSafeState(this::disable);
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}