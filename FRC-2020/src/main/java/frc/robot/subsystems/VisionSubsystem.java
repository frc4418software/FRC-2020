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