/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;


public class VisionHighgoalSubsys extends SubsystemBase {
    //#region   |||||||||||||||||||||||BEGINNING OF MULTI-COMPONENT VARIABLES||||||||||||||||||||||||||||||||||
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
    private boolean consistentHighgoalFound = false;
    public boolean getConsistentHighgoalFound() {
        return this.consistentHighgoalFound;
    }
    public void setConsistentHighgoalFound(boolean consistentHighgoalFound) {
		this.consistentHighgoalFound = consistentHighgoalFound;
    }
    //=======================================================================
    private boolean isUsingBackupCoordsAsDefault = false;      // CONFIG SET WHETHER ROBOT FACES HIGHGOAL USING BACKUP COORDS OR NOT
    public boolean getIsUsingBackupCoordsAsDefault() {
        return this.isUsingBackupCoordsAsDefault;
    }
    public void setIsUsingBackupCoordsAsDefault(boolean isUsingBackupCoordsAsDefault) {
		this.isUsingBackupCoordsAsDefault = isUsingBackupCoordsAsDefault;
    }
    //=======================================================================
    private int jevoisCamWidth = 320;       // CONFIG SET THE PIXEL WIDTH OF THE JEVOIS VIEW
    public int getJevoisCamWidth() {
        return this.jevoisCamWidth;
    }
    public void setJevoisCamWidth(int jevoisCamWidth) {
		this.jevoisCamWidth = jevoisCamWidth;
    }
    //=======================================================================
    private int jevoisCamHeight = 240;      // CONFIG SET THE PIXEL HEIGHT OF THE JEVOIS VIEW
    public int getJevoisCamHeight() {
        return this.jevoisCamHeight;
    }
    public void setJevoisCamHeight(int jevoisCamHeight) {
		this.jevoisCamHeight = jevoisCamHeight;
    }
    //=======================================================================
    private double maxBothMotorOutputPercent = 0.75;      // CONFIG SET THE MAXIMUM PERCENT SPEED OF BOTH MOTORS
    public double getMaxBothMotorOutputPercent() {
        return this.maxBothMotorOutputPercent;
    }
    public void setMaxBothMotorOutputPercent(double maxBothMotorOutputPercent) {
		this.maxBothMotorOutputPercent = maxBothMotorOutputPercent;
    }
    //=======================================================================
    private int leftMotorDirFlip = 1;           // CONFIG USE TO FLIP THE DIRECTION OF THE LEFT MOTOR
    public int getLeftMotorDirFlip() {
        return this.leftMotorDirFlip;
    }
    public void setLeftMotorDirFlip(int leftMotorDirFlip) {
		this.leftMotorDirFlip = leftMotorDirFlip;
    }
    //=======================================================================
    private int rightMotorDirFlip = -1;         // CONFIG USE TO FLIP THE DIRECTION OF THE RIGHT MOTOR
    public int getRightMotorDirFlip() {
        return this.rightMotorDirFlip;
    }
    public void setRightMotorDirFlip(int rightMotorDirFlip) {
		this.rightMotorDirFlip = rightMotorDirFlip;
	}
    //#endregion
    
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
    private long sweepStopwatchStartTime = 0;
    public long getSweepStopwatchStartTime() {
        return this.sweepStopwatchStartTime;
    }
    public void setSweepStopwatchStartTime(long sweepStopwatchStartTime) {
		this.sweepStopwatchStartTime = sweepStopwatchStartTime;
	}
    //#endregion ====================END OF SWEEP VARIABLES SECTION=======================================

