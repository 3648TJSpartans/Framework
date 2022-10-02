package frc.robot.framework.io.out.sensor.SensorTypes.Gyroscopes.GyroTypes;

import frc.robot.framework.io.out.sensor.SensorTypes.Gyroscopes.GyroBase;

public class nav_X implements GyroBase{
    //AHRS gyro;
    public nav_X(){
        //gyro = new AHRS(SPI.Port.kMXP);
    }

    @Override
    public double getGyroAccel(String axis) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getGyroAngle(String axis) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getGyroRate(String axis) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getMagneticField(String axis) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getGyroRate() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getGyroAngle() {
        // TODO Auto-generated method stub
        return 0;
    }
}