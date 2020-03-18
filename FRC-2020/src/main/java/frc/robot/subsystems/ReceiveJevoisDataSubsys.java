/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.hal.util.UncleanStatusException;
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
        try {
            Constants.jevois.enableTermination();
        } catch (UncleanStatusException use) {
            SmartDashboard.putString("Init Jevois", "UncleanStatusException: could not enable jevois termination");
        }
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
    public void ReadAndParseXYSize() {
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
            
            // Seperately set the X and Y coords from the parsed string array
            Robot.xCoord = Integer.parseInt(getParsedData()[0]);
            Robot.yCoord = Integer.parseInt(getParsedData()[1]);
            // Also seperately set the found rectangle's size from the parsed string array
            Robot.rectSize = Integer.parseInt(getParsedData()[2]);
        }
    }
    //#endregion    END OF FUNCTIONS SECTION===============================================================
}