package frc.robot.framework.subsystems.Util;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.ADIS16470_IMU.IMUAxis;
import edu.wpi.first.wpilibj2.command.*;

import frc.robot.framework.robot.*;
import frc.robot.framework.sensor.gyroscope.GyroTypes.ADIS_16470;

public class ADIS16470_Set extends CommandBase implements RobotXML {

    private Element myElement;
    private double startTime;
    private double delayLength = 4;
    private IMUAxis axis;

    public ADIS16470_Set(Element element) {
        myElement = element;

    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        try {
            axis = IMUAxis.valueOf(myElement.getAttribute("axis"));
        } catch (Exception e) {
            throw new NumberFormatException(
                    "Invalid Format on :" + (myElement.getAttribute("axis")));
        }

        ADIS_16470.m_gyro.setYawAxis(axis);

    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        System.out.println("Time Complete: " + (System.currentTimeMillis() - startTime) + " " + (delayLength * 1000)
                + (System.currentTimeMillis() - startTime > delayLength * 1000));
        if (System.currentTimeMillis() - startTime > delayLength * 1000) {
            return true;
        }
        return false;
    }

    @Override
    public void ReadXML(Element node) {

    }

    @Override
    public void ReloadConfig() {

    }
}