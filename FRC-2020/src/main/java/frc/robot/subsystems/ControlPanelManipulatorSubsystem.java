/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ControlPanelManipulatorSubsystem extends SubsystemBase {
  /**
   * Creates a new ControlPanelManipulatorSubsystem
   */
  private WPI_TalonSRX controlPanelManipulatorMotor;
  private WPI_TalonSRX controlPanelManipulatorRotationMotor;

  public DigitalInput rotationLimitSwitch;


  public ControlPanelManipulatorSubsystem() {
    controlPanelManipulatorMotor = new WPI_TalonSRX(Constants.CONTROL_PANEL_MANIPULATOR_TALON_SRX_ID);
    controlPanelManipulatorRotationMotor = new WPI_TalonSRX(Constants.CONTROL_PANEL_ROTATOR_TALON_SRX_ID);

    rotationLimitSwitch = new DigitalInput(Constants.CONTROL_PANEL_LIMIT_SWITCH_INPUT_ID);

  }
  public void SetMotor(double motorValue) {
    controlPanelManipulatorMotor.set(ControlMode.PercentOutput, motorValue);
      
  }

  public void SetRotation(double motorValue) {
    controlPanelManipulatorRotationMotor.set(ControlMode.PercentOutput, motorValue);
  }

  // set the rotation breaks to break or coast
  public void setBrakemodeRotation(boolean isBraking) {
    // when true, set to breaking mode
    if(isBraking) {
      controlPanelManipulatorRotationMotor.setNeutralMode(NeutralMode.Brake);
    } else { // else set to coast
      controlPanelManipulatorRotationMotor.setNeutralMode(NeutralMode.Coast);
    }
  }
  // set the left breaks to break or coast
  public void setBrakemodeWheel(boolean isBraking) {
    // when true, set to breaking mode
    if(isBraking) {
      controlPanelManipulatorMotor.setNeutralMode(NeutralMode.Brake);
    } else { // else set to coast
      controlPanelManipulatorMotor.setNeutralMode(NeutralMode.Coast);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
