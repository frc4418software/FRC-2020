/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
//import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShootSubsystem extends SubsystemBase {
  /**
   * Creates a new ShootSubsystem.
   */
  private WPI_VictorSPX rightFireMotor;
  private WPI_VictorSPX leftFireMotor;
  private WPI_VictorSPX loadMotor;
  private WPI_VictorSPX pivotMotor;
  private WPI_VictorSPX intakeMotor;

  private AnalogPotentiometer pivotPotentiometer;

  private boolean pivotUp = true;

  public ShootSubsystem() {
    rightFireMotor = new WPI_VictorSPX(Constants.SHOOT_FIRE_RIGHT_TALON_SRX_ID);
    leftFireMotor = new WPI_VictorSPX(Constants.SHOOT_FIRE_LEFT_TALON_SRX_ID);
    loadMotor = new WPI_VictorSPX(Constants.SHOOT_LOAD_TALON_SRX_ID);
    pivotMotor = new WPI_VictorSPX(Constants.SHOOT_PIVOT_TALON_SRX_ID);
    intakeMotor = new WPI_VictorSPX(Constants.SHOOT_INTAKE_TALON_SRX_ID);

    pivotPotentiometer = new AnalogPotentiometer(Constants.SHOOT_PIVOT_POTENTIOMETER_ID);
  }
  //set and get the motors stuff

  //control right spinning fire motor
  public void setRightFireMotor(double motorValue){
    rightFireMotor.set(ControlMode.PercentOutput, motorValue);
  }
  public void setLeftFireMotor(double motorValue){
    leftFireMotor.set(ControlMode.PercentOutput, motorValue);
  }
  public void setLoadMotor(double motorValue){
    loadMotor.set(ControlMode.PercentOutput, motorValue);
  }
  public void setIntakeMotor(double motorValue){
    intakeMotor.set(ControlMode.PercentOutput, motorValue);
  }
  public void setPivotMotor(double motorValue){
    pivotMotor.set(ControlMode.PercentOutput, motorValue);
  }

  //read right spinning fire motor
  public double getRightFireMotor(){
    return rightFireMotor.getMotorOutputPercent();
  }
  public double getLeftFireMotor(){
    return leftFireMotor.getMotorOutputPercent();
  }
  public double getLoadMotor(){
    return loadMotor.getMotorOutputPercent();
  }
  public double getIntakeMotor(){
    return intakeMotor.getMotorOutputPercent();
  }
  public double getPivotMotor(){
    return pivotMotor.getMotorOutputPercent(); 
  }
  
  // get whether the pivot is up
  public boolean pivotIsUp(){
    if( getPivotPotentiometer() > 13){
      return pivotUp;
    }
    else{
      return !pivotUp;
    }
  }
  



  //Potentiometer stuffs (I hope this works)
  //read potentiometer
  public double getPivotPotentiometer(){
    return pivotPotentiometer.get();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
