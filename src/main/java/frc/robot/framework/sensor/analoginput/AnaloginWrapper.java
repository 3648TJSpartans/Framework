package frc.robot.framework.sensor.analoginput;

import org.w3c.dom.Element;

public class AnaloginWrapper implements AnalogInBase{
    private AnalogInBase m_analogIn;
    private Element m_analogInElement;

    public AnaloginWrapper(Element element){
        m_analogInElement = element;
        String id = m_analogInElement.getAttribute("id");
        int port = Integer.parseInt(m_analogInElement.getAttribute("port"));
        m_analogIn = new RoboRioAnalogIn(port);

        if (m_analogIn == null) {
            System.out.println("For AnalogInput: " + id + " AnalogInput controller type: " + m_analogInElement.getAttribute("controller") + " was not found!");
            return;
        }
    }

    @Override
    public int getValue() {
        return m_analogIn.getValue();
    }

    @Override
    public int getAverageValue() {
        return m_analogIn.getAverageValue();
    }

    @Override
    public double getVoltage() {
        return m_analogIn.getVoltage();
    }

    @Override
    public double getAverageVoltage() {
        return m_analogIn.getAverageVoltage();
    }
    
    
}
