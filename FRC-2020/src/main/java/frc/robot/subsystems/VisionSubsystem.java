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

  private int halfWidth = 320/2;
  private int fullHeight = 240;

  private double pivotMotorPercent = 0.80;        // CONFIG

  private double leftMotorPercent;
  private double rightMotorPercent;

  private double maxMotorValue = 50.0;            // CONFIG

  private int trackingMode = 0;                   // CONFIG
  
  private short goalXAlignThreshold = 15;         // CONFIG

  private short goalYCoordTarget = 200;           // CONFIG
  private short goalYCoordThreshold = 15;         // CONFIG
  
  private short goalAlignTimeThreshold = 2000;    // CONFIG

  private boolean stopwatchStarted = false;
  private long startStopwatch;

  private int alignStage = 1;
  //#endregion

  //#region Getters and setters
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

  public short getGoalXAlignThreshold() {
    return this.goalXAlignThreshold;
  }

  public void setGoalXAlignThreshold(short goalXAlignThreshold) {
		this.goalXAlignThreshold = goalXAlignThreshold;
  }

  public short getGoalYCoordTarget() {
    return this.goalYCoordTarget;
  }

  public short getGoalYCoordThreshold() {
    return this.goalYCoordThreshold;
  }

  public short getGoalAlignTimeThreshold() {
    return this.goalAlignTimeThreshold;
  }

  public boolean getStopwatchStarted() {
    return this.stopwatchStarted;
  }

  public void setStopwatchStarted(boolean stopwatchStarted) {
    this.stopwatchStarted = stopwatchStarted;
  }

  public long getStartStopwatch() {
    return this.startStopwatch;
  }

  public void setStartStopwatch(long startStopwatch) {
    this.startStopwatch = startStopwatch;
  }

  public int getAlignStage() {
    return this.alignStage;
  }

  public void setAlignStage(int alignStage) {
		this.alignStage = alignStage;
	}
  //#endregion

  //#region Set default command
  @Override
  public void periodic() {
    setDefaultCommand(new VisionCommand());
  }
  //#endregion


  // #region Set left and right motors to previously calculated motor values (based on percentages)
  public void DriveTowardsBall() {
    // Perform necessary calculations to locate and drive towards the ball
    // if ball is on left side of camera view
    if (Robot.getVisionDataSubsystem.getBallXcenter() <= getHalfWidth()) {
      // Left motor pivot math
      setLeftMotorPercent(getPivotMotorPercent() * (Robot.getVisionDataSubsystem.getBallXcenter() / getHalfWidth()) );

      // Set right motor to full speed
      setRightMotorPercent(getMaxMotorPercent());
    } 
    // if ball is on right side of camera view
    else if (Robot.getVisionDataSubsystem.getBallXcenter() > getHalfWidth()) {
      // Set left motor to full speed
      setLeftMotorPercent(getMaxMotorPercent());
      
      // Right motor pivot math
      setRightMotorPercent(getPivotMotorPercent() * ((Robot.getVisionDataSubsystem.getBallXcenter() - getHalfWidth()) / getHalfWidth()) );
    }


    // Set left motor speed (by value) based on calculated pivot OR full speed
    Robot.driveSubsystem.setLeftMotorValue(getMaxMotorValue() * getLeftMotorPercent());

    // Set right motor speed (by value) based on calculated pivot OR full speed
    Robot.driveSubsystem.setRightMotorValue(getMaxMotorValue() * getRightMotorPercent());
  }
  //#endregion
  
  //#region Align robot with x-axis of high-goal in order to shoot
  public void AlignXCoordWithHighgoal() {
    // if goal is on left side of camera view
    if (Robot.getVisionDataSubsystem.getGoalXcenter() <= getHalfWidth()) {
      // Left motor pivot math
      setLeftMotorPercent( (-1) * (Robot.getVisionDataSubsystem.getGoalXcenter() / getHalfWidth()) );

      // Right motor pivot math
      setRightMotorPercent( Robot.getVisionDataSubsystem.getGoalXcenter() / getHalfWidth() );
    }
    // if goal is on right side of camera view
    else if (Robot.getVisionDataSubsystem.getGoalXcenter() > getHalfWidth()) {
      // Left motor pivot math
      setLeftMotorPercent( ((Robot.getVisionDataSubsystem.getGoalXcenter() - getHalfWidth()) / getHalfWidth()) );

      // Right motor pivot math
      setRightMotorPercent( (-1) * ((Robot.getVisionDataSubsystem.getGoalXcenter() - getHalfWidth())) / getHalfWidth() );
    }

    // Set left motor speed (by value) based on calculated pivot OR full speed
    Robot.driveSubsystem.setLeftMotorValue(getMaxMotorValue() * getLeftMotorPercent());

    // Set right motor speed (by value) based on calculated pivot OR full speed
    Robot.driveSubsystem.setRightMotorValue(getMaxMotorValue() * getRightMotorPercent());
  }
  //#endregion

  //#region Align robot with y-axis of high-goal in order to shoot
  public void AlignYCoordWithHighgoal() {
    // if goal is above target y coord
    if (Robot.getVisionDataSubsystem.getGoalYcenter() >= getGoalYCoordTarget()) {
      // Left motor pivot math
      setLeftMotorPercent( (-1) * ((Math.abs(Robot.getVisionDataSubsystem.getGoalYcenter() - getGoalYCoordTarget())) 
      / (getFullHeight() - getGoalYCoordTarget())) );

      // Right motor pivot math
      setRightMotorPercent( ((Math.abs(Robot.getVisionDataSubsystem.getGoalYcenter() - getGoalYCoordTarget())) 
      / (getFullHeight() - getGoalYCoordTarget())) );
    }
    // if goal is below target y coord
    else if (Robot.getVisionDataSubsystem.getGoalYcenter() < getGoalYCoordTarget()) {
      // Left motor pivot math
      setLeftMotorPercent( ((Math.abs(Robot.getVisionDataSubsystem.getGoalYcenter() - getGoalYCoordTarget())) 
      / (getFullHeight() - getGoalYCoordTarget())) );

      // Right motor pivot math
      setRightMotorPercent( (-1) * ((Math.abs(Robot.getVisionDataSubsystem.getGoalYcenter() - getGoalYCoordTarget())) 
      / (getFullHeight() - getGoalYCoordTarget())) );
    }

    // Set left motor speed (by value) based on calculated pivot OR full speed
    Robot.driveSubsystem.setLeftMotorValue(getMaxMotorValue() * getLeftMotorPercent());

    // Set right motor speed (by value) based on calculated pivot OR full speed
    Robot.driveSubsystem.setRightMotorValue(getMaxMotorValue() * getRightMotorPercent());
  }
  //#endregion

  //#region Check if high-goal is aligned to correct height
  public boolean CheckHighgoalYAlignment() {  
    // Robot is aligned well with high goal if absolute dist between goalXcoord and center of screen is below threshold
    if ( (Math.abs(Robot.getVisionDataSubsystem.getGoalYcenter() - getGoalYCoordTarget()) <= getGoalYCoordThreshold()) ) {
      if (!getStopwatchStarted()) {
        setStartStopwatch(System.currentTimeMillis());
        setStopwatchStarted(true);
        return false;
      } else {
        return (System.currentTimeMillis() - getStartStopwatch()) >= getGoalAlignTimeThreshold();
      }
    } else {
      // Reset the stopwatch
      setStopwatchStarted(false);
      return false;
    }
  }
  //#endregion

  //#region Check if robot is well-aligned with high-goal (by x-axis) to shoot
  public boolean CheckHighgoalXAlignment() {  
    // Robot is aligned well with high goal if absolute dist between goalXcoord and center of screen is below threshold
    if ( (Math.abs( Robot.getVisionDataSubsystem.getGoalXcenter() - getHalfWidth() ) <= getGoalXAlignThreshold()) ) {
      if (!getStopwatchStarted()) {
        setStartStopwatch(System.currentTimeMillis());
        setStopwatchStarted(true);
        return false;
      } else {
        return (System.currentTimeMillis() - getStartStopwatch()) >= getGoalAlignTimeThreshold();
      }
    } else {
      // Reset the stopwatch
      setStopwatchStarted(false);
      return false;
    }
  }
  //#endregion

  //#region Use alignment stages to align robot to x and y axis
  public void AlignToHighgoal() {
    if (getAlignStage() == 1) {
      // If robot is aligned with y-axis
      if (CheckHighgoalYAlignment()) {
        setAlignStage(2);
      } else {
        AlignYCoordWithHighgoal();
      }
    } else if (getAlignStage() == 2) {
      if (CheckHighgoalXAlignment()) {
        setAlignStage(3);
      } else {
        AlignXCoordWithHighgoal();
      }
    } else {
      setAlignStage(1);
    }
  }
  //#endregion
}