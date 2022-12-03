package frc.robot.framework.vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.framework.robot.RobotXML;

import org.w3c.dom.Element;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

public class Limelight implements RobotXML {

    private static double cameraHeight, targetHeight, cameraAngle;

    public void Limelight(Element element) {
        ReadXML(element);
    }

    public static double getLimelightX() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = table.getEntry("tx");
        double x = tx.getDouble(0.0);
        return x;
    }

    public static double getLimelightY() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ty = table.getEntry("ty");
        double y = ty.getDouble(0.0);
        return y;
    }

    public static double getLimelightArea() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ta = table.getEntry("ta");
        double area = ta.getDouble(0.0);
        return area;
    }

    public static double getLimelightSkew() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ts = table.getEntry("ts");
        double skew = ts.getDouble(0.0);
        return skew;
    }

    public static double getLimelightTarget() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tv = table.getEntry("tv");
        double target = tv.getDouble(0.0);
        return target;
    }

    public static void setLimelightLEDMode(int mode) {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ledMode = table.getEntry("ledMode");
        ledMode.setNumber(mode);
    }

    public static void setLimelightCamMode(int mode) {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry camMode = table.getEntry("camMode");
        camMode.setNumber(mode);
    }

    public static double getLimelightPipeline() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry pipeline = table.getEntry("pipeline");
        double pipelineNum = pipeline.getDouble(0.0);
        return pipelineNum;
    }

    public static double getLimelightLatency() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry latency = table.getEntry("tl");
        double latencyNum = latency.getDouble(0.0);
        return latencyNum;
    }

    public static void setLimelightPipeline(int pipeline) {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry pipelineNum = table.getEntry("pipeline");
        pipelineNum.setNumber(pipeline);
    }

    public static void setLimelightStream(int stream) {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry streamNum = table.getEntry("stream");
        streamNum.setNumber(stream);
    }

    public static double getLimelightShort() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tshort = table.getEntry("tshort");
        double tshortNum = tshort.getDouble(0.0);
        return tshortNum;
    }

    public static double getLimelightLong() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tshort = table.getEntry("tLong");
        double tlongNum = tshort.getDouble(0.0);
        return tlongNum;
    }

    public static double getLimelightHorizontal() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry thor = table.getEntry("thor");
        double thorNum = thor.getDouble(0.0);
        return thorNum;
    }

    public static double getLimelightVertical() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tvert = table.getEntry("tvert");
        double tvertNum = tvert.getDouble(0.0);
        return tvertNum;
    }

    public static double getDistance() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ty = table.getEntry("ty");
        double y = ty.getDouble(0.0);

        return (targetHeight - cameraHeight) / Math.tan(Math.toRadians(cameraAngle + y));
    }

    @Override
    public void ReadXML(Element node) {
        try {
            targetHeight = Double.parseDouble(node.getAttributes().getNamedItem("targetheight").getNodeValue());
            cameraHeight = Double.parseDouble(node.getAttributes().getNamedItem("cameraheight").getNodeValue());
            cameraAngle = Double.parseDouble(node.getAttributes().getNamedItem("cameraangle").getNodeValue());
        } catch (Exception e) {
            System.out.println("Error reading Limelight XML");
        }
    }

    @Override
    public void ReloadConfig() {
        // TODO Auto-generated method stub

    }
}