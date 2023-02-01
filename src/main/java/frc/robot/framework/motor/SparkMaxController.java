package frc.robot.framework.motor;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAnalogSensor;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.framework.algorithm.PIDBase;
import frc.robot.framework.algorithm.PIDWrapper;
import frc.robot.framework.algorithm.SparkMaxPID;
import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.encoder.EncoderWrapper;
import frc.robot.framework.encoder.SparkMaxEncoder;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.sensor.analoginput.AnalogInBase;
import frc.robot.framework.sensor.analoginput.AnaloginWrapper;
import frc.robot.framework.sensor.analoginput.SparkMaxAnalogIn;
import frc.robot.framework.util.CommandMode;

public class SparkMaxController extends MotorController implements MotorBase, EncoderBase {
    private CANSparkMax controller;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;
    private RelativeEncoder alternateEncoder;

    public SparkMaxController(int port) {
        controller = new CANSparkMax(port, MotorType.kBrushless);
        controller.restoreFactoryDefaults();
        pidController = controller.getPIDController();
        encoder = controller.getEncoder();
        alternateEncoder=controller.getAlternateEncoder(8192);
        encoder.setPosition(0);

    }

    public SparkMaxController(Element element, SubsystemCollection collection) {
        MotorType motorType=MotorType.kBrushless;
        if (element.getAttribute("motortype").toLowerCase().equals("brushed")){
            motorType=MotorType.kBrushed;
        }
        controller = new CANSparkMax(Integer.parseInt(element.getAttribute("port")) , motorType);

        //Get encoders.
        NodeList childNodeList = element.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element childElement = (Element) childNodeList.item(i);
            
            switch (childElement.getTagName().toLowerCase()) {
                case "encoder":
                    String xml_port=childElement.getAttribute("port").toLowerCase();
                    //data encoder on top
                    if (xml_port.equals("data")){
                        EncoderWrapper encoderWrapper = new EncoderWrapper(childElement, new SparkMaxEncoder(alternateEncoder) ); 
                        collection.encoders.put(childElement.getAttribute("id"), encoderWrapper);
                    }
                    //Normal encoder on side
                    else if (xml_port.equals("encoder")) {
                        EncoderWrapper encoderWrapper = new EncoderWrapper(childElement, new SparkMaxEncoder(encoder) ); 
                        collection.encoders.put(childElement.getAttribute("id"), encoderWrapper);
                    }
                    else{
                        throw new UnsupportedOperationException("SparkMaxController id:"+element.getAttribute("id")+" - Encoder port: "+ childElement.getAttribute("port") +" not supported. Only 'data' or 'encoder' ports supported");
                    }
                    break;
                case "pid":
                    collection.pids.put(element.getAttribute("id"), new SparkMaxPID(childElement, this));
                    break;
                case "analog":
                    AnalogInBase analogSparkMax = new SparkMaxAnalogIn(controller.getAnalog(SparkMaxAnalogSensor.Mode.kAbsolute)); 
                    collection.analogInputs.put(element.getAttribute("id"), analogSparkMax);
                    break;
                default:
                    break;
            }
        }

        controller.restoreFactoryDefaults();
        pidController = controller.getPIDController();
        encoder = controller.getEncoder();
        alternateEncoder=controller.getAlternateEncoder(8192);
        encoder.setPosition(0);

    }

    @Override
    public void setInverted(boolean inverted) {
        super.setInverted(inverted);
    }

    public void setReference(double reference, CommandMode mode) {
        if (inverted)
            controller.setInverted(inverted);
        switch (mode) {
            case PERCENTAGE:
                pidController.setReference(reference, ControlType.kDutyCycle);
                break;
            case VELOCITY:
                pidController.setReference(reference, ControlType.kVelocity);
                break;
            case POSITION:
                pidController.setReference(reference, ControlType.kPosition);
                break;
            default:
                System.out.println("SparkMaxController - Unknown commandmode:" + mode);
                break;
        }
    };

    public CANSparkMax getCanSparkMax() {
        return controller;
    }

    public void setVoltage(double voltage) {
        controller.setVoltage(voltage);
    }

    public SparkMaxPIDController getPidController() {
        return pidController;
    }

    @Override
    public int getTicks() {
        return 0;
    }

    @Override
    public double getVelocity() {
        return encoder.getVelocity();
    }

    @Override
    public double getPosition() {
        return encoder.getPosition();
    }

    @Override
    public void setDistancePerPulse(double factor) {
        encoder.setPositionConversionFactor(factor);
        encoder.setVelocityConversionFactor(factor);
    }

    @Override
    public void resetEncoder() {
        encoder.setPosition(0);
    }
}