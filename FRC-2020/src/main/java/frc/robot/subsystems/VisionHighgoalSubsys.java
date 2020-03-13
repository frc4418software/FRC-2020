/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;


public class VisionHighgoalSubsys extends SubsystemBase {
    //#region   ====================BEGINNING OF SWEEP VARIABLES SECTION=======================================
    private int msTimeForFullSideRotation = 1000;           // CONFIG TODO Find correct time for this, is an est
    public int getMsTimeForFullSideRotation() {
        return this.msTimeForFullSideRotation;
    }
    public void getMsTimeForFullSideRotation(int msTimeForFullSideRotation) {
		this.msTimeForFullSideRotation = msTimeForFullSideRotation;
    }
    //=======================================================================
    private int msTimeForCenterSideRotation = 417;         // CONFIG TODO Find correct time for this, is an est
    public int getMsTimeForCenterSideRotation() {
        return this.msTimeForCenterSideRotation;
    }
    public void setMsTimeForCenterSideRotation(int msTimeForCenterSideRotation) {
		this.msTimeForCenterSideRotation = msTimeForCenterSideRotation;
	}
    //=======================================================================
    private double sweepLeftMotorMaxPercent = 1.0;              // CONFIG USE TO FLIP MOTOR DIR IF NEEDED
    public double getSweepLeftMotorMaxPercent() {
        return this.sweepLeftMotorMaxPercent;
    }
    public void setSweepLeftMotorMaxPercent(double sweepLeftMotorMaxPercent) {
		this.sweepLeftMotorMaxPercent = sweepLeftMotorMaxPercent;
	}
    //=======================================================================
    private double sweepRightMotorMaxPercent = -1.0;            // CONFIG USE TO FLIP MOTOR DIR IF NEEDED
    public double getSweepRightMotorMaxPercent() {
        return this.sweepRightMotorMaxPercent;
    }
    public void setSweepRightMotorMaxPercent(double sweepRightMotorMaxPercent) {
		this.sweepRightMotorMaxPercent = sweepRightMotorMaxPercent;
    }
    //=======================================================================
    private boolean isSweepFinished = false;      // CONFIG SET WHETHER SWEEP IS NEEDED FOR NOT, TRUE FOR NOT NEEDED
    public boolean getIsSweepFinished() {
        return this.isSweepFinished;
    }
    public void setIsSweepFinished(boolean isSweepFinished) {
		this.isSweepFinished = isSweepFinished;
    }
    //=======================================================================
    private boolean startSweepStopwatch = false;
    public boolean getStartSweepStopwatch() {
        return this.startSweepStopwatch;
    }
    public void setStartSweepStopwatch(boolean startSweepStopwatch) {
		this.startSweepStopwatch = startSweepStopwatch;
	}
    //=======================================================================
    private long sweepStopwatchTime = 0;
    public long getSweepStopwatchTime() {
        return this.sweepStopwatchTime;
    }
    public void setSweepStopwatchTime(long sweepStopwatchTime) {
		this.sweepStopwatchTime = sweepStopwatchTime;
	}
    //=======================================================================

    //#endregion ====================END OF SWEEP VARIABLES SECTION=======================================

