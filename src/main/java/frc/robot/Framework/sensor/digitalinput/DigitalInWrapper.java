package frc.robot.framework.sensor.digitalinput;

import org.w3c.dom.Element;

public class DigitalInWrapper implements DigitalInBase{
    private DigitalInBase m_digitalIn;
    private Element m_digitalInElement;

    public DigitalInWrapper(Element element){
        m_digitalInElement = element;
        String id = m_digitalInElement.getAttribute("id");
        int port = Integer.parseInt(m_digitalInElement.getAttribute("port"));
        m_digitalIn = new Digital_In(port);

        if (m_digitalIn == null) {
            System.out.println("For DIO: " + id + " DIO controller type: " + m_digitalInElement.getAttribute("controller") + " was not found!");
            return;
        }
    }
    
    public Boolean getDigitalIn(){
        return m_digitalIn.getDigitalIn();
    }
    
}
