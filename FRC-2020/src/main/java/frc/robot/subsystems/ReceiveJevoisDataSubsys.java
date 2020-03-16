/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;


public class ReceiveJevoisDataSubsys extends SubsystemBase {
    //#region   BEGINNING OF VARIABLES SECTION==========================================================
    private String[] parsedData;
    public String[] getParsedData() {
        return this.parsedData;
    }
    public void setParsedData(String[] parsedData) {
        this.parsedData = parsedData;
    }
    //#endregion    END OF VARIABLES SECTION============================================================


    

    //#region   BEGINNING OF FUNCTIONS SECTION============================================================
    public void InitJevois() {
        Constants.jevois.enableTermination();
    }
    //================================================================
    // Sub-function
    public boolean IsReceivedStringValidData() {
        if (    (! Robot.jevoisString.contains(Constants.nopeString)) && (! Robot.jevoisString.isBlank())   ) {
            return true;
        } else {
            return false;
        }
    }
    //================================================================
    public void ReadAndParseXY() {
        // Get raw, received data from the JeVois
        Robot.jevoisString = (Constants.jevois.readString());
        SmartDashboard.putString("Raw Jevois string: ", Robot.jevoisString);    

        // If the string is NOT the nopeString
            // The "nope" string is the string to indicate no successful output found from the vision pipeline
        if (IsReceivedStringValidData()) {
            try {
                // Split the valid data (assumed that the data is x and y coords) into elements of a parsed string array
                setParsedData(Robot.jevoisString.split(Constants.delims));
            }
            catch (StringIndexOutOfBoundsException siobe) {
                SmartDashboard.putString("ParseXYError","StringIndexOutOfBoundsException: tried to set parsedData to split jevoisString");
            }
            
            try {
                // Seperately set the X and Y coords from the parsed string array
                Robot.xCoord = Integer.parseInt(getParsedData()[0]);
                Robot.yCoord = Integer.parseInt(getParsedData()[1]);

                // TODO CHANGE all jevois scripts to also send the rect size after a second comma (2nd comma after the y coord)
                // Also seperately set the found rectangle's size from the parsed string array
                Robot.rectSize = Integer.parseInt(getParsedData()[3]);
            }
            catch (NumberFormatException nfe) {
                SmartDashboard.putString("ParseXYError", "NumberFormatException: tried to set XY coords to toInt-casted elements of parsedData");            
            }
            
            SmartDashboard.putString("Coords", "X: " + Integer.toString(Robot.xCoord) + 
                                               "   Y: " + Integer.toString(Robot.yCoord));
            SmartDashboard.putString("RectSize", Integer.toString(Robot.rectSize));
        // If the string IS the nopeString
        } else {
            SmartDashboard.putString("Coords", "No valid XY found");
        }
    }
    //#endregion    END OF FUNCTIONS SECTION===============================================================
}