    //#region   ====================BEGINNING OF CONFIRM VARIABLES SECTION=======================================
    //TODO Write needed variables for adjusting the perceived brightness/lighting of the JeVois
    //=======================================================================
    private boolean startConfirmTimeoutStopwatch = false;
    public boolean getStartConfirmTimeoutStopwatch() {
        return this.startConfirmTimeoutStopwatch;
    }
    public void setStartConfirmTimeoutStopwatch(boolean startConfirmTimeoutStopwatch) {
		this.startConfirmTimeoutStopwatch = startConfirmTimeoutStopwatch;
    }
    //=======================================================================
    private long confirmStopwatchTime = 0;
    public long getConfirmStopwatchTime() {
        return this.confirmStopwatchTime;
    }
    public void setConfirmStopwatchTime(long confirmStopwatchTime) {
		this.confirmStopwatchTime = confirmStopwatchTime;
	}
    //=======================================================================
    private long consistentHighgoalConfirmMsTime = 1300;   // CONFIG TIME OF CONSISTENT XY RECEIVE FOR CONFIRMED HIGHGOAL
    public long getConsistentHighgoalConfirmMsTime() {
        return this.consistentHighgoalConfirmMsTime;
    }
    public void setConsistentHighgoalConfirmMsTime(long consistentHighgoalConfirmMsTime) {
		this.consistentHighgoalConfirmMsTime = consistentHighgoalConfirmMsTime;
	}
    //=======================================================================
    private int backupConfirmedHighgoalXCoord;
    public int getBackupConfirmedHighgoalXCoord() {
        return this.backupConfirmedHighgoalXCoord;
    }
    public void setBackupConfirmedHighgoalXCoord(int backupConfirmedHighgoalXCoord) {
        this.backupConfirmedHighgoalXCoord = backupConfirmedHighgoalXCoord;
    }
    //=======================================================================
    private int backupConfirmedHighgoalYCoord;
    public int getBackupConfirmedHighgoalYCoord() {
        return this.backupConfirmedHighgoalYCoord;
    }
    public void setBackupConfirmedHighgoalYCoord(int backupConfirmedHighgoalYCoord) {
        this.backupConfirmedHighgoalYCoord = backupConfirmedHighgoalYCoord;
    }
    //=======================================================================
    private long confirmStopwatchMsTimeout = 2500;    // CONFIG MAX TIME TO LOOK FOR CONSISTENT STREAM OF XY, MUST BE LARGER THAN MS CONFIRM TIME
    public long getConfirmStopwatchMsTimeout() {
        return this.confirmStopwatchMsTimeout;
    }
    public void setConfirmStopwatchMsTimeout(long confirmStopwatchMsTimeout) {
		this.confirmStopwatchMsTimeout = confirmStopwatchMsTimeout;
	} 
    //=======================================================================
    private boolean consistentHighgoalFound = false;
    public boolean getConsistentHighgoalFound() {
        return this.consistentHighgoalFound;
    }
    public void setConsistentHighgoalFound(boolean consistentHighgoalFound) {
		this.consistentHighgoalFound = consistentHighgoalFound;
    }
    //=======================================================================
    private boolean stopConfirmCmd = false;
    public boolean getStopConfirmCmd() {
        return this.stopConfirmCmd;
    }
    public void setStopConfirmCmd(boolean stopConfirmCmd) {
		this.stopConfirmCmd = stopConfirmCmd;
	}
    //#endregion    ====================END OF CONFIRM VARIABLES SECTION=======================================

    //#region   ====================BEGINNING OF FACE VARIABLES SECTION=======================================
    private int highgoalXCoordThreshold = 15;   // CONFIG DISTANCE FROM WANTED X CENTER ALLOWED
    public int getHighgoalXCoordThreshold() {
        return this.highgoalXCoordThreshold;
    }
    public void setHighgoalXCoordThreshold(int highgoalXCoordThreshold) {
		this.highgoalXCoordThreshold = highgoalXCoordThreshold;
	}
    //#endregion    ====================END OF FACE VARIABLES SECTION=======================================



