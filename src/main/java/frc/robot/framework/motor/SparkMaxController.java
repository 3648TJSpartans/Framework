package frc.robot.framework.motor;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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

    public SparkMaxController(Element element, SubsystemCollection collection) {
        MotorType motorType=MotorType.kBrushless;
        if (element.getAttribute("motortype").toLowerCase().equals("brushed")){
            motorType=MotorType.kBrushed;
        }
        controller = new CANSparkMax(Integer.parseInt(element.getAttribute("port")) , motorType);
        controller.restoreFactoryDefaults();

        pidController = controller.getPIDController();
        

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
                        
                        int countsPerRev=8192;
                        if (childElement.hasAttribute("countsPerRev")){
                            countsPerRev=Integer.parseInt(childElement.getAttribute("countsPerRev"));
                        }
                        alternateEncoder=controller.getAlternateEncoder(countsPerRev);
                        
                        EncoderWrapper encoderWrapper = new EncoderWrapper(childElement, new SparkMaxEncoder(alternateEncoder) ); 
                        collection.encoders.put(childElement.getAttribute("id"), encoderWrapper);
                    }
                    //Normal encoder on side
                    else if (xml_port.equals("encoder")) {
                        encoder = controller.getEncoder();
                        EncoderWrapper encoderWrapper = new EncoderWrapper(childElement, new SparkMaxEncoder(encoder) ); 
                        collection.encoders.put(childElement.getAttribute("id"), encoderWrapper);
                    }
                    else{
                        throw new UnsupportedOperationException("SparkMaxController id:"+element.getAttribute("id")+" - Encoder port: "+ childElement.getAttribute("port") +" not supported. Only 'data' or 'encoder' ports supported");
                    }
                    break;
                case "pid":
                    SparkMaxPID pid = new SparkMaxPID(childElement, this);
                    pidController.setFeedbackDevice(encoder);
                    pidController.setPositionPIDWrappingEnabled(inverted);
                    if (childElement.hasAttribute("setPositionPIDWrappingEnabled") && childElement.hasAttribute("setPositionPIDWrappingMinInput") && childElement.hasAttribute("setPositionPIDWrappingMaxInput")){
                        try{
                        boolean boolValue = Boolean.parseBoolean(childElement.getAttribute("setPositionPIDWrappingEnabled"));
                        double min = Double.parseDouble(childElement.getAttribute("setPositionPIDWrappingMinInput")); 
                        double max = Double.parseDouble(childElement.getAttribute("setPositionPIDWrappingMaxInput")); 
                        pidController.setPositionPIDWrappingEnabled(boolValue);
                        pidController.setPositionPIDWrappingMinInput(min);
                        pidController.setPositionPIDWrappingMaxInput(max);
                        }catch (Exception e){
                            System.out.println("Invalid Format in PIDWrapping on SparkMaxController id:"+
                            element.getAttribute("id")+" - setPositionPIDWrappingEnabled: " 
                            +childElement.getAttribute("setPositionPIDWrappingEnabled")+
                            " setPositionPIDWrappingMinInput:"+childElement.getAttribute("setPositionPIDWrappingMinInput")+" setPositionPIDWrappingMaxInput:"+childElement.getAttribute("setPositionPIDWrappingMaxInput")+" not supported varible type");
                        }     
                    }else if(childElement.hasAttribute("setPositionPIDWrappingEnabled") || childElement.hasAttribute("setPositionPIDWrappingMinInput") || childElement.hasAttribute("setPositionPIDWrappingMaxInput")){
                        System.out.println("Invalid Fields in PIDWrapping on SparkMaxController id:"+
                            element.getAttribute("id")+" - setPositionPIDWrappingEnabled: " 
                            +childElement.getAttribute("setPositionPIDWrappingEnabled")+
                            " setPositionPIDWrappingMinInput:"+childElement.getAttribute("setPositionPIDWrappingMinInput")+" setPositionPIDWrappingMaxInput:"+childElement.getAttribute("setPositionPIDWrappingMaxInput")+" not supported varible type");
                    }

                    if(childElement.hasAttribute("kMinOutput")&& childElement.hasAttribute("kMaxOutput")){
                            try{
                                double min = Double.parseDouble(childElement.getAttribute("kMinOutput"));
                                double max = Double.parseDouble(childElement.getAttribute("kMaxOutput"));
                                pidController.setOutputRange(min, max);
                            }catch (Exception e){
                                System.out.println("Invalid Format in PIDWrapping on SparkMaxController id:"+element.getAttribute("id")+"kMinOutput: "+childElement.getAttribute("kMinOutput")+"kMaxOutput"+childElement.getAttribute("kMaxOutput"));
                            }
                    }else if (childElement.hasAttribute("kMinOutput") || childElement.hasAttribute("kMaxOutput")){
                        System.out.println("Invalid Fields in PIDWrapping on SparkMaxController id:"+element.getAttribute("id")+"kMinOutput: "+childElement.getAttribute("kMinOutput")+"kMaxOutput"+childElement.getAttribute("kMaxOutput"));
                    }
                    collection.pids.put(element.getAttribute("id"), pid);
                    break;
                case "analog":
                    AnalogInBase analogSparkMax = new SparkMaxAnalogIn(controller.getAnalog(SparkMaxAnalogSensor.Mode.kAbsolute)); 
                    collection.analogInputs.put(element.getAttribute("id"), analogSparkMax);
                    break;
                default:
                    break;
            }
        }

        

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
        encoder.setVelocityConversionFactor(factor / 60);
    }

    @Override
    public void resetEncoder() {
        encoder.setPosition(0);
    }
}