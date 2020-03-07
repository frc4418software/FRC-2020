/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ManipulatorSubsystem extends SubsystemBase {
  /**
   * Creates a new ShootSubsystem.
   */
  private WPI_TalonSRX bottomFireMotor;
  private WPI_TalonSRX topFireMotor;
  private WPI_TalonSRX loadMotor;
  private WPI_TalonSRX pivotMotor;
  private WPI_VictorSPX intakeMotor;

  private Encoder topFireEncoder;
  private Encoder bottomFireEncoder;

  private AnalogPotentiometer pivotPotentiometer;

  private boolean pivotUp = true;

  public boolean launchSpin;
  public int launchPosition;
  double bottomFireValue;
  double topFireValue;
  int waitTime;
  //button 7 - launch position 1 is for high goal at distance 1.8m
  //button 8 - launch position 2 is for high goal at distance 0m
  //button 9 - launch position 3 is for low goal at distance 0m

  public ManipulatorSubsystem() {
    bottomFireMotor = new WPI_TalonSRX(Constants.MAN_FIRE_BOTTOM_TALON_SRX_ID);
    topFireMotor = new WPI_TalonSRX(Constants.MAN_FIRE_TOP_TALON_SRX_ID);
    loadMotor = new WPI_TalonSRX(Constants.MAN_LOAD_TALON_SRX_ID);
    intakeMotor = new WPI_VictorSPX(Constants.MAN_INTAKE_VICTOR_SPX_ID);
    
    topFireEncoder = new Encoder(1,0);
    bottomFireEncoder = new Encoder(0,1);

    topFireEncoder.setDistancePerPulse(1);
    bottomFireEncoder.setDistancePerPulse(1);

    topFireEncoder.reset();
    bottomFireEncoder.reset();
  }
  //set and get the motors stuff

  //control bottom fire motor
  //right is bottom for now
  public void setBottomFireMotor(double motorValue){
    bottomFireMotor.set(ControlMode.PercentOutput, motorValue);
  }
  public void setTopFireMotor(double motorValue){
    topFireMotor.set(ControlMode.PercentOutput, motorValue);
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

  //stop the motors
  public void stopBottomFireMotor(){
    bottomFireMotor.set(ControlMode.PercentOutput, 0);
  }
  public void stopTopFireMotor(){
    topFireMotor.set(ControlMode.PercentOutput, 0);
  }
  public void stopLoadMotor(){
    loadMotor.set(ControlMode.PercentOutput, 0);
  }
  public void stopIntakeMotor(){
    intakeMotor.set(ControlMode.PercentOutput, 0);
  }

  //read bottom fire motor
  public double getBottomFireMotor(){
    return bottomFireMotor.getMotorOutputPercent();
  }
  public double getTopFireMotor(){
    return topFireMotor.getMotorOutputPercent();
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

  //read the encoders
  public double getTopFireEncoder(){
    return topFireEncoder.getRate();
  }
  public double getBottomFireEncoder(){
    return bottomFireEncoder.getRate();
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

  public void selectLaunch(int p){
    launchPosition = p;
    SmartDashboard.putNumber("Launch Selection", launchPosition);
  }

  public void semiAutoFire(){
    SmartDashboard.putNumber("Bottom Fire", getBottomFireMotor());
    SmartDashboard.putNumber("Top Fire", getTopFireMotor());
    SmartDashboard.putNumber("Load", getLoadMotor());
    SmartDashboard.putNumber("Top Fire rate", getTopFireEncoder());
    SmartDashboard.putNumber("Bottom Fire rate", getBottomFireEncoder());
    if(launchPosition == 1){
      bottomFireValue = .24;
      topFireValue = -.6;
      waitTime = 1500;
    }
    else if(launchPosition == 2){
      bottomFireValue = .5;
      topFireValue = -.5;
      waitTime = 1000;
    }
    else if(launchPosition == 3){
      bottomFireValue = .3;
      topFireValue = -.3;
      waitTime = 250;
    }
    setBottomFireMotor(bottomFireValue);
    setTopFireMotor(topFireValue);
    try{
      Thread.sleep(waitTime);
    } catch (InterruptedException e){
      e.printStackTrace();
    }
    setLoadMotor(.5);

  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}