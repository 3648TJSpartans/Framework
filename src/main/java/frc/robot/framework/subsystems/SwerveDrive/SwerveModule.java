package frc.robot.framework.subsystems.SwerveDrive;

import org.w3c.dom.Element;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.framework.controller.ControllerBase;
import frc.robot.framework.robot.*;
import frc.robot.framework.util.CommandMode;
import frc.robot.framework.algorithm.SparkMaxPID;

public class SwerveModule {
    private SubsystemCollection subsystemColection;
    private String driveMotorID = "drive";
    private String turnMotorID = "turn";
    private String driveEncoderID= "driveEncoder";
    private String turnEncoderID= "turnEncoder";
    private String analogID = "analog_encoder";

    // double minV;
    // double maxV;

    Element myElement;

    public SwerveModule(Element _myElement) {
        myElement = _myElement;
        // minV = Double.parseDouble(myElement.getAttribute("minV"));
        // maxV = Double.parseDouble(myElement.getAttribute("maxV"));
        subsystemColection = new SubsystemCollection(_myElement);

    }

    public double getDrivePosition() {
        return subsystemColection.encoders.getPosition(driveEncoderID);
    }

    public double getTurningPosition() {
        return subsystemColection.encoders.getPosition(turnEncoderID);
    }

    // private void updateBounds(double v) {
    //     minV = Math.min(minV, v);
    //     maxV = Math.max(maxV, v);
    // }

    public double getDriveVelocity() {
        return subsystemColection.encoders.getVelocity(driveEncoderID);
    }

    public double getTurningVelocity() {
        return subsystemColection.encoders.getVelocity(turnEncoderID);
    }

    public SwerveModulePosition getState() {
        //IDK about this check later if theres an error
        return new SwerveModulePosition(getDrivePosition(), new Rotation2d(getTurningPosition()));
    }

    public void setDesiredState(SwerveModuleState state) {
        if (Math.abs(state.speedMetersPerSecond) < 0.001) {
            stop();
            return;
        }
        state = SwerveModuleState.optimize(state, getState().angle);
        subsystemColection.motors.setOutput(driveMotorID, state.speedMetersPerSecond / 5, CommandMode.PERCENTAGE);
        subsystemColection.motors.setOutput(turnMotorID, (state.angle.getDegrees() % 360) / 360, CommandMode.POSITION);
    }


    public void stop() {
        subsystemColection.motors.setOutput(driveMotorID, 0, CommandMode.PERCENTAGE);
        subsystemColection.motors.setOutput(turnMotorID, 0, CommandMode.PERCENTAGE);
    }
}
