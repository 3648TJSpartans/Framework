package frc.robot.framework.sensor.gyroscope.GyroTypes;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.framework.sensor.gyroscope.GyroBase;

public class nav_X implements GyroBase{
    //AHRS gyro;
    public nav_X(){
        //gyro = new AHRS(SPI.Port.kMXP);
    }

    @Override
    public double getGyroAccel(String axis) {
        
        return 0;
    }

    @Override
    public double getGyroAngle(String axis) {
        
        return 0;
    }

    @Override
    public double getGyroRate(String axis) {
        
        return 0;
    }

    @Override
    public double getMagneticField(String axis) {
        
        return 0;
    }

    @Override
    public double getGyroRate() {
        
        return 0;
    }

    @Override
    public double getGyroAngle() {
        
        return 0;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        // builder.setSmartDashboardType("Motor Controller");
        // builder.setActuator(false);
        builder.addDoubleProperty("GyroAngle", this::getGyroAngle, null);
        // builder.addDoubleProperty("Y", this::get, this::set);
        // builder.addDoubleProperty("X", this::get, this::set);
    }
}