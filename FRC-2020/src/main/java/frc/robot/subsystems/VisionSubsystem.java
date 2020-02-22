/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.VisionCommand;


public class VisionSubsystem extends SubsystemBase {
  private float timeoutTime = 60.0f;
  private SerialPort jevois;

  private String receivedString;
  private int ballXcenter;
  private int ballYcenter;

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

  //#region Getters and setters

  public int getBallXcenter() {
    return this.ballXcenter;
  }
  public void setBallXcenter(int xcenter) {
    this.ballXcenter = xcenter;
  }



  public int getBallYcenter() {
    return this.ballYcenter;
  }
  public void setBallYcenter(int ycenter) {
    this.ballYcenter = ycenter;
  }



  public String getReceivedString() {
    return this.receivedString;
  }
  public void setReceivedString(String receivedString) {
    this.receivedString = receivedString;
  }

  //#endregion

  //#region MORE getters and setters

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

  //#endregion

  //#region Set default command
  @Override
  public void periodic() {
    setDefaultCommand(new VisionCommand());
  }
  //#endregion

  //#region Initialize needed stuff
  public void Init() {
    jevois = new SerialPort(115200, SerialPort.Port.kUSB1);
    jevois.enableTermination();
    jevois.setTimeout(timeoutTime);
  }
  //#endregion

  //#region Serial sending and receiving methods
  public void WriteString(String write) {
    jevois.writeString(write);
  }

  public void ReadString() {
    setReceivedString(jevois.readString());
    SmartDashboard.putString("JeVois XY: ", getReceivedString());
  }

  public void ReadStringLimit(int limit) {
    jevois.readString(limit);
  }
  //#endregion
  
  //#region Get the X OR Y coords of the ball from the strings sent by the JeVois
  public void ExtractXandY() {
    ReadString();
    if (getReceivedString() != "nah") {

      try {
        if (getReceivedString().charAt(0) == 'x') {
          setBallXcenter(
            Integer.parseInt(getReceivedString().substring(1))
                                                      );  
        }
  
        else if (getReceivedString().charAt(0) == 'y') {
          setBallYcenter(
            Integer.parseInt(getReceivedString().substring(1))
                                                      );
        }
      }
      catch (NumberFormatException nfe) {
        System.out.println("NumberFormatException: Did not receive string from JeVois correctly");
      }
      catch (NullPointerException npe) {
        System.out.println("NullPointerException: Did not receive string from JeVois correctly");
      }

    }
  }
  //#endregion

  //#region Test serial connection from JeVois to roboRio
  public void TestSerialToJevois() {
    try {
      Robot.visionSubsystem.ReadString();
      System.out.println(Robot.visionSubsystem.getReceivedString());
    }
    catch(NullPointerException npe) {
      System.out.println("NullPointerException: Did not read string from JeVois correctly");
    }
  }
  //#endregion




  // #region Calculate ball coordinate difference from bottom center of camera view
  public void CalculateCoordError() {
    // calculate how far x coord of ball is from center of camera view
    setXerror(getHalfWidth() - getBallXcenter());

    // calculate how far y coord of ball is from bottom of camera view
    setYerror(getFullHeight() - getBallYcenter());
  }
  //#endregion

  // #region Calculate the correct motor percentages for turning and driving towards ball
  public void CalculateMotorPivot() {
    // if ball is on left side of camera view
    if (getBallXcenter() <= getHalfWidth()) {
      // Left motor pivot math
      setLeftRelativePercent(( getBallXcenter() ) / getHalfWidth());
      setLeftMotorPercent(getPivotMotorPercent() * getLeftRelativePercent());

      // Set right motor to full speed
      setRightMotorPercent(getMaxMotorPercent());
    } 
    // if ball is on right side of camera view
    else if (getBallXcenter() > getHalfWidth()) {
      // Set left motor to full speed
      setLeftMotorPercent(getMaxMotorPercent());
      
      // Right motor pivot math
      setRightRelativePercent(( getBallXcenter() - getHalfWidth()) / getHalfWidth());
      setRightMotorPercent(getPivotMotorPercent() * getRightRelativePercent());
    }
  }
  //#endregion

  // #region Set left and right motors to previously calculated motor values (based on percentages)
  public void DriveTowardsBall() {
    // Set left motor speed (by value) based on calculated pivot OR full speed
    setLeftMotorValue(getMaxMotorValue() * getLeftMotorPercent());

    // Set right motor speed (by value) based on calculated pivot OR full speed
    setRightMotorValue(getMaxMotorValue() * getRightMotorPercent());
  }
  //#endregion
  


  

  //#region Cleanup needed stuff
  public void Cleanup() {
    try {
      jevois.disableTermination();
    }
    catch (NullPointerException npe) {
      System.out.println("NullPointerException: Did not cleanup the JeVois termination correctly");
    }
    jevois.reset();
  }
  //#endregion 
}