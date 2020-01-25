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

public class ShootSubsystem extends SubsystemBase {
  /**
   * Creates a new ShootSubsystem.
   */
  private WPI_TalonSRX rightFireMotor;
  private WPI_TalonSRX leftFireMotor;
  private WPI_TalonSRX loadMotor;
  private WPI_TalonSRX pivotMotor;
  private WPI_TalonSRX intakeMotor;

  public ShootSubsystem() {
    rightFireMotor = new WPI_TalonSRX(Constants.SHOOT_FIRE_RIGHT_TALON_SRX_ID);
    leftFireMotor = new WPI_TalonSRX(Constants.SHOOT_FIRE_LEFT_TALON_SRX_ID);
    loadMotor = new WPI_TalonSRX(Constants.SHOOT_LOAD_TALON_SRX_ID);
    pivotMotor = new WPI_TalonSRX(Constants.SHOOT_PIVOT_TALON_SRX_ID);
    intakeMotor = new WPI_TalonSRX(Constants.SHOOT_INTAKE_TALON_SRX_ID);
  }
  //set and get the motors stuff

  //control right spinning fire motor
  public void setRightFireMotor(double motorValue){
    rightFireMotor.set(ControlMode.PercentOutput, motorValue);
  }

  //read right spinning fire motor
  public double getRightFireMotor(){
    return rightFireMotor.getMotorOutputPercent();
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
