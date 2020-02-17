/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.commands.DriveToBallCommand;


public class DriveToBallSubsystem extends SubsystemBase {

  private int xError;
  private int yError;

  private int halfWidth = 320/2;
  private int fullHeight = 240;

  private double pivotMotorPercent = 0.50;

  private double leftMotorPercent;
  private double rightMotorPercent;

  private double leftRelativePercent;
  private double rightRelativePercent;

  private double maxMotorPercent = 1.0;
  private double maxMotorValue = 50.0;

  //#region xError getter and setter
  public int getxError() {
    return this.xError;
  }
  public void setXerror(int xError) {
    this.xError = xError;
  }
  //#endregion

  //#region yError getter and setter
  public int getYerror() {
    return this.yError;
  }
  public void setYerror(int yError) {
    this.yError = yError;
  }
  //#endregion

  //#region halfWidth getter
  public int getHalfWidth() {
    return this.halfWidth;
  }
  //#endregion

  //#region fullHeight getter
  public int getFullHeight() {
    return this.fullHeight;
  }
  //#endregion

  //#region pivotMotorPercent getter
  public double getPivotMotorPercent() {
    return this.pivotMotorPercent;
  }
  //#endregion

  //#region leftMotorPercent getter and setter
  public double getLeftMotorPercent() {
    return this.leftMotorPercent;
  }
  public void setLeftMotorPercent(double leftMotorPercent) {
    this.leftMotorPercent = leftMotorPercent;
  }
  //#endregion

  //#region rightMotorPercent getter and setter
  public double getRightMotorPercent() {
    return this.rightMotorPercent;
  }
  public void setRightMotorPercent(double rightMotorPercent) {
    this.rightMotorPercent = rightMotorPercent;
  }
  //#endregion

  //#region leftRelativePercent getter and setter
  public double getLeftRelativePercent() {
    return this.leftRelativePercent;
  }
  public void setLeftRelativePercent(double leftRelativePercent) {
    this.leftRelativePercent = leftRelativePercent;
  }
  //#endregion
  
  //#region rightRelativePercent getter and setter
  public double getRightRelativePercent() {
    return this.rightRelativePercent;
  }
  public void setRightRelativePercent(double rightRelativePercent) {
    this.rightRelativePercent = rightRelativePercent;
  }
  //#endregion

  //#region maxMotorPercent getter
  public double getMaxMotorPercent() {
		return this.maxMotorPercent;
  }
  //#endregion
  
  //#region maxMotorValue getter
  public double getMaxMotorValue() {
    return this.maxMotorValue;
  }
  public void setMaxMotorValue(double maxMotorValue) {
		this.maxMotorValue = maxMotorValue;
	}
  //#endregion

  // Set default command to DriveToBallCommand
  @Override
  public void periodic() {
    setDefaultCommand(new DriveToBallCommand());
  }

  // Calculate ball coordinate difference from bottom center of camera view
  public void CalculateCoordError() {
    // calculate how far x coord of ball is from center of camera view
    setXerror(getHalfWidth() - Robot.hsvDetectBallSubsystem.getBallXcenter());

    // calculate how far y coord of ball is from bottom of camera view
    setYerror(getFullHeight() - Robot.hsvDetectBallSubsystem.getBallYcenter());
  }

  // Calculate the correct motor percentages for turning and driving towards ball
  public void CalculateMotorPivot() {
    // if ball is on left side of camera view
    if (Robot.hsvDetectBallSubsystem.getBallXcenter() <= getHalfWidth()) {
      // Left motor pivot math
      setLeftRelativePercent(( Robot.hsvDetectBallSubsystem.getBallXcenter() ) / getHalfWidth());
      setLeftMotorPercent(getPivotMotorPercent() * getLeftRelativePercent());

      // Set right motor to full speed
      setRightMotorPercent(getMaxMotorPercent());
    } 
    // if ball is on right side of camera view
    else if (Robot.hsvDetectBallSubsystem.getBallXcenter() > getHalfWidth()) {
      // Set left motor to full speed
      setLeftMotorPercent(getMaxMotorPercent());
      
      // Right motor pivot math
      setRightRelativePercent(( Robot.hsvDetectBallSubsystem.getBallXcenter() - getHalfWidth()) / getHalfWidth());
      setRightMotorPercent(getPivotMotorPercent() * getRightRelativePercent());
    }
  }

  // Set left and right motors to previously calculated motor values (based on percentages)
  public void DriveTowardsBall() {
    // Set left motor speed (by value) based on calculated pivot OR full speed
    Robot.driveSubsystem.setLeftMotorValue(getMaxMotorValue() * getLeftMotorPercent());

    // Set right motor speed (by value) based on calculated pivot OR full speed
    Robot.driveSubsystem.setRightMotorValue(getMaxMotorValue() * getRightMotorPercent());
  }
}