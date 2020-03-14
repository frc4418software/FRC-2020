// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Robot;


// public class VisionSubsystem extends SubsystemBase {
//   //#region Variables
//   final private double maxMotorPercent = 1.0;   // pretty plz dont change this

//   private int halfWidth = 320/2;
//   private int fullHeight = 240;

//   private double pivotMotorPercent = 0.80;        // CONFIG

//   private double leftMotorPercent;
//   private double rightMotorPercent;

//   private double maxMotorValue = 50.0;            // CONFIG

//   private boolean lockedOntoBall = false;
//   private int trackingMode = 0;                   // CONFIG
  
//   private short goalXAlignThreshold = 15;         // CONFIG
//   private short ballConfirmTimeThreshold = 1500;  // CONFIG

//   private short goalYCoordTarget = 200;           // CONFIG
//   private short goalYCoordThreshold = 15;         // CONFIG
  
//   private short goalAlignXTimeThreshold = 2000;   // CONFIG
//   private short goalAlignYTimeThreshold = 2000;   // CONFIG

//   private boolean stopwatchStarted = false;
//   private long startStopwatch;

//   private int alignStage = 1;
//   //#endregion

//   //#region Getters and setters
//   public int getHalfWidth() {
//     return this.halfWidth;
//   }

//   public int getFullHeight() {
//     return this.fullHeight;
//   }

//   public double getPivotMotorPercent() {
//     return this.pivotMotorPercent;
//   }

//   public double getLeftMotorPercent() {
//     return this.leftMotorPercent;
//   }
//   public void setLeftMotorPercent(double leftMotorPercent) {
//     this.leftMotorPercent = leftMotorPercent;
//   }

//   public double getRightMotorPercent() {
//     return this.rightMotorPercent;
//   }
//   public void setRightMotorPercent(double rightMotorPercent) {
//     this.rightMotorPercent = rightMotorPercent;
//   }

//   public double getMaxMotorPercent() {
// 		return this.maxMotorPercent;
//   }
  
//   public double getMaxMotorValue() {
//     return this.maxMotorValue;
//   }
//   public void setMaxMotorValue(double maxMotorValue) {
// 		this.maxMotorValue = maxMotorValue;
//   }
  
//   public boolean getLockedOntoBall() {
//     return this.lockedOntoBall;
//   }

//   public void setLockedOntoBall(boolean lockedOntoBall) {
// 		this.lockedOntoBall = lockedOntoBall;
// 	}

//   public int getTrackingMode() {
//     return this.trackingMode;
//   }

//   public void setTrackingMode(int trackingMode) {
// 		this.trackingMode = trackingMode;
//   }

//   public short getGoalXAlignThreshold() {
//     return this.goalXAlignThreshold;
//   }

//   public void setGoalXAlignThreshold(short goalXAlignThreshold) {
// 		this.goalXAlignThreshold = goalXAlignThreshold;
//   }

//   public short getBallConfirmTimeThreshold() {
//     return this.ballConfirmTimeThreshold;
//   }

//   public short getGoalYCoordTarget() {
//     return this.goalYCoordTarget;
//   }

//   public short getGoalYCoordThreshold() {
//     return this.goalYCoordThreshold;
//   }

//   public short getGoalAlignXTimeThreshold() {
//     return this.goalAlignXTimeThreshold;
//   }

//   public void setGoalAlignXTimeThreshold(short goalAlignTimeXThreshold) {
// 		this.goalAlignXTimeThreshold = goalAlignTimeXThreshold;
//   }
  
//   public short getGoalAlignYTimeThreshold() {
//     return this.goalAlignYTimeThreshold;
//   }

//   public void setGoalAlignYTimeThreshold(short goalAlignTimeYThreshold) {
// 		this.goalAlignYTimeThreshold = goalAlignTimeYThreshold;
// 	}

//   public boolean getStopwatchStarted() {
//     return this.stopwatchStarted;
//   }

//   public void setStopwatchStarted(boolean stopwatchStarted) {
//     this.stopwatchStarted = stopwatchStarted;
//   }

//   public long getStartStopwatch() {
//     return this.startStopwatch;
//   }

//   public void setStartStopwatch(long startStopwatch) {
//     this.startStopwatch = startStopwatch;
//   }

//   public int getAlignStage() {
//     return this.alignStage;
//   }

//   public void setAlignStage(int alignStage) {
// 		this.alignStage = alignStage;
// 	}
//   //#endregion

