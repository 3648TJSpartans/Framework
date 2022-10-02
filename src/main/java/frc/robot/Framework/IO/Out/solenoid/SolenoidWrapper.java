package frc.robot.framework.io.out.solenoid;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class SolenoidWrapper implements SolenoidBase {
    private SolenoidBase solenoid;
    private Element solenoidElement;

    public SolenoidWrapper(Element element){
        this.solenoidElement = element;
        String id = element.getAttribute("id");
        int port = Integer.parseInt(element.getAttribute("port"));
        PneumaticsModuleType moduleType = PneumaticsModuleType.valueOf(element.getAttribute("type"));
        if(element.getAttribute("type").equals("SINGLE")){
            //
            solenoid = new SolenoidSingle(moduleType, port);
        }else if(element.getAttribute("type").equals("DOUBLE")){
            String[] splitPorts = element.getAttribute("port").split(",");
            if(splitPorts.length != 2){
                System.out.println("Double solenoid must have two ports defined (split by a comma).");
                return;
            }
            int portOne = Integer.parseInt(splitPorts[0]);
            int portTwo = Integer.parseInt(splitPorts[1]);
            //
            solenoid = new SolenoidDouble(moduleType, portOne, portTwo);
        }else{
            //
            solenoid = new SolenoidSingle(moduleType, port);
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