package frc.robot.Framework.IO.In;

import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import frc.robot.Framework.IO.In.Controllers.ControllerWrapper;
import frc.robot.Framework.IO.In.Controllers.ControllerTypes.LogitechGamepad;
import frc.robot.Framework.IO.In.Controllers.ControllerTypes.PSGamepad;
import frc.robot.Framework.IO.In.Controllers.ControllerTypes.XboxGamepad;
import frc.robot.Framework.Util.XMLMerger;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;

import org.w3c.dom.*;

/**
 * [In] is a class containing static methods for interfacing with all inputs to
 * the robot. This includes (but is not limited to) controllers and sensors.
 * 
 * @note Currently encoders are paired with motors in [Out] instead of being
 *       handled by [In] like they intuitively should be. May want to move
 *       encoder handling to [In] and have the motors reference using getters.
 */

public class In {
    private static Map<String, ControllerWrapper> controllers = new HashMap<>();

    /**
     * [Init] initializes [In] using an XML file detailing the control scheme.
     * 
     * @param thing path to the controls file (relative to the deploy
     *                    directory).
     * 
     * @note the current control file setup requires operator and driver controls to
     *       be specified in the same file. This is not ideal for mixing / matching
     *       driver + operator pairs. Could add an additional path to specify
     *       operator vs driver controls.
     * 
     * @note sensor input is not currently implemented. I was conflicted on how to
     *       implement this, so i'll detail the options as I see them and let you
     *       pick:
     *       1) Make a separate file that only includes sensors, and parse that with
     *       [In]. This is the simplest option, but loses the benefits associated
     *       with using XML in the first place (allowing text based representation
     *       of the robot structure).
     *       2) Parse the full configuration file. I think this is the better
     *       option, but comes with additional problems. First, it clutters
     *       the configuration file (do we care?). Second, how do we deal with
     *       encapsulation? Do we associate sensors to certain subsystems (allows
     *       for name overlaps + how controls and outputs are currently done) or do
     *       we make all sensors global scope (easier for auto)?
     */

    public static void Init(String... strings) {
        XMLMerger merger = new XMLMerger();
        String XMLPath = merger.merger("controller", strings);
        XMLParser parser = new XMLParser(XMLPath);
        Element root = parser.getRootElement();
        NodeList controllerList = root.getElementsByTagName("controller");
        initControllers(controllerList);
    };

    /**
     * [controllerList] is a helper function for [Init]. Takes in an XML nodeList
     * representing controllers and registers those controllers with [In].
     * 
     * @param controllerList a list of XML nodes representing controllers
     * 
     * @note currently is hardcoded to assume LogitechGamepads (standard to 3648 in
     *       the 2020 season). This doesn't have to be the case, and could be
     *       changed to allow further customizability for driver preference.
     */

    private static void initControllers(NodeList controllerList) {
        for (int i = 0; i < controllerList.getLength(); i++) {
            Node controllerNode = controllerList.item(i);
            if (controllerNode.getNodeType() == Node.ELEMENT_NODE) {
                Element controllerElement = (Element) controllerNode;
                if (controllerElement.getAttribute("type").equals("LOGITECH")) {
                    Integer port = Integer.parseInt(controllerElement.getAttribute("port"));
                    String id = controllerElement.getAttribute("id");
                    controllers.put(id, new ControllerWrapper(new LogitechGamepad(port), controllerElement));
                }
                if (controllerElement.getAttribute("type").equals("XBOX")) {
                    Integer port = Integer.parseInt(controllerElement.getAttribute("port"));
                    String id = controllerElement.getAttribute("id");
                    controllers.put(id, new ControllerWrapper(new XboxGamepad(port), controllerElement));
                }
                if (controllerElement.getAttribute("type").equals("PS")) {
                    Integer port = Integer.parseInt(controllerElement.getAttribute("port"));
                    String id = controllerElement.getAttribute("id");
                    controllers.put(id, new ControllerWrapper(new PSGamepad(port), controllerElement));
                }
                //add new controller types here

            }
        }
    }

    /**
     * [sensorList] is a helper function for [Init]. It takes an XML nodeList
     * representing sensors and registers those sensors with [In]
     * 
     * @param sensorList a list of XML nodes representing sensors
     */

    private static void initSensors(NodeList sensorList) {
        // TODO: Implement [sensorList]
    }

    private SubsystemID id;

    /**
     * Constructor for [In]. Sets which subsystem this instance of [In] is for. That
     * system will only have access to its designated controls + sensors.
     * 
     * @param systemID the id of the subsystem
     */

    public In(SubsystemID systemID) {
        id = systemID;
    }

    /**
     * [getButton], [getAxis], [getAttribute] return information about the given
     * control interface.
     */

    /** 
     * [getButton] returns the value of requested button
     * 
     * @param function   the name of the function (ie "TURN" or "EXTEND_ARM")
     * @param controllerID the name of the controller (ie "OPERATOR")
     * @return information about the requested control interface.
     */

    public boolean getButton(String function, String controllerID) {
        ControllerWrapper requestedController = controllers.get(controllerID);
        return requestedController.getButton(function, id);
    }
    /**
     * [getAxis] returns the value of the requested axis
     * 
     * @param function   the name of the function (ie "TURN" or "EXTEND_ARM")
     * @param controllerID the name of the controller (ie "OPERATOR")
     * @return information about the requested control interface.
     */
    public double getAxis(String function, String controllerID) {
        ControllerWrapper requestedController = controllers.get(controllerID);
        return requestedController.getAxis(function, id);
    }
    /**
     * [getAttribute] returns the value of the XML attributed named [name]
     * 
     * @param function   the name of the function (ie "TURN" or "EXTEND_ARM")
     * @param controllerID the name of the controller (ie "OPERATOR")
     * @return information about the requested control interface.
     */
    public String getAttribute(String name, String controllerID) {
        ControllerWrapper requestedController = controllers.get(controllerID);
        return requestedController.getAttribute(name, id);
    }
}