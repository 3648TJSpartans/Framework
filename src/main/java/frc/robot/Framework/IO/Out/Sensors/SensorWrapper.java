// package frc.robot.Framework.IO.Out.Sensors;

// import org.w3c.dom.*;

// import edu.wpi.first.wpilibj.AnalogPotentiometer;
// import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Accelerometers.ACLWrapper;
// import frc.robot.Framework.IO.Out.Sensors.SensorTypes.DigitalIn.DigitalIn;
// import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Gyroscopes.GyroWrapper;
// import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Potentiometers.AnalogPOT;
// import frc.robot.Framework.IO.Out.Sensors.SensorTypes.Ultrasonic.PingUltrasonic;

// public class SensorWrapper implements SensorBase{
//     public Element sensorElement;
//     public SensorBase sensor;
//     public SensorWrapper(Element element){
//         sensorElement = element;
//         String id = sensorElement.getAttribute("id");
//         int port = Integer.parseInt(sensorElement.getAttribute("port"));
//         sensor = getSensorType(sensorElement.getAttribute("sensorType"), port);

//         if (sensor == null) {
//             System.out.println("For sensor: " + id + " sensor type: " + sensorElement.getAttribute("sensorType") + " was not found!");
//             return;
//         }

//     }
//     public SensorWrapper(Element element, Element motor){
        
//     }
//     private SensorBase getSensorType(String sensorType, int port) {
//         if (sensorType.equals("ACCELEROMETER") || sensorType.equals("ACL")) {
//             return new ACLWrapper(port);
//         } else if (sensorType.equals("DIGITAL_IN") || sensorType.equals("DIGITAL_INPUT") || sensorType.equals("DIO")||sensorType.equals("LIMIT_SWITCH")) {
//             return new DigitalIn(port);
//         } else if(sensorType.equals("GYROSCOPES")||sensorType.equals("GYROS")){
//             return new GyroWrapper(port);
//         }else if (sensorType.equals("POTENTIOMETERS") || sensorType.equals("POT")) {
//             //naming is weird due to collsions with the naming of WPILIB naming
//             return new AnalogPOT(port);
//         } else if(sensorType.equals("ULTRASONIC")){
//             return new PingUltrasonic(port);
//         }else{
//             return null;
//         }
//     }
    
//     public boolean getPressed() {
//         // TODO Auto-generated method stub
//         return false;
//     }
    
// }
