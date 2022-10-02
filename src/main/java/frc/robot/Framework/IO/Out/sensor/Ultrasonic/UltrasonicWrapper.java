package frc.robot.framework.io.out.sensor.ultrasonic;

import org.w3c.dom.Element;

public class UltrasonicWrapper implements UltrasonicBase{
    private UltrasonicBase m_ultrasonic;
    private Element m_UltrasonicElement;

    public UltrasonicWrapper(Element element){
        m_UltrasonicElement = element;
        String id = m_UltrasonicElement.getAttribute("id");
        int port1 = Integer.parseInt(m_UltrasonicElement.getAttribute("port1"));
        int port2 = Integer.parseInt(m_UltrasonicElement.getAttribute("port2"));
        m_ultrasonic = getUltrasonicType(m_UltrasonicElement.getAttribute("type"), port1, port2);

        if (m_ultrasonic == null) {
            System.out.println("For motor: " + id + " motor controller type: " + m_UltrasonicElement.getAttribute("controller") + " was not found!");
            return;
        }
    }
    private UltrasonicBase getUltrasonicType(String controllerType, int port1, int port2) {
        if (controllerType.equals("PING")) {
            return new Ping_Ultrasonic(port1, port2);
        }else{
            return null;
        }
    }
    public double getRangeInches() {
        return m_ultrasonic.getRangeInches();
    }
    public double getRangeMM() {
        return m_ultrasonic.getRangeMM();
    }
    public int getEchoChannel() {
        return m_ultrasonic.getEchoChannel();
    }

}
