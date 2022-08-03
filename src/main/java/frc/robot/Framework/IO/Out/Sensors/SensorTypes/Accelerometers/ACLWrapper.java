package frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLTypes.ADXL_345_I2C;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLTypes.ADXL_345_SPI;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLTypes.ADXL_362;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLTypes.Analog_Accelerometer;
import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLTypes.Built_In_Accelerometer;

public class ACLWrapper implements ACLBase{
    Element m_accelerometerElement;
    ACLBase m_accelerometer;
    public ACLWrapper(Element element){
        m_accelerometerElement = element;
        String id = m_accelerometerElement.getAttribute("id");
        String port = m_accelerometerElement.getAttribute("port");
        String range = m_accelerometerElement.getAttribute("range");
        m_accelerometer = getAccelerometerType(m_accelerometerElement.getAttribute("type"), port, range);

        if (m_accelerometer == null) {
            System.out.println("For motor: " + id + " motor controller type: " + m_accelerometerElement.getAttribute("controller") + " was not found!");
            return;
        }
    }
    private ACLBase getAccelerometerType(String type, String port, String range) {
        if (type.equals("ADXL345I2C")) {
            return new ADXL_345_I2C(port , range);
        } else if (type.equals("ADXL345SPI")) {
            return new ADXL_345_SPI(port , range);
        } else if(type.equals("ADXL362")){
            return new ADXL_362(port , range);
        }else if(type.equals("AnalogAccelerometer")){
            return new Analog_Accelerometer(port);
        }else if(type.equals("BuiltInAccelerometer")){
            return new Built_In_Accelerometer();
        }else{
            return null;
        }
    }
    public double getAccelerometerAxis(String axis) {
        return m_accelerometer.getAccelerometerAxis(axis);
    }
    public void setAccelerometerRange(String range) {
        m_accelerometer.setAccelerometerRange(range);
        
    }
    public double getAcceleration() {
        return m_accelerometer.getAcceleration();
    }
    public void setAccelerometerZero(double zero) {
        m_accelerometer.setAccelerometerZero(zero);
        
    }
    public void setAccelerometerSensitivity(double sensitivity) {
        m_accelerometer.setAccelerometerSensitivity(sensitivity);
        
    }
}
