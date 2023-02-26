package frc.robot.framework.motor;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAnalogSensor;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.framework.encoder.EncoderBase;
import frc.robot.framework.encoder.EncoderWrapper;
import frc.robot.framework.encoder.SparkMaxEncoderAbsoluteEncoder;
import frc.robot.framework.encoder.SparkMaxEncoderRelativeEncoder;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.sensor.analoginput.AnalogInBase;
import frc.robot.framework.sensor.analoginput.SparkMaxAnalogIn;
import frc.robot.framework.algorithm.PIDBase;
import frc.robot.framework.algorithm.SparkMaxPID;
import frc.robot.framework.util.CommandMode;
import frc.robot.framework.util.ShuffleboardFramework;

public class SparkMaxController extends MotorController implements EncoderBase {
    private CANSparkMax controller;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;

    //I hate this but I have to keep track of it. Only one should be used
    private RelativeEncoder data_RelativeEncoder;
    private AbsoluteEncoder data_AbsoluteEncoder;

    public SparkMaxController(Element element, SubsystemCollection collection) {
        MotorType motorType=MotorType.kBrushless;
        if (element.getAttribute("motortype").toLowerCase().equals("brushed")){
            motorType=MotorType.kBrushed;
        }
        try{
            controller = new CANSparkMax(Integer.parseInt(element.getAttribute("port")) , motorType);
        } catch (NumberFormatException e){
            throw new NumberFormatException("SparkMaxController id:"+element.getAttribute("id")+" - Port number: '"+ element.getAttribute("port") +"'': Invalid value for 'port'");
        }
        controller.restoreFactoryDefaults();

        pidController = controller.getPIDController();

        if (element.getAttribute("idleMode").toLowerCase().equals("break")){
            controller.setIdleMode(IdleMode.kBrake);
        }
        else{
            controller.setIdleMode(IdleMode.kCoast);
        }
        

        //Get encoders.
        NodeList childNodeList = element.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element childElement = (Element) childNodeList.item(i);
            EncoderWrapper encoderWrapper;
            switch (childElement.getTagName().toLowerCase()){
                case "encoder":
                    String xml_port=childElement.getAttribute("port").toLowerCase();
                    
                    //data encoder on top
                    if (xml_port.equals("data")){
                        
                        int countsPerRev=8192;
                        if (childElement.hasAttribute("countsPerRev")){
                            try{
                                countsPerRev=Integer.parseInt(childElement.getAttribute("countsPerRev"));

                            } catch (NumberFormatException e){
                                throw new NumberFormatException("SparkMaxController id:"+element.getAttribute("id")+" - countsPerRev: '"+ childElement.getAttribute("countsPerRev") +"'': Invalid value for 'countsPerRev'");
                            }
                        }
                        
                        if (childElement.hasAttribute("type")){
                            if (childElement.getAttribute("type").toLowerCase().equals("relative")){
                                data_RelativeEncoder=controller.getAlternateEncoder(countsPerRev);
                                encoderWrapper = new EncoderWrapper(childElement, new SparkMaxEncoderRelativeEncoder(data_RelativeEncoder)); 
                               
                            }
                            else if (childElement.getAttribute("type").toLowerCase().equals("absolute")){ 
                                data_AbsoluteEncoder = controller.getAbsoluteEncoder(Type.kDutyCycle);
                                encoderWrapper = new EncoderWrapper(childElement, new SparkMaxEncoderAbsoluteEncoder(data_AbsoluteEncoder));

                                double zeroOffset=0;
                                if (childElement.hasAttribute("setZeroOffset")){
                                    try{
                                        zeroOffset=Double.parseDouble(childElement.getAttribute("setZeroOffset"));
                                    } catch (NumberFormatException e){
                                        throw new NumberFormatException("SparkMaxController id:"+element.getAttribute("id")+" - Encoder id: "+ childElement.getAttribute("id") +": Invalid value for 'setZeroOffset'");
                                    }
                                }
                                encoderWrapper.setPosition(zeroOffset);
                            }
                            else {
                                throw new UnsupportedOperationException("SparkMaxController id:"+element.getAttribute("id")+" - Encoder 'type' is not specified. Only 'data' or 'encoder' ports supported");
                            }
                        }
                        else{
                            throw new UnsupportedOperationException("SparkMaxController id:"+element.getAttribute("id")+" - Encoder 'type' is not specified. Only 'data' or 'encoder' ports supported");
                        }
                    }
                    //Normal encoder on side
                    else if (xml_port.equals("encoder")) {
                        encoder = controller.getEncoder();
                        encoderWrapper = new EncoderWrapper(childElement, new SparkMaxEncoderRelativeEncoder(encoder) ); 
                    }
                    else{
                        throw new UnsupportedOperationException("SparkMaxController id:"+element.getAttribute("id")+" - Encoder port: "+ childElement.getAttribute("port") +" not supported. Only 'data' or 'encoder' ports supported");
                    }
                    
                    super.encoder=encoderWrapper;
                    collection.encoders.put(childElement.getAttribute("id"), encoderWrapper);
                    //ShuffleboardFramework.getSubsystem(collection.subsystemName).addSendableToTab((childElement.getAttribute("id")+Math.random()).substring(0,18), encoderWrapper);
                    ShuffleboardFramework.getSubsystem(collection.subsystemName).addSendableToTab(childElement.getAttribute("id"), encoderWrapper);

                    break;
                case "pid":
                    SparkMaxPID pid = new SparkMaxPID(childElement, this);
                    if (childElement.getAttribute("encoderPort").toLowerCase().equals("encoder")){
                        pidController.setFeedbackDevice(encoder);
                    }else if (childElement.getAttribute("encoderPort").toLowerCase().equals("data")){
                        if (data_RelativeEncoder != null){
                            pidController.setFeedbackDevice(data_RelativeEncoder);
                        }
                        else if (data_AbsoluteEncoder != null){
                            pidController.setFeedbackDevice(data_AbsoluteEncoder);
                        }
                        else{
                            throw new UnsupportedOperationException("SparkMaxController: Setting feedback device on Pid - Encoder is not initalized");
                        }
                    }else{
                        throw new UnsupportedOperationException("SparkMaxController id:"+element.getAttribute("id")+" - PID encoderPort: "+ childElement.getAttribute("encoderPort") +" not supported. Only 'encoder' or 'data' ports supported");
                    }
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
                    super.pid=pid;
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
        
        controller.burnFlash();
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
    public double getAbsolutePosition() {
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

    @Override
    public void setPosition(double position) {
        encoder.setPosition(position);
        
    }

    @Override
    public PIDBase getPID(){
        if (pid !=null)
           return pid;
        else
            throw new UnsupportedOperationException("SparkMaxController: pid is null. Can't getPID");
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        //builder.setSmartDashboardType("Motor Controller");
        // builder.setActuator(true);
        // builder.setSafeState(this::disable);
        builder.addDoubleProperty("Position", this::getPosition, this::setPosition);
        builder.addDoubleProperty("Velocity", this::getVelocity, null);
    }
}