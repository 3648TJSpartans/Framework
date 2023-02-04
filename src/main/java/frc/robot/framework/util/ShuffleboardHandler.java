package frc.robot.framework.util;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

public class ShuffleboardHandler {
    public static Map<String, ShuffleboardBase> Handlers = new HashMap<>();
    public ShuffleboardBase base;

    public ShuffleboardHandler(Element root) {
        NodeList systemList = root.getElementsByTagName("subsystem");
        for (int i = 0; i < systemList.getLength(); i++) {
            Node currentSystem = systemList.item(i);
            if (currentSystem.getNodeType() == Node.ELEMENT_NODE) {
                Element systemElement = (Element) currentSystem;
                // NodeList subsystemList = systemElement.getChildNodes();
                // for (int j = 0; j < subsystemList.getLength(); j++) {
                // Node currentSubsystem = subsystemList.item(j);
                if (systemElement.getNodeType() == Node.ELEMENT_NODE) {
                    Element subsystemElement = (Element) systemElement;
                    if (!subsystemElement.getAttribute("id").isEmpty()) {
                        Handlers.put(subsystemElement.getAttribute("id"), new ShuffleboardBase(subsystemElement));
                    }
                }
                // }
            }
        }
    }

    public ShuffleboardHandler(String system) {
        base = Handlers.get(system);
        if (base == null) {
            System.out.println("Shuffleboard - Subsystem: " + system + " not found");
        }
    }

    public boolean getEnabled(String title, String system) {
        if (base == null || base.Widgets.get(title) == null) {
            // System.out.println("entry not found");
            return true;
        }
        return base.getEnabled(title, system);

    }

    public Object get(String title) {
        return base.get(title);
    }

    public void set(String title, Object value) {
        base.set(title, value);
    }

    public static class ShuffleboardBase {
        // TODO expand this to read all attributes in the element. Detect the type
        // TODO add a <shuffleboardY
        ShuffleboardTab tab;
        ShuffleboardTab liveWindow = Shuffleboard.getTab("LiveWindow");
        public Map<String, NetworkTableEntry> Widgets = new HashMap<>();
        public Map<String, NetworkTableEntry> liveWindowWidgets = new HashMap<>();

        public ShuffleboardBase(Element system) {
            tab = Shuffleboard.getTab(system.getAttribute("id"));
            NodeList children = system.getChildNodes();
            Boolean sysEnabled = Boolean.parseBoolean(system.getAttribute("enabled"));
            SimpleWidget sysWidget = tab.add(system.getAttribute("id"), sysEnabled).withWidget("Toggle Button");
            // NetworkTableEntry sysEntry = sysWidget.getEntry();

            // Widgets.put(system.getAttribute("id"), sysEntry);
            SimpleWidget sysliveWindowWidget = liveWindow.add(system.getAttribute("id"), sysEnabled)
                    .withWidget("Toggle Button");
            // NetworkTableEntry sysliveWindowEntry = sysliveWindowWidget.getEntry();

            // liveWindowWidgets.put(system.getAttribute("id"), sysliveWindowEntry);

            for (int i = 0; i < children.getLength(); i++) {
                Node currentChild = children.item(i);
                if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                    Element childElement = (Element) currentChild;
                    // String valueType = childElement.getAttribute("valueType").toLowerCase();
                    // System.out.print(valueType);
                    // System.out.print(valueType.equals("double"));

                    // if(valueType.equals("double")){
                    // System.out.print(childElement.getAttribute("id"));
                    // Double enabled =
                    // Double.parseDouble((childElement.getAttribute("defaultValue")));
                    // String title = childElement.getAttribute("id");
                    // SimpleWidget widget = tab.add(title, enabled);
                    // NetworkTableEntry entry = widget.getEntry();
                    // Widgets.put(title, entry);
                    // SimpleWidget liveWindowWidget = liveWindow.add(title, enabled);
                    // NetworkTableEntry liveWindowEntry = liveWindowWidget.getEntry();
                    // liveWindowWidgets.put(title, liveWindowEntry);
                    // }else if(valueType.equals("string")){
                    // String enabled = childElement.getAttribute("defaultValue");
                    // String title = childElement.getAttribute("id");
                    // SimpleWidget widget = tab.add(title, enabled);
                    // NetworkTableEntry entry = widget.getEntry();
                    // Widgets.put(title, entry);
                    // SimpleWidget liveWindowWidget = liveWindow.add(title, enabled);
                    // NetworkTableEntry liveWindowEntry = liveWindowWidget.getEntry();
                    // liveWindowWidgets.put(title, liveWindowEntry);
                    // }else if(valueType.equals("int")){
                    // int enabled = Integer.parseInt((childElement.getAttribute("defaultValue")));
                    // String title = childElement.getAttribute("id");
                    // SimpleWidget widget = tab.add(title, enabled);
                    // NetworkTableEntry entry = widget.getEntry();
                    // Widgets.put(title, entry);
                    // SimpleWidget liveWindowWidget = liveWindow.add(title, enabled);
                    // NetworkTableEntry liveWindowEntry = liveWindowWidget.getEntry();
                    // liveWindowWidgets.put(title, liveWindowEntry);
                    // }else {
                    // Boolean enabled = childElement.getAttribute("enabled") == "" ? true
                    //         : Boolean.parseBoolean(childElement.getAttribute("enabled"));

                    // String title = system.getAttribute("id") + "." + childElement.getAttribute("id");
                    // SimpleWidget widget = tab.add(title, enabled).withWidget("Toggle Button");
                    // NetworkTableEntry entry = widget.getEntry();
                    // Widgets.put(title, entry);
                    // SimpleWidget liveWindowWidget = liveWindow.add(title, enabled).withWidget("Toggle Button");
                    // NetworkTableEntry liveWindowEntry = liveWindowWidget.getEntry();
                    // liveWindowWidgets.put(title, liveWindowEntry);
                    // // }

                }
            }
        }

        public ShuffleboardBase(String system) {
            tab = Shuffleboard.getTab(system);
        }

        public boolean getEnabled(String title, String system) {
            NetworkTableEntry entry = Widgets.get(title);
            NetworkTableEntry sysEntry = Widgets.get(system);
            NetworkTableEntry liveEntry = liveWindowWidgets.get(title);
            NetworkTableEntry liveSysEntry = liveWindowWidgets.get(title);
            if (entry == null) {
                networkTableError(title, tab.getTitle());
                return false;
            }
            Boolean tempBool = entry.getBoolean(false) && sysEntry.getBoolean(false) && liveEntry.getBoolean(false)
                    && liveSysEntry.getBoolean(false);
            return tempBool;

        }

        public Object get(String title) {
            NetworkTableEntry entry = Widgets.get(title);
            NetworkTableEntry liveEntry = liveWindowWidgets.get(title);
            Object obj;
            if (entry.getValue().getValue() == liveEntry.getValue().getValue()) {
                return entry.getValue().getValue();
            } else {
                return liveEntry.getValue().getValue();
            }

        }

        public void set(String title, Object value) {
            NetworkTableEntry entry = Widgets.get(title);
            NetworkTableEntry liveEntry = liveWindowWidgets.get(title);

            entry.setValue(value);
            liveEntry.setValue(value);
        }
        
        private void networkTableError(String id, String tabName){
            System.out.println("Shuffleboard:" + id + " not found in tab: " + tabName);
        }
    }
}