package frc.robot.framework.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleboardFramework {
    public static Map<String, ShuffleboardBase> subsystems = new HashMap<>(); //Shuffleboard base for each subsystem
    private static ShuffleboardBase base; // This is the main page. things like auton.

    public ShuffleboardFramework(Element root) {
        NodeList systemList = root.getElementsByTagName("subsystem");
        for (int i = 0; i < systemList.getLength(); i++) {
            Node currentSystem = systemList.item(i);
            if (currentSystem.getNodeType() == Node.ELEMENT_NODE) {
                Element systemElement = (Element) currentSystem;
                if (systemElement.getNodeType() == Node.ELEMENT_NODE) {
                    Element subsystemElement = (Element) systemElement;
                    if (!subsystemElement.getAttribute("id").isEmpty()) {
                        subsystems.put(subsystemElement.getAttribute("id"), new ShuffleboardBase(subsystemElement));
                    }
                }
            }
        }
    }

    //This is used for subclasses of a subsystem that consume 
    public static ShuffleboardBase addSubsystem(String subsystemName){
        return subsystems.put(subsystemName, new ShuffleboardBase(subsystemName));
    }

    public static boolean getEnabled(String title, String system) {
        if (base == null || base.Widgets.get(title) == null) {
            // System.out.println("entry not found");
            return true;
        }
        return base.getEnabled(title);

    }

    // public static Object get(String title, String system) {
    //     return base.get(title);
    // }

    public static ShuffleboardBase getSubsystem(String system) {
        return subsystems.get(system);
    }

    public static class ShuffleboardBase {
        // TODO expand this to read all attributes in the element. Detect the type
        // TODO add a <shuffleboardY
        private ShuffleboardTab tab;
        private ShuffleboardTab liveWindow = Shuffleboard.getTab("LiveWindow");
        public Map<String, GenericEntry> Widgets = new HashMap<>();
        public Map<String, GenericEntry> liveWindowWidgets = new HashMap<>();

        public ShuffleboardBase(Element system) {
            tab = Shuffleboard.getTab(system.getAttribute("id"));
            // Boolean sysEnabled = system.hasAttribute("enabled") ? Boolean.parseBoolean(system.getAttribute("enabled")) : true;
            // SimpleWidget sysWidget = tab.add(system.getAttribute("id"), sysEnabled).withWidget("Toggle Button");
        
            // GenericEntry sysEntry = sysWidget.getEntry();
            // NodeList children = system.getChildNodes();
            // Widgets.put(system.getAttribute("id"), sysEntry);

            // SimpleWidget sysliveWindowWidget = liveWindow.add(system.getAttribute("id"), sysEnabled).withWidget("Toggle Button");
                
            // GenericEntry sysliveWindowEntry = sysliveWindowWidget.getEntry();

            // // liveWindowWidgets.put(system.getAttribute("id"), sysliveWindowEntry);

            // for (int i = 0; i < children.getLength(); i++) {
            //     Node currentChild = children.item(i);
            //     if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
            //         Element childElement = (Element) currentChild;
            //          String valueType = childElement.getAttribute("valueType").toLowerCase();
                    // System.out.print(valueType);
                    // System.out.print(valueType.equals("double"));

                    // if(valueType.equals("double")){
                    // System.out.print(childElement.getAttribute("id"));
                    // Double enabled =
                    // Double.parseDouble((childElement.getAttribute("defaultValue")));
                    // String title = childElement.getAttribute("id");
                    // SimpleWidget widget = tab.add(title, enabled);
                    // GenericEntry entry = widget.getEntry();
                    // Widgets.put(title, entry);
                    // SimpleWidget liveWindowWidget = liveWindow.add(title, enabled);
                    // GenericEntry liveWindowEntry = liveWindowWidget.getEntry();
                    // liveWindowWidgets.put(title, liveWindowEntry);
                    // }else if(valueType.equals("string")){
                    // String enabled = childElement.getAttribute("defaultValue");
                    // String title = childElement.getAttribute("id");
                    // SimpleWidget widget = tab.add(title, enabled);
                    // GenericEntry entry = widget.getEntry();
                    // Widgets.put(title, entry);
                    // SimpleWidget liveWindowWidget = liveWindow.add(title, enabled);
                    // GenericEntry liveWindowEntry = liveWindowWidget.getEntry();
                    // liveWindowWidgets.put(title, liveWindowEntry);
                    // }else if(valueType.equals("int")){
                    // int enabled = Integer.parseInt((childElement.getAttribute("defaultValue")));
                    // String title = childElement.getAttribute("id");
                    // SimpleWidget widget = tab.add(title, enabled);
                    // GenericEntry entry = widget.getEntry();
                    // Widgets.put(title, entry);
                    // SimpleWidget liveWindowWidget = liveWindow.add(title, enabled);
                    // GenericEntry liveWindowEntry = liveWindowWidget.getEntry();
                    // liveWindowWidgets.put(title, liveWindowEntry);
                    // }else {
                    // Boolean enabled = childElement.getAttribute("enabled") == "" ? true
                    //         : Boolean.parseBoolean(childElement.getAttribute("enabled"));

                    // String title = system.getAttribute("id") + "." + childElement.getAttribute("id");
                    // SimpleWidget widget = tab.add(title, enabled).withWidget("Toggle Button");
                    // GenericEntry entry = widget.getEntry();
                    // Widgets.put(title, entry);
                    // SimpleWidget liveWindowWidget = liveWindow.add(title, enabled).withWidget("Toggle Button");
                    // GenericEntry liveWindowEntry = liveWindowWidget.getEntry();
                    // liveWindowWidgets.put(title, liveWindowEntry);
                    // // }

        }

        public void addWidgetToTab(String id, Object obj, BuiltInWidgets widgetType){

            // SimpleWidget sysWidget = tab.add(id, obj).withWidget(widgetType);
            // GenericEntry sysEntry = sysWidget.getEntry();

            // Widgets.put(id, sysEntry);
    
            // double test = Math.random();
            // SimpleWidget sysliveWindowWidget = liveWindow.add(id+""+test, obj).withWidget(widgetType);
                
            // GenericEntry sysliveWindowEntry = sysliveWindowWidget.getEntry();
    
            // liveWindowWidgets.put(id+""+test, sysliveWindowEntry);
        }

        public void addSendableToTab(String id, Sendable objSendable){
            tab.add(id,objSendable);
        }

        public void addSendableToTab(String id, Sendable objSendable, BuiltInWidgets widgetType){
            tab.add(id,objSendable).withWidget(widgetType);
        }

        // public void addDoubleToTab(String id, DoubleSupplier objSendable){
        //     System.out.println("in tab: "+tab.getTitle() +" adding id:"+id);
        //     tab.addDouble(id,objSendable);//.withWidget(BuiltInWidgets.kTextView);
        // }

        public ShuffleboardBase(String system) {
            tab = Shuffleboard.getTab(system);
        }

        public boolean getEnabled(String title) {
            GenericEntry entry = Widgets.get(title);
            GenericEntry liveEntry = liveWindowWidgets.get(title);
            GenericEntry liveSysEntry = liveWindowWidgets.get(title);
            if (entry == null) {
                networkTableError(title, tab.getTitle());
                return true;
            }
            Boolean tempBool = entry.getBoolean(false) && liveEntry.getBoolean(false)
                    && liveSysEntry.getBoolean(false);
            return tempBool;

        }

        // public Object get(String title) {
        //     GenericEntry entry = Widgets.get(title);
        //     GenericEntry liveEntry = liveWindowWidgets.get(title);
        //     if (entry.get() == liveEntry.get()) {
        //         return entry.get();
        //     } else {
        //         return liveEntry.get();
        //     }
        // }

        // public void set(String title, Object value) {
        //     GenericEntry entry = Widgets.get(title);
        //     GenericEntry liveEntry = liveWindowWidgets.get(title);

        //     entry.setValue(value);
        //     liveEntry.setValue(value);
        // }
        
        private void networkTableError(String id, String tabName){
            //System.out.println("Shuffleboard:" + id + " not found in tab: " + tabName);
        }
    }
}