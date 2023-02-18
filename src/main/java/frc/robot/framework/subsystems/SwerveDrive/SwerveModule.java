// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.framework.subsystems.SwerveDrive;

import org.w3c.dom.Element;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.framework.robot.SubsystemCollection;
import frc.robot.framework.subsystems.SwerveDrive.Constants.ModuleConstants;
import frc.robot.framework.util.CommandMode;


public class SwerveModule {

  private double m_chassisAngularOffset = 0;
  private SwerveModuleState m_desiredState = new SwerveModuleState(0.0, new Rotation2d());
  private Element element;
  private SubsystemCollection subsystemColection;

  private String driveMotorID = "drive";
  private String turnMotorID = "turn";
  private String driveEncoderID= "driveEncoder";
  private String turnEncoderID= "turnEncoder";

  /**
   * Constructs a MAXSwerveModule and configures the driving and turning motor,
   * encoder, and PID controller. This configuration is specific to the REV
   * MAXSwerve Module built with NEOs, SPARKS MAX, and a Through Bore
   * Encoder.
   */
  public SwerveModule(Element myElement) {

    element = myElement;
    subsystemColection = new SubsystemCollection(element);

    m_chassisAngularOffset = Double.parseDouble(element.getAttribute("angularOffset"));
    m_desiredState.angle = new Rotation2d(subsystemColection.encoders.getPosition(turnEncoderID));
    subsystemColection.encoders.reset(driveEncoderID);
  }

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    // Apply chassis angular offset to the encoder position to get the position
    // relative to the chassis.
    return new SwerveModuleState(subsystemColection.encoders.getVelocity(driveEncoderID),
        new Rotation2d(subsystemColection.encoders.getPosition(turnEncoderID) - m_chassisAngularOffset));
  }

  /**
   * Returns the current position of the module.
   *
   * @return The current position of the module.
   */
  public SwerveModulePosition getPosition() {
    // Apply chassis angular offset to the encoder position to get the position
    // relative to the chassis.
    return new SwerveModulePosition(
      subsystemColection.encoders.getPosition(driveEncoderID),
        new Rotation2d(subsystemColection.encoders.getPosition(turnEncoderID) - m_chassisAngularOffset));
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Apply chassis angular offset to the desired state.
    SwerveModuleState correctedDesiredState = new SwerveModuleState();
    correctedDesiredState.speedMetersPerSecond = desiredState.speedMetersPerSecond;
    correctedDesiredState.angle = desiredState.angle.plus(Rotation2d.fromRadians(m_chassisAngularOffset));

    // Optimize the reference state to avoid spinning further than 90 degrees.
    SwerveModuleState optimizedDesiredState = SwerveModuleState.optimize(correctedDesiredState,
        new Rotation2d(subsystemColection.encoders.getPosition(turnEncoderID)));

    // Command driving and turning SPARKS MAX towards their respective setpoints.
    subsystemColection.motors.setOutput(driveMotorID, optimizedDesiredState.speedMetersPerSecond, CommandMode.VELOCITY);
    subsystemColection.motors.setOutput(turnMotorID, optimizedDesiredState.angle.getRadians(), CommandMode.POSITION);

    m_desiredState = desiredState;
  }

  /** Zeroes all the SwerveModule encoders. */
  public void resetEncoders() {
    subsystemColection.encoders.reset(driveEncoderID);
  }
}
