/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.hal.util.UncleanStatusException;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class GetVisionDataSubsystem extends SubsystemBase {

  //#region Init variables
  private float timeoutTime = 60.0f;
  private SerialPort jevois;
  private String receivedString;

  private int ballXcenter;
  private int ballYcenter;

  private int goalXcenter;
  private int goalYcenter;
  //#endregion

  //#region Initialize needed stuff
  public void Init() {
    try {
      jevois = new SerialPort(115200, SerialPort.Port.kOnboard);
      jevois.enableTermination();
      jevois.setTimeout(timeoutTime);
    }
    catch (UncleanStatusException use) {
      System.out.println("UncleanStatusException: Did not find serial port on roboRIO");
    }
    catch (NullPointerException npe) {
      System.out.println("NullPointerException: I dont freakin know");
    }
  }
  //#endregion

  //#region Getters and setters
  public String getReceivedString() {
    return this.receivedString;
  }
  public void setReceivedString(String receivedString) {
    this.receivedString = receivedString;
  }
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

  public int getGoalXcenter() {
    return this.goalXcenter;
  }

  public void setGoalXcenter(int goalXcenter) {
    this.goalXcenter = goalXcenter;
  }
  public int getGoalYcenter() {
    return this.goalYcenter;
  }

  public void setGoalYcenter(int goalYcenter) {
    this.goalYcenter = goalYcenter;
  }
  //#endregion

  //#region Serial sending and receiving methods
  public void WriteString(String write) {
    jevois.writeString(write);
  }

  public void ReadString() {
    if (jevois.readString() != "") {
      setReceivedString(jevois.readString());
      SmartDashboard.putString("JeVois XY: ", getReceivedString());
    }
  }

  public void ReadStringLimit(int limit) {
    jevois.readString(limit);
  }
  //#endregion
  
  //#region Get the X OR Y coords of the ball from the strings sent by the JeVois
  public void BallExtractXandY() {
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
      catch (StringIndexOutOfBoundsException siobe) {
        System.out.println("StringINdexOutOfBoundsException: Did not receive string from JeVois correctly");
      }
    }
  }
  //#endregion

  //#region Get the X OR Y coords of the high-goal from the strings sent by the JeVois
  public void GoalExtractXandY() {
    ReadString();
    if (getReceivedString() != "nah") {

      try {
        if (getReceivedString().charAt(0) == 'x') {
          setGoalXcenter(
            Integer.parseInt(getReceivedString().substring(1))
                                                      );  
        }
  
        else if (getReceivedString().charAt(0) == 'y') {
          setGoalYcenter(
            Integer.parseInt(getReceivedString().substring(1))
                                                      );
        }
      }
      catch (StringIndexOutOfBoundsException siobe) {
        System.out.println("StringIndexOutOfBoundsException: Did not receive string from JeVois correctly");
      }
    }
  }
  //#endregion

  //#region Test serial connection from JeVois to roboRio
  public void TestSerialToJevois() {
    try {
      ReadString();
      System.out.println(getReceivedString());
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
      jevois.reset();
    }
    catch (NullPointerException npe) {
      System.out.println("NullPointerException: Did not cleanup the JeVois termination correctly");
    }
  }
  //#endregion 
}
