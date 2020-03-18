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



  //#region ===============================BEGINNING OF FIRE DELAY VARIABLES======================================================
  private boolean startFireDelayTimer = false;
  public boolean getStartFireDelayTimer() {
    return this.startFireDelayTimer;
  }
  public void setStartFireDelayTimer(boolean startFireDelayTimer) {
		this.startFireDelayTimer = startFireDelayTimer;
	}
  //========================================================================================
  private long fireDelayTimerStartTime = 0;
  public long getFireDelayTimerStartTime() {
    return this.fireDelayTimerStartTime;
  }
  public void setFireDelayTimerStartTime(long fireDelayTimerStartTime) {
		this.fireDelayTimerStartTime = fireDelayTimerStartTime;
  }
  //========================================================================================
  //#endregion  ===============================END OF LOAD DELAY VARIABLES======================================================

  //#region ===============================BEGINNING OF LOAD DELAY VARIABLES======================================================
  private boolean readyForLoadMotor = false;
  public boolean getReadyForLoadMotor() {
    return this.readyForLoadMotor;
  }
  public void setReadyForLoadMotor(boolean readyForLoadMotor) {
		this.readyForLoadMotor = readyForLoadMotor;
	}
  //========================================================================================
  private boolean startLoadDelayTimer = false;
  public boolean getStartLoadDelayTimer() {
    return this.startLoadDelayTimer;
  }
  public void setStartLoadDelayTimer(boolean startLoadDelayTimer) {
		this.startLoadDelayTimer = startLoadDelayTimer;
  }
  //========================================================================================
  private long loadDelayTimerStartTime = 0;
  public long getLoadDelayTimerStartTime() {
    return this.loadDelayTimerStartTime;
  }
  public void setLoadDelayTimerStartTime(long loadDelayTimerStartTime) {
		this.loadDelayTimerStartTime = loadDelayTimerStartTime;
	}
  //========================================================================================
  private boolean stopShootLoadDelay = false;
  public boolean getStopShootLoadDelay() {
    return this.stopShootLoadDelay;
  }
  public void setStopShootLoadDelay(boolean stopShootLoadDelay) {
		this.stopShootLoadDelay = stopShootLoadDelay;
	}
  //#endregion  ==================================END OF LOAD DELAY VARIABLES=====================================================




  public ManipulatorSubsystem() {
    bottomFireMotor = new WPI_TalonSRX(Constants.MAN_FIRE_BOTTOM_TALON_SRX_ID);
    topFireMotor = new WPI_TalonSRX(Constants.MAN_FIRE_TOP_TALON_SRX_ID);
    loadMotor = new WPI_TalonSRX(Constants.MAN_LOAD_TALON_SRX_ID);
    intakeMotor = new WPI_VictorSPX(Constants.MAN_INTAKE_VICTOR_SPX_ID);
  }
  //set and get the motors stuff

  //#region control the motors
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
  //#endregion

  //#region stop the motors
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
  //#endregion

  //#region read the motors
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
  //#endregion
  
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
  public double getPivotPotentiometer() {
    return pivotPotentiometer.get();
  }

  public void selectLaunch(int p) {
    launchPosition = p;
    SmartDashboard.putNumber("Launch Selection", launchPosition);
  }

  public void StartLoadingWithDelay(boolean subUsingLoadDelay, long subLoadDelayTime) {
    // If the manipulator is ready for loading
    if (getReadyForLoadMotor() == true) {
      // If the load motor is being autonomously used with a delay (called by vision)
      if (subUsingLoadDelay) {
        // Start the timer and get the starting system time to use for calculating difference in time elapsed
        if (getStartLoadDelayTimer() == false) {
          setLoadDelayTimerStartTime(System.currentTimeMillis());
          setStartLoadDelayTimer(true);

          // Set the load motor's speed percentage just once (don't wanna keep resetting it's speed, that'd be small brain)
          setLoadMotor(Constants.shootLoadMotorSpeedPercent);
        }

        // If the elapsed time since the start time is over the wanted delay time
        if (  (System.currentTimeMillis() - getLoadDelayTimerStartTime()) >= subLoadDelayTime) {
          setStopShootLoadDelay(true);
        }
      // If the load motor is being manually controlled with a "while the button is held" (called by teleop manipulator)
      } else {
        setLoadMotor(Constants.teleopLoadMotorPercent);
      }
    }
  }

  public void semiAutoFire(boolean usingLoadDelay, long loadDelayTime) {
    SmartDashboard.putNumber("Bottom Fire", getBottomFireMotor());
    SmartDashboard.putNumber("Top Fire", getTopFireMotor());
    SmartDashboard.putNumber("Load", getLoadMotor());

    switch (launchPosition) {
      case 1:
        bottomFireValue = .24;
        topFireValue = -.6;
        waitTime = 1500;
        break;
      case 2:
        bottomFireValue = .5;
        topFireValue = -.5;
        waitTime = 1000;
        break;
      case 3:
        bottomFireValue = .3;
        topFireValue = -.3;
        waitTime = 250;
        break;
    }

    // Start the timer for delaying the fire motors
    if (getStartFireDelayTimer() == false) {
      setStartFireDelayTimer(true);
      setFireDelayTimerStartTime(System.currentTimeMillis());

      // Set the fire motor's speed percentages just once (don't wanna keep resetting it's speed, that'd be small brain)
      setBottomFireMotor(bottomFireValue);
      setTopFireMotor(topFireValue);
    }

    // If the robot has been running the Bottom and Top fire motors for the wanted waitTime, indicate that we are ready for loading
    if (    (System.currentTimeMillis() - getFireDelayTimerStartTime()) >= waitTime   ) {
      setReadyForLoadMotor(true);
    }

    // Turn load motors for either "while-held-teleop" or with "timed-delay-autonomous"
    StartLoadingWithDelay(usingLoadDelay, loadDelayTime);
  }

  public void ResetMotorTimersAndLoadReadiness() {
    // Reset the timers used to delay the motors using time-elapsed
    setStartFireDelayTimer(false);
    setStartLoadDelayTimer(false);

    // Reset indicator that manipulator is ready for loading
    setReadyForLoadMotor(false);
  }

  public void StopAndResetManipulator() {
    // Stop all manipulator motors
    stopBottomFireMotor();
    stopTopFireMotor();
    stopLoadMotor();

    // Reset the fire and load motor delay timers
    ResetMotorTimersAndLoadReadiness();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}