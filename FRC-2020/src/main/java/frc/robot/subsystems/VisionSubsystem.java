/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

import frc.robot.commands.VisionCommand;


public class VisionSubsystem extends SubsystemBase {
  //#region Variables
  final private double maxMotorPercent = 1.0;   // pretty plz dont change this
  
  private int goalXerror;
  private int goalYerror;

  private int halfWidth = 320/2;
  private int fullHeight = 240;

  private double pivotMotorPercent = 0.80;      // CONFIG

  private double leftMotorPercent;
  private double rightMotorPercent;

  private double leftRelativePercent;
  private double rightRelativePercent;

  private double maxMotorValue = 50.0;           // CONFIG

  private int trackingMode = 0;                  // CONFIG
  //#endregion

  //#region Getters and setters
  public int getGoalXerror() {
    return this.goalXerror;
  }

  public void setGoalXerror(int goalXerror) {
    this.goalXerror = goalXerror;
  }
  public int getGoalYerror() {
    return this.goalYerror;
  }

  public void setGoalYerror(int goalYerror) {
    this.goalYerror = goalYerror;
  }

  public int getHalfWidth() {
    return this.halfWidth;
  }

  public int getFullHeight() {
    return this.fullHeight;
  }

  public double getPivotMotorPercent() {
    return this.pivotMotorPercent;
  }

  public double getLeftMotorPercent() {
    return this.leftMotorPercent;
  }
  public void setLeftMotorPercent(double leftMotorPercent) {
    this.leftMotorPercent = leftMotorPercent;
  }

  public double getRightMotorPercent() {
    return this.rightMotorPercent;
  }
  public void setRightMotorPercent(double rightMotorPercent) {
    this.rightMotorPercent = rightMotorPercent;
  }

  public double getLeftRelativePercent() {
    return this.leftRelativePercent;
  }
  public void setLeftRelativePercent(double leftRelativePercent) {
    this.leftRelativePercent = leftRelativePercent;
  }
  
  public double getRightRelativePercent() {
    return this.rightRelativePercent;
  }
  public void setRightRelativePercent(double rightRelativePercent) {
    this.rightRelativePercent = rightRelativePercent;
  }

  public double getMaxMotorPercent() {
		return this.maxMotorPercent;
  }
  
  public double getMaxMotorValue() {
    return this.maxMotorValue;
  }
  public void setMaxMotorValue(double maxMotorValue) {
		this.maxMotorValue = maxMotorValue;
	}

  public int getTrackingMode() {
    return this.trackingMode;
  }

  public void setTrackingMode(int trackingMode) {
		this.trackingMode = trackingMode;
  }

  //#endregion

  //#region Set default command
  @Override
  public void periodic() {
    setDefaultCommand(new VisionCommand());
  }
  //#endregion

  // #region Calculate the correct motor percentages for turning and driving towards ball
  public void CalculateMotorPivot() {

    // if ball is on left side of camera view
    if (Robot.getVisionDataSubsystem.getBallXcenter() <= getHalfWidth()) {
      // Left motor pivot math
      setLeftRelativePercent( Robot.getVisionDataSubsystem.getBallXcenter() / getHalfWidth());
      setLeftMotorPercent(getPivotMotorPercent() * getLeftRelativePercent());

      // Set right motor to full speed
      setRightMotorPercent(getMaxMotorPercent());
    } 
    // if ball is on right side of camera view
    else if (Robot.getVisionDataSubsystem.getBallXcenter() > getHalfWidth()) {
      // Set left motor to full speed
      setLeftMotorPercent(getMaxMotorPercent());
      
      // Right motor pivot math
      setRightRelativePercent( (Robot.getVisionDataSubsystem.getBallXcenter() - getHalfWidth()) / getHalfWidth());
      setRightMotorPercent(getPivotMotorPercent() * getRightRelativePercent());
    }
  }
  //#endregion



  // #region Set left and right motors to previously calculated motor values (based on percentages)
  public void DriveTowardsBall() {
    // Perform necessary calculations to locate and drive towards the ball
    CalculateMotorPivot();

    // Set left motor speed (by value) based on calculated pivot OR full speed
    Robot.driveSubsystem.setLeftMotorValue(getMaxMotorValue() * getLeftMotorPercent());

    // Set right motor speed (by value) based on calculated pivot OR full speed
    Robot.driveSubsystem.setRightMotorValue(getMaxMotorValue() * getRightMotorPercent());
  }
  //#endregion
  
  //#region Align robot with the high-goal in order to shoot
  public void AlignWithHighgoal() {
    
  }
  //#endregion
}