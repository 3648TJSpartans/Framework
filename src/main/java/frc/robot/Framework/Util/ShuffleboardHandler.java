package frc.robot.Framework.Util;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShuffleboardHandler {
    public static Map<String, ShuffleboardBase> Handlers = new HashMap<>();
    ShuffleboardBase base;

    public ShuffleboardHandler(Element root){
        NodeList systemList = root.getElementsByTagName("subsystem");
        for (int i = 0; i < systemList.getLength(); i++) {
            Node currentSystem = systemList.item(i);
            if (currentSystem.getNodeType() == Node.ELEMENT_NODE) {
                Element systemElement = (Element) currentSystem;
                NodeList subsystemList = systemElement.getChildNodes();
                for (int j = 0; j < subsystemList.getLength(); j++) {
                    Node currentSubsystem = subsystemList.item(j);
                    if (currentSubsystem.getNodeType() == Node.ELEMENT_NODE) {
                        Element subsystemElement = (Element) currentSubsystem;
                        Handlers.put(subsystemElement.getTagName(), new ShuffleboardBase(subsystemElement));
                    }
                }
            }
        }
        

    }
    public ShuffleboardHandler(String system){
        System.out.print(Handlers.size());
        base = Handlers.get(system);
        if(base == null){
            System.out.println("entry: " + system + " not found");
        }
    }
    public boolean get(String title, String system){
        //System.out.print(title);
        //base = Handlers.get(system);
        if(base == null){
            System.out.println("entry not found");
        }
        return base.get(title);

    }

    public static class ShuffleboardBase {
        ShuffleboardTab tab;
        public Map<String, NetworkTableEntry> Widgets = new HashMap<>();
        
        public ShuffleboardBase(Element system){
            tab = Shuffleboard.getTab(system.getTagName());
            NodeList children = system.getChildNodes();
            System.out.println(system.getNodeName());
            for (int i = 0; i < children.getLength(); i++) {
                Node currentChild = children.item(i);
                if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                    Element childElement = (Element) currentChild;
                    Boolean enabled = Boolean.parseBoolean(childElement.getAttribute("enabled"));
                    String title = childElement.getAttribute("id");
                    //System.out.print(title);
                    SimpleWidget widget = tab.add(title, enabled).withWidget("Toggle Button");
                    NetworkTableEntry entry = widget.getEntry();
                    Widgets.put(title, entry);
                }
            }
        }
        ShuffleboardBase(String system){
            tab = Shuffleboard.getTab(system);
        }
        public boolean get(String title){
            //System.out.println(Widgets.size());
            NetworkTableEntry entry = Widgets.get(title);
            if (entry == null) {
                System.out.println("entry: " + title + " not found in tab: " + tab.getTitle());
            }
            return entry.getBoolean(false);
        }
    }
    

}
