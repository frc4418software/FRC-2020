/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.HSVDetectBallCommand;;


public class HSVDetectBallSubsystem extends SubsystemBase {
  private char terminateChar = 's';
  private float timeoutTime = 60.0f;
  private SerialPort jevois;

  private String receivedString;
  private int ballXcenter;
  private int ballYcenter;

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



  //#region Set default command
  @Override
  public void periodic() {
    setDefaultCommand(new HSVDetectBallCommand());
  }
  //#endregion

  //#region Initialize needed stuff
  public void Init() {
    jevois = new SerialPort(921600, SerialPort.Port.kUSB);
    jevois.enableTermination(terminateChar);
    jevois.setTimeout(timeoutTime);
  }
  //#endregion

  //#region Sending and receiving strings to and from the jevois by serial
  public void WriteString(String write) {
    jevois.writeString(write);
  }

  public void ReadString() {
    setReceivedString(jevois.readString());
  }

  public void ReadStringLimit(int limit) {
    jevois.readString(limit);
  }
  //#endregion
  


  //#region Cleanup needed stuff
  public void Cleanup() {
    jevois.disableTermination();
    jevois.reset();
  }
  //#endregion 
}