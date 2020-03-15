/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.hal.util.UncleanStatusException;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ReceiveJevoisDataSubsys extends SubsystemBase {
    //#region   BEGINNING OF VARIABLES SECTION==========================================================
    private SerialPort jevois;
    public SerialPort getJevois() {
        return this.jevois;
    }
    public void setJevois(SerialPort jevois) {
        this.jevois = jevois;
    }
    //================================================================
    private String jevoisString;
    public String getJevoisString() {
        return this.jevoisString;
    }
    public void setJevoisString(String jevoisString) {
        this.jevoisString = jevoisString;
    }
    //================================================================
    private String nopeString = "n";
    public String getNopeString() {
        return this.nopeString;
    }
    //================================================================
    private int xCoord;
    public int getXCoord() {
        return this.xCoord;
    }
    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }
    //================================================================
    private int yCoord;
    public int getYCoord() {
        return this.yCoord;
    }
    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }
    //================================================================
    private int rectSize;
    public int getRectSize() {
        return this.rectSize;
    }
    public void setRectSize(int rectSize) {
        this.rectSize = rectSize;
    }
    //================================================================
    private String delims = ",";
    public String getDelims() {
        return this.delims;
    }
    public void setDelims(String delims) {
		this.delims = delims;
	}
    //================================================================
    private String[] parsedData;
    public String[] getParsedData() {
        return this.parsedData;
    }
    public void setParsedData(String[] parsedData) {
        this.parsedData = parsedData;
    }
    //#endregion    END OF VARIABLES SECTION============================================================




    //#region   BEGINNING OF FUNCTIONS SECTION============================================================
    public void InitSerialPort() {
        try {
            // Create a new serial port for a USB connection from the JeVois
            jevois = new SerialPort(115200, SerialPort.Port.kUSB1);
            SmartDashboard.putString("JeVois Port", "Created new JeVois serialport");
          }
          catch (UncleanStatusException use) {
            SmartDashboard.putString("SerialPortError","UncleanStatusException: Make sure the serial port is being reset or cleaned without a proper delay!");
          }
          catch (NullPointerException npe) {
            SmartDashboard.putString("SerialPortError","NullPointerException: Could not find the JeVois on the serial port, is it plugged into the top USB port?");
        }

        jevois.enableTermination();
    }
    //================================================================
    // Sub-function
    public boolean IsReceivedStringValidData() {
        if ((!getJevoisString().contains(getNopeString())) && (!getJevoisString().isBlank())) {
            return true;
        } else {
            return false;
        }
    }
    //================================================================
    public void ReadAndParseXY() {
        // Get raw, received data from the JeVois
        setJevoisString(getJevois().readString());
        SmartDashboard.putString("Raw Jevois string: ", getJevoisString());    

        // If the string is NOT the nopeString
            // The "nope" string is the string to indicate no successful output found from the vision pipeline
        if (IsReceivedStringValidData()) {
            try {
                // Split the valid data (assumed that the data is x and y coords) into elements of a parsed string array
                setParsedData(getJevoisString().split(getDelims()));
            }
            catch (StringIndexOutOfBoundsException siobe) {
                SmartDashboard.putString("ParseXYError","StringIndexOutOfBoundsException: tried to set parsedData to split jevoisString");
            }
            
            try {
                // Seperately set the X and Y coords from the parsed string array
                setXCoord(Integer.parseInt(getParsedData()[0]));
                setYCoord(Integer.parseInt(getParsedData()[1]));

                // TODO change all jevois scripts to also send the rect size after a second comma (2nd comma after the y coord)
                // Also seperately set the found rectangle's size from the parsed string array
                setRectSize(Integer.parseInt(getParsedData()[3]));
            }
            catch (NumberFormatException nfe) {
                SmartDashboard.putString("ParseXYError", "NumberFormatException: tried to set XY coords to toInt-casted elements of parsedData");            
            }
            
            SmartDashboard.putString("Coords", "X: " + Integer.toString(getXCoord()) + 
                                               "   Y: " + Integer.toString(getYCoord()));
            SmartDashboard.putString("RectSize", Integer.toString(getRectSize()));
        // If the string IS the nopeString
        } else {
            SmartDashboard.putString("Coords", "No valid XY found");
        }
    }
    //#endregion    END OF FUNCTIONS SECTION===============================================================
}