//   // #region Set left and right motors to previously calculated motor values (based on percentages)
//   public void DriveTowardsBall() {
//     // Perform necessary calculations to locate and drive towards the ball
//     // if ball is on left side of camera view
//     if (Robot.receiveJevoisDataSubsys.getXCoord() <= getHalfWidth()) {
//       // Left motor pivot math
//       setLeftMotorPercent(getPivotMotorPercent() * (1 - (Robot.receiveJevoisDataSubsys.getXCoord() / getHalfWidth())) );
    
//       // Set right motor to full speed
//       setRightMotorPercent(getMaxMotorPercent());
//     } 
//     // if ball is on right side of camera view
//     else if (Robot.receiveJevoisDataSubsys.getXCoord() > getHalfWidth()) {
//       // Set left motor to full speed
//       setLeftMotorPercent(getMaxMotorPercent());
      
//       // Right motor pivot math
//       setRightMotorPercent(getPivotMotorPercent() * (1 - ((Robot.receiveJevoisDataSubsys.getXCoord() - getHalfWidth()) / getHalfWidth())) );
//     }


//     // Set left motor speed (by value) based on calculated pivot OR full speed
//     Robot.driveSubsystem.setLeftMotorValue(getMaxMotorValue() * getLeftMotorPercent());

//     // Set right motor speed (by value) based on calculated pivot OR full speed
//     Robot.driveSubsystem.setRightMotorValue(getMaxMotorValue() * getRightMotorPercent());
//   }
//   //#endregion
  
//   //#region Align robot with x-axis of high-goal in order to shoot
//   public void AlignXCoordWithHighgoal() {
//     // if goal is on left side of camera view
//     if (Robot.receiveJevoisDataSubsys.getXCoord() <= getHalfWidth()) {
//       // Left motor pivot math
//       setLeftMotorPercent( (-1) * (Robot.receiveJevoisDataSubsys.getXCoord() / getHalfWidth()) );

//       // Right motor pivot math
//       setRightMotorPercent( Robot.receiveJevoisDataSubsys.getXCoord() / getHalfWidth() );
//     }
//     // if goal is on right side of camera view
//     else if (Robot.receiveJevoisDataSubsys.getXCoord() > getHalfWidth()) {
//       // Left motor pivot math
//       setLeftMotorPercent( ((Robot.receiveJevoisDataSubsys.getXCoord() - getHalfWidth()) / getHalfWidth()) );

//       // Right motor pivot math
//       setRightMotorPercent( (-1) * ((Robot.receiveJevoisDataSubsys.getXCoord() - getHalfWidth())) / getHalfWidth() );
//     }

//     // Set left motor speed (by value) based on calculated pivot OR full speed
//     Robot.driveSubsystem.setLeftMotorValue(getMaxMotorValue() * getLeftMotorPercent());

//     // Set right motor speed (by value) based on calculated pivot OR full speed
//     Robot.driveSubsystem.setRightMotorValue(getMaxMotorValue() * getRightMotorPercent());
//   }
//   //#endregion

//   //#region Align robot with y-axis of high-goal in order to shoot
//   public void AlignYCoordWithHighgoal() {
//     // if goal is above target y coord
//     if (Robot.receiveJevoisDataSubsys.getYCoord() >= getGoalYCoordTarget()) {
//       // Left motor pivot math
//       setLeftMotorPercent( (-1) * ((Math.abs(Robot.receiveJevoisDataSubsys.getYCoord() - getGoalYCoordTarget())) 
//       / (getFullHeight() - getGoalYCoordTarget())) );

//       // Right motor pivot math
//       setRightMotorPercent( ((Math.abs(Robot.receiveJevoisDataSubsys.getYCoord() - getGoalYCoordTarget())) 
//       / (getFullHeight() - getGoalYCoordTarget())) );
//     }
//     // if goal is below target y coord
//     else if (Robot.receiveJevoisDataSubsys.getYCoord() < getGoalYCoordTarget()) {
//       // Left motor pivot math
//       setLeftMotorPercent( ((Math.abs(Robot.receiveJevoisDataSubsys.getYCoord() - getGoalYCoordTarget())) 
//       / (getFullHeight() - getGoalYCoordTarget())) );

//       // Right motor pivot math
//       setRightMotorPercent( (-1) * ((Math.abs(Robot.receiveJevoisDataSubsys.getYCoord() - getGoalYCoordTarget())) 
//       / (getFullHeight() - getGoalYCoordTarget())) );
//     }

//     // Set left motor speed (by value) based on calculated pivot OR full speed
//     Robot.driveSubsystem.setLeftMotorValue(getMaxMotorValue() * getLeftMotorPercent());

//     // Set right motor speed (by value) based on calculated pivot OR full speed
//     Robot.driveSubsystem.setRightMotorValue(getMaxMotorValue() * getRightMotorPercent());
//   }
//   //#endregion
// }