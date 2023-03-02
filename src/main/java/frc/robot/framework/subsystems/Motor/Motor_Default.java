package frc.robot.framework.subsystems.Motor;

import org.w3c.dom.Element;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.util.CommandMode;

public class Motor_Default extends CommandBase implements RobotXML {

	private Element myElement;
	private Motor motorSubsystem;
	private ControllerBase myController;
	private double deadzone = .05;
	private double scale = 1;

	private int axisNumberPower1 = -1;
	private int axisNumberPower2 = -1;
	
	private boolean inverted1 = false;
	private boolean inverted2 = false;
	private boolean wasUsingAxis = false;

	public Motor_Default(Element element, ControllerBase controller) {
		myElement = element;
		myController = controller;

		SubsystemBase temp = RobotInit.GetSubsystem(myElement.getAttribute("subsystemID"));
		if (temp == null || !(temp instanceof Motor)) {
			System.out
					.println("Motor_Default could not find Motor subsystem with id:" + myElement.getAttribute("subsystemID"));
			return;
		}
		
		motorSubsystem = (Motor) temp;
		this.addRequirements(motorSubsystem);
		CommandScheduler.getInstance().setDefaultCommand(motorSubsystem, this);

		if (element.hasAttribute("deadzone"))
			deadzone = Double.parseDouble(element.getAttribute("deadzone"));

		if (element.hasAttribute("scale"))
			scale = Double.parseDouble(element.getAttribute("scale"));

		if (element.hasAttribute("axisReference1") || element.hasAttribute("axisReference")) {
			axisNumberPower1 = element.hasAttribute("axisReference1") ? 
				myController.GetAxisMap().get(element.getAttribute("axisReference1")) : 
				myController.GetAxisMap().get(element.getAttribute("axisReference"));
		}

		if (element.hasAttribute("axisReference2"))
			axisNumberPower2 = myController.GetAxisMap().get(element.getAttribute("axisReference2"));
			
		if (element.hasAttribute("inverted1") || element.hasAttribute("inverted")){
			inverted1 = element.hasAttribute("inverted1") ? Boolean.parseBoolean(element.getAttribute("inverted1")) : Boolean.parseBoolean(element.getAttribute("inverted")) ;
		}
		if (element.hasAttribute("inverted2"))
			inverted2 = Boolean.parseBoolean(element.getAttribute("inverted2"));

	}

	@Override
		public void initialize(){
		}

	@Override
	public void execute() {
		double input1 = myController.getAxis(axisNumberPower1);
		if (Math.abs(input1) > deadzone) {
			wasUsingAxis=true;
			if (inverted1)
				motorSubsystem.setReference(-input1*scale);
			else
				motorSubsystem.setReference(input1*scale, CommandMode.PERCENTAGE);
			System.out.println("Motor Default is writing out");

			return; //Got a valid value for axis one. no need to parse 2nd axis
		}

		if (axisNumberPower2==-1){
			wasUsingAxis=false;
			motorSubsystem.setReference(0,CommandMode.PERCENTAGE);
			return;
		}
		double input2 = myController.getAxis(axisNumberPower2);
		if (Math.abs(input2) > deadzone) {
			wasUsingAxis=true;
			System.out.println("Motor Default is writing out");

			if (inverted2)
				motorSubsystem.setReference(-input2*scale);
			else
				motorSubsystem.setReference(input2*scale, CommandMode.PERCENTAGE);
			return; //Got a valid value for axis one. no need to parse 2nd axis
		}
		if (wasUsingAxis){
			wasUsingAxis=false;
			motorSubsystem.setReference(0,CommandMode.PERCENTAGE);
			return;
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void ReadXML(Element element) {

	}

	@Override
	public void ReloadConfig() {

	}
}