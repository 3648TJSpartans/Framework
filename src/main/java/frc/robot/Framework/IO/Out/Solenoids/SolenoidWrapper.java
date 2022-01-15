package frc.robot.Framework.IO.Out.Solenoids;

import org.w3c.dom.Element;

import frc.robot.Framework.IO.Out.Solenoids.SolenoidTypes.SolenoidDouble;
import frc.robot.Framework.IO.Out.Solenoids.SolenoidTypes.SolenoidSingle;

public class SolenoidWrapper implements SolenoidBase {
    private SolenoidBase solenoid;
    private Element solenoidElement;

    public SolenoidWrapper(Element element){
        this.solenoidElement = element;
        String id = element.getAttribute("id");
        int port = Integer.parseInt(element.getAttribute("port"));
        if(element.getAttribute("type").equals("SINGLE")){
            solenoid = new SolenoidSingle(port);
        }else if(element.getAttribute("type").equals("DOUBLE")){
            String[] splitPorts = element.getAttribute("port").split(",");
            if(splitPorts.length != 2){
                System.out.println("Double solenoid must have two ports defined (split by a comma).");
                return;
            }
            int portOne = Integer.parseInt(splitPorts[0]);
            int portTwo = Integer.parseInt(splitPorts[1]);
            solenoid = new SolenoidDouble(portOne, portTwo);
        }else{
            solenoid = new SolenoidSingle(port);
            System.out.println("For solenoid: "+id+" solenoid type: "+element.getAttribute("type")+" was not found!");
        }
    }

    public void set(boolean extended){
        solenoid.set(extended);
    }

    public String getAttribute(String attribute){
        return solenoidElement.getAttribute(attribute);
    }
}