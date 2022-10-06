package frc.robot.framework.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BehaviorHandler {

    public BehaviorHandler(Element systemsElement){
        NodeList systemList = systemsElement.getChildNodes();
        
        for (int i = 0; i < systemList.getLength(); i++) {
            Node currentSystem = systemList.item(i);
            if (currentSystem.getNodeType() == Node.ELEMENT_NODE) {
                Element systemElement = (Element) currentSystem;
                switch(systemElement.getTagName()) {
                    case "ARCADE_DRIVE":
                      
                    case "TANK_DRIVE":
                      
                    case "MANUAL_SHOOTER":
                      
                    case "AUTO_SHOOTER":
                      
                    case "INTAKE":
                      
                    case "CLIMBER":
                      
                    default:
                      
                }
            }
        }
    }
}
