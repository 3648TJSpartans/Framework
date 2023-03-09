package frc.robot.framework.sensor.gyroscope.GyroTypes;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.framework.sensor.gyroscope.GyroBase;

public class nav_X implements GyroBase{
    AHRS gyro;
    public nav_X(){
        gyro = new AHRS(SerialPort.Port.kMXP);
    }

    @Override
    public double getGyroAccel(String axis) {
        if(axis == "x"|| axis == "X"){
            return gyro.getWorldLinearAccelX();
        }else if(axis == "y"|| axis == "Y"){
            return gyro.getWorldLinearAccelY();
        }else{
            return gyro.getWorldLinearAccelZ();
        }
    }

    @Override
    public double getGyroAngle(String axis) {
        if(axis == "x"|| axis == "X"){
            return gyro.getPitch();
        }else if(axis == "y"|| axis == "Y"){
            return gyro.getRoll();
        }else{
            return gyro.getYaw();
        }
    }

    @Override
    public double getGyroRate(String axis) {
        
        return 0;
    }

    @Override
    public double getMagneticField(String axis) {
        if(axis == "x"|| axis == "X"){
            return gyro.getRawMagX();
        }else if(axis == "y"|| axis == "Y"){
            return gyro.getRawMagY();
        }else{
            return gyro.getRawMagZ();
        }
    }

    @Override
    public double getGyroRate() {
        
        return 0;
    }

    @Override
    public double getGyroAngle() {
        
        return gyro.getYaw();
    }

    public double getGyroAngleX() {
        
        return  gyro.getPitch();
    }

    public double getGyroAngleY() {
        
        return gyro.getRoll();
    }

    public double getGyroAngleZ() {
        
        return gyro.getYaw();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        // builder.setSmartDashboardType("Motor Controller");
        // builder.setActuator(false);
        builder.addDoubleProperty("X", this::getGyroAngleX, null);
        builder.addDoubleProperty("Y", this::getGyroAngleY, null);
        builder.addDoubleProperty("Z", this::getGyroAngleZ, null);
        // builder.addDoubleProperty("Y", this::get, this::set);
        // builder.addDoubleProperty("X", this::get, this::set);
    }
}