    //#region   ====================BEGINNING OF CONFIRM VARIABLES SECTION=======================================
    //TODO Write needed variables for adjusting the perceived brightness/lighting of the JeVois
    private long confirmStopwatchTime = 0;
    public long getConfirmStopwatchTime() {
        return this.confirmStopwatchTime;
    }
    public void setConfirmStopwatchTime(long confirmStopwatchTime) {
		this.confirmStopwatchTime = confirmStopwatchTime;
    }
    //=======================================================================
    private long confirmStopwatchResetTime = 0;
    public long getConfirmStopwatchResetTime() {
        return this.confirmStopwatchResetTime;
    }
    public void setConfirmStopwatchResetTime(long confirmStopwatchResetTime) {
		this.confirmStopwatchResetTime = confirmStopwatchResetTime;
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
    private long confirmStopwatchMsTimeout = 2500;    // CONFIG MAX TIME TO LOOK FOR CONSISTENT STREAM OF XY, MUST BE LARGER THAN MS CONFIRM TIME
    public long getConfirmStopwatchMsTimeout() {
        return this.confirmStopwatchMsTimeout;
    }
    public void setConfirmStopwatchMsTimeout(long confirmStopwatchMsTimeout) {
		this.confirmStopwatchMsTimeout = confirmStopwatchMsTimeout;
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
    private int faceXCoordThreshold = 12;   // CONFIG DISTANCE FROM WANTED X CENTER ALLOWED
    public int getFaceXCoordThreshold() {
        return this.faceXCoordThreshold;
    }
    public void setFaceXCoordThreshold(int faceXCoordThreshold) {
		this.faceXCoordThreshold = faceXCoordThreshold;
    }
    //=======================================================================
    private long faceStopwatchTime = 0;
    public long getFaceStopwatchTime() {
        return this.faceStopwatchTime;
    }
    public void setFaceStopwatchTime(long faceStopwatchTime) {
		this.faceStopwatchTime = faceStopwatchTime;
	}
    //=======================================================================
    private long faceMsTimeThreshold = 700;        // CONFIG TIME NEEDED TO CONFIRM THAT ROBOT IS CONSISTENTLY FACING THE HIGHGOAL
    public long getFaceMsTimeThreshold() {
        return this.faceMsTimeThreshold;
    }
    public void setFaceMsTimeThreshold(long faceMsTimeThreshold) {
		this.faceMsTimeThreshold = faceMsTimeThreshold;
    }
    //=======================================================================
    private long faceStopwatchResetTime = 0;
    public long getFaceStopwatchResetTime() {
        return this.faceStopwatchResetTime;
    }
    public void setFaceStopwatchResetTime(long faceStopwatchResetTime) {
        this.faceStopwatchResetTime = faceStopwatchResetTime;
    }
    //=======================================================================
    private boolean isFaceHighgoalComplete = false;
    public boolean getIsFaceHighgoalComplete() {
        return this.isFaceHighgoalComplete;
    }
    public void setIsFaceHighgoalComplete(boolean isFaceHighgoalComplete) {
		this.isFaceHighgoalComplete = isFaceHighgoalComplete;
	}
    //#endregion    ====================END OF FACE VARIABLES SECTION=======================================

    //#region   ====================BEGINNING OF DRIVE VARIABLES SECTION======================================
                // TODO FIND RIGHT BOX SIZE TO USE
    private int highgoalRectAreaThreshold = 150;         // CONFIG SET SIZE OF HIGHGOAL THRESHOLD WHEN ROBOT GETS CLOSE ENOUGH
    public int getHighgoalRectAreaThreshold() {
        return this.highgoalRectAreaThreshold;
    }
    public void setHighgoalRectAreaThreshold(int highgoalRectAreaThreshold) {
		this.highgoalRectAreaThreshold = highgoalRectAreaThreshold;
	}
    //=======================================================================
    private int highCenterPosDistFromHighgoal;
    public int getHighCenterPosDistFromHighgoal() {
        return this.highCenterPosDistFromHighgoal;
    }
    public void setHighCenterPosDistFromHighgoal(int highCenterPosDistFromHighgoal) {
        this.highCenterPosDistFromHighgoal = highCenterPosDistFromHighgoal;
    }
    //=======================================================================
    private int trueHighgoalDist;
    public int getTrueHighgoalDist() {
        return this.trueHighgoalDist;
    }
    public void setTrueHighgoalDist(int trueHighgoalDist) {
        this.trueHighgoalDist = trueHighgoalDist;
    }
    //=======================================================================
            // TODO FIND RIGHT DIST TO USE
    private int highgoalDistThreshold = 20;     // CONFIG SET ULTRASONIC DIST THRESHOLD FROM HIGHGOAL WHEN ROBOT GETS CLOSE ENOUGH 
    public int getHighgoalDistThreshold() {
        return this.highgoalDistThreshold;
    }
    public void setHighgoalDistThreshold(int highgoalDistThreshold) {
		this.highgoalDistThreshold = highgoalDistThreshold;
    }
    //=======================================================================
    private boolean hasReachedHighgoal = false;
    public boolean getHasReachedHighgoal() {
        return this.hasReachedHighgoal;
    }
    public void setHasReachedHighgoal(boolean hasReachedHighgoal) {
		this.hasReachedHighgoal = hasReachedHighgoal;
	}
    //#endregion






    //#region   ||||||||||||||||||||||BEGINNING OF MULTI-COMPONENT FUNCTIONS||||||||||||||||||||||||||||||
    // Sub-function
    public boolean IsHighgoalOnLeftOfXCenter() {
        if (Robot.receiveJevoisDataSubsys.getXCoord() >= (getJevoisCamWidth()/2)) {
            return false;
        } else {
            return true;
        }
    }
    //#endregion

    //#region   ====================BEGINNING OF SWEEP FUNCTIONS SECTION=======================================
    // Sub-function
    public void StopwatchTurnAndStop(long msDelayTime, boolean turningLeft) {
        // If sweep stopwatch is not started
        if (getStartSweepStopwatch() != true) {
            setSweepStopwatchStartTime(System.currentTimeMillis());
            setStartSweepStopwatch(true);
        // If sweep stopwatch is started
        } else {
            if (turningLeft) {
                // Set motors to turn to face its left
                Robot.driveSubsystem.setLeftMotorValue(     (getLeftMotorDirFlip()) * ( (-1) * getSweepLeftMotorMaxPercent() )    );
                Robot.driveSubsystem.setRightMotorValue(    (getRightMotorDirFlip()) * ( (1) * getSweepRightMotorMaxPercent() )    );
            } else {
                // Set motors to turn to face its right
                Robot.driveSubsystem.setLeftMotorValue(     (getLeftMotorDirFlip()) * ( (50) * getSweepLeftMotorMaxPercent() )    );
                Robot.driveSubsystem.setRightMotorValue(    (getRightMotorDirFlip()) * ( (-50) * getSweepRightMotorMaxPercent() )      );
            }

            setSweepStopwatchTime(System.currentTimeMillis() - getSweepStopwatchStartTime());
        }

        // If sweep duration is fulfilled
        if (getSweepStopwatchTime() >= msDelayTime) {
            Robot.driveSubsystem.stopDrive();

            // Reset the sweep stopwatch
            setStartSweepStopwatch(false);

            // Confirm that the sweep has fulfilled its delay time
            setIsSweepFinished(true);
        }
    }
    //======================================================================================================
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

            // Save that we have found a highgoal to follow
            setConsistentHighgoalFound(true);
            // Stop the robot from trying to confirm a consistently-time highgoal
            setStopConfirmCmd(true);
        }
    }
    //======================================================================================================
    public void ConfirmHighgoalXYWithTimeout() {
        // If the robot has NOT been trying to confirm a consistent highgoal for the max time allowed
        if (getConfirmStopwatchTime() < getConfirmStopwatchMsTimeout()) {
            if (! Robot.receiveJevoisDataSubsys.IsReceivedStringValidData()) {
                setConfirmStopwatchTime(0);
                setConfirmStopwatchResetTime(System.currentTimeMillis());
            } else {
                setConfirmStopwatchTime(System.currentTimeMillis() - getConfirmStopwatchResetTime());
            }

            // Stop the robot from trying to confirm a highgoal if a constant stream of XY highgoal coords was found
            StopConfirmCmdIfConsistentHighgoalIsFound();

        // If the robot has been trying to confirm a consistent highgoal for the max time allowed
        } else {
            // Save that we have NOT found a highgoal to follow
            setConsistentHighgoalFound(false);
            // Stop the robot from trying to confirm a consistently-time highgoal
            setStopConfirmCmd(true);
        }
    }
    //#endregion    ====================END OF CONFIRM FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF FACE FUNCTIONS SECTION=======================================
    // Sub-function
    public boolean IsHighgoalXCoordWithinFaceThreshold() {
        // If the highgoal's distance from the x axis' center is within threshold
        if (    (Robot.receiveJevoisDataSubsys.getXCoord() >= ((getJevoisCamWidth()/2) - getFaceXCoordThreshold())) && 
                (Robot.receiveJevoisDataSubsys.getXCoord() <= ((getJevoisCamWidth()/2) + getFaceXCoordThreshold()))    ) {
            return true;
        } else {
            return false;
        }
    }
    //======================================================================================================
    // Sub-function
    public double HighgoalXCenterOtherDiffPercentage(int highgoalXCoord) {
        // If the found highgoal x coord is on the left side of the cam's x-center line
        if (IsHighgoalOnLeftOfXCenter()) {
            return (    (1 - (highgoalXCoord / (getJevoisCamWidth()/2))  )   );
        // If the found highgoal x coord is on the right side of the cam's x-center line
        } else {
            return (    (1 - ((highgoalXCoord - (getJevoisCamWidth()/2)) / (getJevoisCamWidth()/2))  )   );
        }
    }
    //======================================================================================================
    // Sub-function
    public void TurnToHighgoalWithOtherDiffPercent(boolean usingBackupCoords) {
        if (usingBackupCoords) {
            // If the highgoal is on the left half of the jevois cam's view
            if (IsHighgoalOnLeftOfXCenter()) {
                Robot.driveSubsystem.setLeftMotorValue(     (getLeftMotorDirFlip()) * ( (-1) * (HighgoalXCenterOtherDiffPercentage(getBackupConfirmedHighgoalXCoord())) )  );
                Robot.driveSubsystem.setRightMotorValue(    (getRightMotorDirFlip()) * ( (1) * (HighgoalXCenterOtherDiffPercentage(getBackupConfirmedHighgoalXCoord())) )   );
            // If the highgoal is on the right half of the jevois cam's view
            } else {
                Robot.driveSubsystem.setLeftMotorValue(     (getLeftMotorDirFlip()) * ( (1) * (HighgoalXCenterOtherDiffPercentage(getBackupConfirmedHighgoalXCoord())) )  );
                Robot.driveSubsystem.setRightMotorValue(    (getRightMotorDirFlip()) * ( (-1) * (HighgoalXCenterOtherDiffPercentage(getBackupConfirmedHighgoalXCoord())) )   );
            }
        } else {
            // If the highgoal is on the left half of the jevois cam's view
            if (IsHighgoalOnLeftOfXCenter()) {
                Robot.driveSubsystem.setLeftMotorValue(     (getLeftMotorDirFlip()) * ( (-1) * (HighgoalXCenterOtherDiffPercentage(Robot.receiveJevoisDataSubsys.getXCoord())) )  );
                Robot.driveSubsystem.setRightMotorValue(    (getRightMotorDirFlip()) * ( (1) * (HighgoalXCenterOtherDiffPercentage(Robot.receiveJevoisDataSubsys.getXCoord())) )   );
            // If the highgoal is on the right half of the jevois cam's view
            } else {
                Robot.driveSubsystem.setLeftMotorValue(     (getLeftMotorDirFlip()) * ( (1) * (HighgoalXCenterOtherDiffPercentage(Robot.receiveJevoisDataSubsys.getXCoord())) )  );
                Robot.driveSubsystem.setRightMotorValue(    (getRightMotorDirFlip()) * ( (-1) * (HighgoalXCenterOtherDiffPercentage(Robot.receiveJevoisDataSubsys.getXCoord())) )   );
            }
        }
    }
    //======================================================================================================
    public void UpdateBackupCoords() {
        // TODO write math calculations for updating backup coords
        //setBackupConfirmedHighgoalXCoord(backupConfirmedHighgoalXCoord);
        //setBackupConfirmedHighgoalYCoord(backupConfirmedHighgoalYCoord);
    }
    //======================================================================================================
    // Sub-function
    public void DriveTurnToFaceHighgoal() {
        // If the robot has backup coords to use, and is by default set to face highgoal based on backup coords
        if (getIsUsingBackupCoordsAsDefault() && getConsistentHighgoalFound()) {
            UpdateBackupCoords();
            TurnToHighgoalWithOtherDiffPercent(true);
        // If the robot does NOT (have backup coords AND default setting to face highgoal based on backup coords)
        } else {
            TurnToHighgoalWithOtherDiffPercent(false);
        }
    }
    //======================================================================================================
    public void TurnToFaceHighgoalWithTimedThreshold() {
        if (getFaceStopwatchTime() >= getFaceMsTimeThreshold()) {
            Robot.driveSubsystem.stopDrive();
            setIsFaceHighgoalComplete(true);
        } else {
            DriveTurnToFaceHighgoal();
            if (! Robot.receiveJevoisDataSubsys.IsReceivedStringValidData() ||
            (! IsHighgoalXCoordWithinFaceThreshold())  ) {
                
                setFaceStopwatchTime(0);
                setFaceStopwatchResetTime(System.currentTimeMillis());
            }

            if (IsHighgoalXCoordWithinFaceThreshold()) {
                setFaceStopwatchTime(System.currentTimeMillis() - getFaceStopwatchResetTime());
            }
        }
    }
    //#endregion    ====================END OF FACE FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF DRIVE FUNCTIONS SECTION========================================
    // Sub-function
    public boolean IsCloseEnoughToHighgoal(boolean usingBoxAndDistThresh) {
        if (usingBoxAndDistThresh) {
            if (    (Robot.receiveJevoisDataSubsys.getRectSize() >= getHighgoalRectAreaThreshold()) && 
            (getHighCenterPosDistFromHighgoal() <= getHighgoalDistThreshold())    ) {
                return true;
            } else {
                return false;
            }
        } else {
            if (     (Robot.receiveJevoisDataSubsys.getRectSize() >= getHighgoalRectAreaThreshold()) || 
            (getHighCenterPosDistFromHighgoal() <= getHighgoalDistThreshold())    ) {
                return true;
            } else {
                return false;
            }
        }
    }
    //======================================================================================================
    // Sub-function
    public void UpdateDistFromHighgoal() {
        // TODO figure out how to get ultrasonic sensor input
        //setHighCenterPosDistFromHighgoal(highCenterPosDistFromHighgoal);

        // TODO if using multiple distance sensors, do math here
        //setTrueHighgoalDist(getHighCenterPosDistFromHighgoal());
    }
    //======================================================================================================
    public void DriveUntilCloseToHighgoal() {
        // If the robot is close enough to the highgoal by the setup thresholds (ultrasonic distance AND/OR highgoal overall contour rect size)
        if (IsCloseEnoughToHighgoal(false)) {
            // Stop the robot's driving motors
            Robot.driveSubsystem.stopDrive();

            // Indicate that we have reached the highgoal
            setHasReachedHighgoal(true);
        } else {
            // Drive the robot's drive motors at max driving speed (config)
            Robot.driveSubsystem.setLeftMotorValue(     (getLeftMotorDirFlip()) * ( (1) * (getMaxBothMotorOutputPercent()) )    );
            Robot.driveSubsystem.setRightMotorValue(     (getRightMotorDirFlip()) * ( (1) * (getMaxBothMotorOutputPercent()) )    );
            
            // Use distance(ultrasonic) sensors to update our known distance from the highgoal
            UpdateDistFromHighgoal();
        }
    }
    //#endregion
}