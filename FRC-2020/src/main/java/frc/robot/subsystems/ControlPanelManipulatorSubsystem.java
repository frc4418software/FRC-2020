/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ControlPanelManipulatorSubsystem extends SubsystemBase {
  /**
   * Creates a new ControlPanelManipulatorSubsystem
   */
  private WPI_TalonSRX controlPanelManipulatorMotor;

  public ControlPanelManipulatorSubsystem() {
    controlPanelManipulatorMotor = new WPI_TalonSRX(Constants.COLOR_PANEL_MANIPULATOR_TALON_SRX_ID);
  
  }
  public void SetMotor(double motorValue) {
    controlPanelManipulatorMotor.set(ControlMode.PercentOutput, motorValue);
      
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