    //#region   ====================BEGINNING OF SWEEP FUNCTIONS SECTION=======================================
    // Sub-function
    public void StopwatchTurnAndStop(long msDelayTime, boolean turningLeft) {
        // If sweep stopwatch is not started
        if (getStartSweepStopwatch() != true) {
            setStartSweepStopwatch(true);
        // If sweep stopwatch is started
        } else {
            if (turningLeft) {
                // Set motors to turn to face its left
                Robot.driveSubsystem.setLeftMotorValue(-50 * getSweepLeftMotorMaxPercent());
                Robot.driveSubsystem.setRightMotorValue(50 * getSweepRightMotorMaxPercent());
            } else {
                // Set motors to turn to face its right
                Robot.driveSubsystem.setLeftMotorValue(50 * getSweepLeftMotorMaxPercent());
                Robot.driveSubsystem.setRightMotorValue(-50 * getSweepRightMotorMaxPercent());
            }
            setSweepStopwatchTime(System.currentTimeMillis());
        }

        // If sweep duration is fulfilled
        if (getSweepStopwatchTime() >= msDelayTime) {
            // TODO figure out how to properly stop the robot's drive motors
            Robot.driveSubsystem.setLeftMotorValue(0);
            Robot.driveSubsystem.setRightMotorValue(0);

            // Stop the sweep stopwatch
            setStartSweepStopwatch(false);

            // Confirm that the sweep has fulfilled its delay time
            setIsSweepFinished(true);
        }
    }
    public void SurveyTurnForHighgoal(int surveyMode) {
        switch (surveyMode) {
            case 1:
                // Surveying mode for when the robot is on the leftmost position
                StopwatchTurnAndStop(getMsTimeForFullSideRotation(), false);
                break;
            case 2:
                // Surveying mode for when the robot is on the centermost position
                StopwatchTurnAndStop(getMsTimeForCenterSideRotation(), false);
                break;
            case 3:
                // Surveying mode for when the robot is on the rightmost position
                StopwatchTurnAndStop(getMsTimeForFullSideRotation(), true);
                break;
        }
    }
    //#endregion    ====================END OF SWEEP FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF CONFIRM FUNCTIONS SECTION=======================================
    // Sub-function
    public void StopConfirmCmdIfConsistentHighgoalIsFound() {
        // If there is a constant receiving of highgoal XY coords greater than the CONFIG confirm time
        if (getConfirmStopwatchTime() >= getConsistentHighgoalConfirmMsTime()) {
            // Read the latest XY and parse it into usable integer coordinates
            Robot.receiveJevoisDataSubsys.ReadAndParseXY();

            // Set the backup highgoal coord to the parsed, found, and usable integer coordinates
            setBackupConfirmedHighgoalXCoord(Robot.receiveJevoisDataSubsys.getXCoord());
            setBackupConfirmedHighgoalYCoord(Robot.receiveJevoisDataSubsys.getYCoord());

            // Reset the stopwatch for timing a consistently found highgoal
            setStartConfirmTimeoutStopwatch(false);

            // Save that we have found a highgoal to follow
            setConsistentHighgoalFound(true);

            // Stop the robot from trying to confirm a consistently-time highgoal
            setStopConfirmCmd(true);
        }
    }
    //======================================================================================================
    // Sub-function
    public void ResetTimeIfDataIsNotValid() {
        // Update amount of time robot has received constant XY highgoal that is not the "nopeString" or blank
        if (Robot.receiveJevoisDataSubsys.IsReceivedStringValidData()) {
            setConfirmStopwatchTime(System.currentTimeMillis());
        } else {
            setConfirmStopwatchTime(0);
        }
    }
    //======================================================================================================
    public void ConfirmHighgoalXYWithTimeout() {
        // If the timeout stopwatch for confirming a highgoal is NOT started
        if (!getStartConfirmTimeoutStopwatch()) {
            setStartConfirmTimeoutStopwatch(true);
        // If the timeout stopwatch for confirming a highgoal is started
        } else {
            // If the robot has NOT been trying to confirm a consistent highgoal for the max time allowed
            if (getConfirmStopwatchTime() >= getConfirmStopwatchMsTimeout()) {
                // Reset the confirm stopwatch is the received XY data is the "nopeString" or is blank
                    // Functionn also keeps updating the confirmStopwatch while detecting highgoal XY coords
                ResetTimeIfDataIsNotValid();

                // Stop the robot from trying to confirm a highgoal if a constant stream of XY highgoal coords was found
                StopConfirmCmdIfConsistentHighgoalIsFound();

            // If the robot has been trying to confirm a consistent highgoal for the max time allowed
            } else {
                // Reset the stopwatch for timing a consistently found highgoal
                setStartConfirmTimeoutStopwatch(false);

                // Save that we have NOT found a highgoal to follow
                setConsistentHighgoalFound(false);
                
                // Stop the robot from trying to confirm a consistently-time highgoal
                setStopConfirmCmd(true);
            }
        }  
    }
    //#endregion    ====================END OF CONFIRM FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF FACE FUNCTIONS SECTION=======================================
    public boolean IsHighgoalXCoordWithinThreshold() {
        // TODO Finish check for highgoal X coord within threshold
        return true;
    }
    //#endregion    ====================END OF FACE FUNCTIONS SECTION=======================================
}