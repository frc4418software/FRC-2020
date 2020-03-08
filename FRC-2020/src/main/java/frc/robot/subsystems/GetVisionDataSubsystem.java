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
  private static SerialPort jevois;
  private String receivedString;

  private int ballXcenter;
  private int ballYcenter;

  private int goalXcenter;
  private int goalYcenter;

  private int[] xy;
  private String delims = ",";
  private String[] parsedData;

  private String nopeString = "n";

  
  //#endregion

  //#region Initialize needed stuff
  public void Init() {
    try {
      jevois = new SerialPort(115200, SerialPort.Port.kUSB1);
    }
    catch (UncleanStatusException use) {
      System.out.println("UncleanStatusException: Did not find serial port on roboRIO");
    }
    catch (NullPointerException npe) {
      System.out.println("NullPointerException: I dont freakin know");
    }


    try {
      jevois.writeString("streamon");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  //#endregion

  //#region Getters and setters
  public int[] getXy() {
    return this.xy;
  }
  public void setXy(int[] xy) {
    this.xy = xy;
  }
  
  public String getDelims() {
    return this.delims;
  }

  public String[] getParsedData() {
    return this.parsedData;
  }

  public void setParsedData(String[] parsedData) {
    this.parsedData = parsedData;
  }
  
  public String getNopeString() {
    return this.nopeString;
  }
  
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
  
  //#region Get the X OR Y coords of the ball from the strings sent by the JeVois
  public void BallExtractXandY() {
    setReceivedString(jevois.readString());
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
    setReceivedString(jevois.readString());
    if (getReceivedString() != getNopeString()) {
      try {
        SmartDashboard.putString("JeVois received", getReceivedString());
        setParsedData(getReceivedString().split(getDelims()));
        setGoalXcenter(Integer.parseInt(getParsedData()[0]));
        setGoalYcenter(Integer.parseInt(getParsedData()[1]));
      }
      catch (StringIndexOutOfBoundsException siobe) {
        System.out.println("StringIndexOutOfBoundsException: Did not receive string from JeVois correctly");
      }
    }
  }
  //#endregion

  //#region Test serial connection from JeVois to roboRio
  public void TestSerialToJevois() {
    setReceivedString(jevois.readString());
    SmartDashboard.putString("RECEIVED", getReceivedString());
    SmartDashboard.putString("getting serial", "");
  }
  //#endregion

  //#region Cleanup needed stuff
  public void Cleanup() {
    /*
    try {
      jevois.disableTermination();
      jevois.reset();
    }
    catch (NullPointerException npe) {
      System.out.println("NullPointerException: Did not cleanup the JeVois termination correctly");
    }
    */
  }
  //#endregion 
}
