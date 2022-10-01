package frc.robot.Framework.IO.Out.Sensors.SensorTypes.Potentiometers;

import org.w3c.dom.Element;

public class PotentiometerWrapper implements PotentiometerBase{
    private PotentiometerBase m_Potentiometer;
    private Element m_PotentiometerElement;

    public PotentiometerWrapper(Element element){
        m_PotentiometerElement = element;
        String id = m_PotentiometerElement.getAttribute("id");
        int port = Integer.parseInt(m_PotentiometerElement.getAttribute("port"));
        double scale = Double.parseDouble(m_PotentiometerElement.getAttribute("scale"));
        m_Potentiometer = getPotentiometerType(m_PotentiometerElement.getAttribute("type"), port, scale);

        if (m_Potentiometer == null) {
            System.out.println("For motor: " + id + " motor controller type: " + m_PotentiometerElement.getAttribute("controller") + " was not found!");
            return;
        }
    }
    private PotentiometerBase getPotentiometerType(String controllerType, int port, double scale) {
        if (controllerType.equals("ANALOG")) {
            return new Analog_Potentiometer(port, scale);
        }else{
            return null;
        }
    }
    public double getPotentiometer() {
        return m_Potentiometer.getPotentiometer();
    }

}
