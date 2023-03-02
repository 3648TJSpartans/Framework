package frc.robot.framework.motor;

import java.util.ArrayList;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.framework.algorithm.PIDBase;
import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.util.CommandMode;

public class MotorGroup extends MotorController{
    private ArrayList<MotorBase> motors = new ArrayList<>();

    public MotorGroup() {
    };

    public void addMotor(MotorBase newMotor) {
        motors.add(newMotor);
    }

    public ArrayList<MotorBase> getAllMotors() {
        return motors;
    }

    @Override
    public void setReference(double reference, CommandMode mode) {
        if (inverted)
            reference*=-1.0;
        for (int i = 0; i < motors.size(); i++) {
                motors.get(i).setReference(reference, mode);
        }
    }

    @Override
    public boolean hasEncoder(){
        return motors.get(0).hasEncoder();
    }

    @Override
    public EncoderBase getEncoder(){
        return motors.get(0).getEncoder();
    }

    @Override
    public double getVelocity(){
        return motors.get(0).getVelocity();
    }

    @Override
    public double getPosition(){
        return motors.get(0).getPosition();
    }

    @Override
    public void setPID(double kP, double kI, double kD, double kF){
        motors.get(0).setPID(kP, kI, kD, kF);
    }

    @Override
    public PIDBase getPID(){
        return motors.get(0).getPID();
    }

    @Override
    public boolean hasPID(){
        return motors.get(0).hasPID();
    }

    @Override
    public void set(double speed){
        if (inverted)
            speed*=-1.0;
        for (int i = 0; i < motors.size(); i++) {
            motors.get(i).setReference(speed, CommandMode.PERCENTAGE);
    }
    }

    @Override
    public double get() {
        return motors.get(0).get();
    }

    @Override
    public void disable() {
        for (int i = 0; i < motors.size(); i++) {
            motors.get(i).disable();
        }
    }

    @Override
    public void stopMotor() {
        for (int i = 0; i < motors.size(); i++) {
            motors.get(i).stopMotor();
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        for (int i = 0; i < motors.size(); i++) {
            motors.get(i).initSendable(builder);
        }
    }
}