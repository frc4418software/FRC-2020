/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ClimbSubsystem extends SubsystemBase {
  /**
   * Creates a new ClimbSubsystem.
   */

  private WPI_TalonSRX frontClimbMotor;
  private WPI_TalonSRX backClimbMotor;

  public ClimbSubsystem() {
    frontClimbMotor = new WPI_TalonSRX(Constants.FRONT_CLIMBER_TALON_SRX_ID);
    backClimbMotor = new WPI_TalonSRX(Constants.BACK_CLIMBER_TALON_SRX_ID);

  }

  public void setFrontClimb(double motorValue) {
    frontClimbMotor.set(ControlMode.PercentOutput, motorValue);
  }
  public void setBackClimb(double motorValue) {
    backClimbMotor.set(ControlMode.PercentOutput, motorValue);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
