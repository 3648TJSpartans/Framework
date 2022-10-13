package frc.robot.framework.robot;

import org.w3c.dom.Element;

public interface RobotXML{

    public void ReadXML(Element node);
    public void ReloadConfig();
}