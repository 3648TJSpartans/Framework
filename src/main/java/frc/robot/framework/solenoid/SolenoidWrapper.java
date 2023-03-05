package frc.robot.framework.solenoid;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class SolenoidWrapper implements SolenoidBase {
    private SolenoidBase solenoid;
    private Element solenoidElement;

    public SolenoidWrapper(Element element) {
        this.solenoidElement = element;
        String id = element.getAttribute("id");
        int port = Integer.parseInt(element.getAttribute("port"));

        if (!element.getAttribute("vendor").toLowerCase().equals("revph")
                && !element.getAttribute("vendor").toLowerCase().equals("ctrepcm")) {
            System.out.println("For solenoid: " + id + " vendor: " + element.getAttribute("vendor").toLowerCase()
                    + " was not found!");
            return;
        }

        PneumaticsModuleType moduleType = PneumaticsModuleType.valueOf(element.getAttribute("vendor").toUpperCase());
        if (element.getAttribute("type").toLowerCase().equals("single")) {
            solenoid = new SolenoidSingle(moduleType, port);
        } else if (element.getAttribute("type").toLowerCase().equals("double")) {
            String[] splitPorts = element.getAttribute("port").split(",");
            if (splitPorts.length != 2) {
                System.out.println("Double solenoid must have two ports defined (split by a comma).");
                return;
            }
            int portOne = Integer.parseInt(splitPorts[0]);
            int portTwo = Integer.parseInt(splitPorts[1]);
            //
            solenoid = new SolenoidDouble(moduleType, portOne, portTwo);
        } else {
            //
            solenoid = new SolenoidSingle(moduleType, port);
            System.out.println(
                    "For solenoid: " + id + " solenoid type: " + element.getAttribute("type") + " was not found!");
        }

        boolean invertedSolenoid = false;
        if (solenoidElement.hasAttribute("inverted")) {
            invertedSolenoid = (Boolean.parseBoolean(solenoidElement.getAttribute("inverted")));
        }
        solenoid.setInverted(invertedSolenoid);
    }

    public void set(boolean extended) {
        solenoid.set(extended);
    }

    public String getAttribute(String attribute) {
        return solenoidElement.getAttribute(attribute);
    }

    @Override
    public void setInverted(boolean inverted) {
        solenoid.setInverted(inverted);

    }
}