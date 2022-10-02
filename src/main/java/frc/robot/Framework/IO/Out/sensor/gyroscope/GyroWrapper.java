package frc.robot.framework.io.out.sensor.gyroscope;

import org.w3c.dom.Element;

import frc.robot.framework.io.out.sensor.gyroscope.GyroTypes.ADIS_16448;
import frc.robot.framework.io.out.sensor.gyroscope.GyroTypes.ADIS_16470;
import frc.robot.framework.io.out.sensor.gyroscope.GyroTypes.ADXRS_450;
import frc.robot.framework.io.out.sensor.gyroscope.GyroTypes.Analog_Gyro;
import frc.robot.framework.io.out.sensor.gyroscope.GyroTypes.Pigeon;
import frc.robot.framework.io.out.sensor.gyroscope.GyroTypes.nav_X;

public class GyroWrapper implements GyroBase{
    Element m_gyroElement;
    GyroBase m_gyro;
    public GyroWrapper(Element element){
        m_gyroElement = element;
        String id = m_gyroElement.getAttribute("id");
        int port = Integer.parseInt(m_gyroElement.getAttribute("port"));
        m_gyro = getGyroType(m_gyroElement.getAttribute("type"), port);

        if (m_gyro == null) {
            System.out.println("For motor: " + id + " motor controller type: " + m_gyroElement.getAttribute("controller") + " was not found!");
            return;
        }

        
    }
    private GyroBase getGyroType(String controllerType, int port) {
        if (controllerType.equals("ADIS16448")) {
            return new ADIS_16448();
        } else if (controllerType.equals("ADIS16470")) {
            return new ADIS_16470();
        } else if(controllerType.equals("ADXRS450")){
            return new ADXRS_450();
        }else if(controllerType.equals("AnalogGyro")){
            return new Analog_Gyro();
        }else if(controllerType.equals("navX")){
            return new nav_X();
        }else if(controllerType.equals("pigeon") || controllerType.equals("Pigeon")){
            return new Pigeon(port);
        }else{
            return null;
        }
    }
    public double getGyroAccel(String axis){
        return m_gyro.getGyroAccel(axis);
    }
    @Override
    public double getGyroAngle(String axis) {
        if(axis != null){
            return m_gyro.getGyroAngle(axis);
        }else{
            return m_gyro.getGyroAngle();
        }
        
    }
    @Override
    public double getGyroRate(String axis) {
        if(axis != null){
            return m_gyro.getGyroRate(axis);
        }else{
            return m_gyro.getGyroRate();
        }
    }
    @Override
    public double getMagneticField(String axis) {
        return m_gyro.getMagneticField(axis);
    }
    @Override
    public double getGyroRate() {
        return m_gyro.getGyroRate();
    }
    @Override
    public double getGyroAngle() {
        return m_gyro.getGyroAngle();
    }

}
