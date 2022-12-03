package frc.robot.framework.vision;

import org.w3c.dom.Element;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import frc.robot.framework.robot.RobotXML;

public class USBCamera implements RobotXML{

    public void Limelight(Element element) {
        ReadXML(element);
    }

    public void camera(){
        // Camera setup
        UsbCamera camera = CameraServer.startAutomaticCapture();
        camera.setResolution(640, 480);
        CameraServer.startAutomaticCapture();
    }

    @Override
    public void ReadXML(Element node) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub
        
    }

}