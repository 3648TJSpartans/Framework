package frc.robot.framework.util;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleboardFramework {
    public static Map<String, ShuffleboardBase> subsystems = new HashMap<>(); //Shuffleboard base for each subsystem
    public static ShuffleboardBase mainTab = new ShuffleboardBase("Robot"); // This is the main page. things like auton.

    public ShuffleboardFramework(Element root) {
        NodeList systemList = root.getElementsByTagName("subsystem");
        for (int i = 0; i < systemList.getLength(); i++) {
            Node currentSystem = systemList.item(i);
            if (currentSystem.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            
            Element subsystemElement = (Element) currentSystem;
            if (!subsystemElement.getAttribute("id").isEmpty()) {
                subsystems.put(subsystemElement.getAttribute("id"), new ShuffleboardBase(subsystemElement));
            }
        }
    }

    public static ComplexWidget addSendableToMainWindow(String id, Sendable objSendable, BuiltInWidgets widgetType){
        return mainTab.addSendableToTab(id,objSendable,widgetType);
    }

    public static ComplexWidget addSendableToMainWindow(String id, Sendable objSendable){
        return mainTab.addSendableToTab(id,objSendable);
    }

    //This is used for subclasses of a subsystem that want to publish their subsystemCollection
    public static ShuffleboardBase addSubsystem(String subsystemName){
        return subsystems.put(subsystemName, new ShuffleboardBase(subsystemName));
    }

    public static ShuffleboardBase getSubsystem(String system) {
        return subsystems.get(system);
    }

    public static class ShuffleboardBase {
        private ShuffleboardTab tab;
        public Map<String, GenericEntry> Widgets = new HashMap<>();
        public Map<String, GenericEntry> liveWindowWidgets = new HashMap<>();

        public ShuffleboardBase(Element system) {
            tab = Shuffleboard.getTab(system.getAttribute("id"));
        }

        public ComplexWidget addSendableToTab(String id, Sendable objSendable){
            return tab.add(id,objSendable);
        }

        public ComplexWidget addSendableToTab(String id, Sendable objSendable, BuiltInWidgets widgetType){
            return tab.add(id,objSendable).withWidget(widgetType);
        }

        public ShuffleboardBase(String system) {
            tab = Shuffleboard.getTab(system);
        }

        public boolean getEnabled(String title) {
                //TODO: need to go back and add this functionality
                return true;
        }

        private void networkTableError(String id, String tabName){
            //System.out.println("Shuffleboard:" + id + " not found in tab: " + tabName);
        }
    }
}