package frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers;

public interface ACLBase {
    public double getAccelerometerAxis(String axis);
    public void setAccelerometerRange(String range);
    public double getAcceleration();
    public void setAccelerometerZero(double zero);
    public void setAccelerometerSensitivity(double sensitivity);
}
