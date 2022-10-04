package frc.robot.framework.robot;

import org.w3c.dom.Node;

public interface RobotXML{

    public void ReadXML(Node node);
    public void ReloadConfig();
}