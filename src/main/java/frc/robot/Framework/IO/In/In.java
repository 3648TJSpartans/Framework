package frc.robot.Framework.IO.In;

import java.util.Map;
import java.util.HashMap;

import frc.robot.Framework.IO.In.Controllers.ControllerWrapper;
import frc.robot.Framework.IO.In.Controllers.ControllerTypes.LogitechGamepad;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;

import org.w3c.dom.*;

public class In{
    private static Map<String, ControllerWrapper> controllers = new HashMap<>();

    public static void Init(String controlPath){
        XMLParser parser = new XMLParser("/home/lvuser/deploy/"+controlPath);
        Element root = parser.getRootElement();
        NodeList controllerList = root.getElementsByTagName("controller");
        initControllers(controllerList);
    };

    private static void initControllers(NodeList controllerList){
        for (int i = 0; i < controllerList.getLength(); i++){
            Node controllerNode = controllerList.item(i);
            if(controllerNode.getNodeType() == Node.ELEMENT_NODE){
                Element controllerElement = (Element) controllerNode;
                if(controllerElement.getAttribute("type").equals("LOGITECH")){
                    Integer port = Integer.parseInt(controllerElement.getAttribute("port"));
                    String id = controllerElement.getAttribute("id");
                    controllers.put(id, new ControllerWrapper(new LogitechGamepad(port), controllerElement));
                }
            }
        }
    }

    private static void initSensors(NodeList sensorList){

    }

    private SubsystemID id;

    public In(SubsystemID systemID){
        id = systemID;
    }

    public boolean getButton(String function, String controller){
        ControllerWrapper requestedController = controllers.get(controller);
        return requestedController.getButton(function, id);
    }

    public double getAxis(String function, String controller){
        ControllerWrapper requestedController = controllers.get(controller);
        return requestedController.getAxis(function, id);
    }

    public String getAttribute(String name, String controller){
        ControllerWrapper requestedController = controllers.get(controller);
        return requestedController.getAttribute(name, id);
    }
}