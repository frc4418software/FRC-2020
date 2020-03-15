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
            // TODO CONFIG if robot uses the backup coords to drive towards the highgoal or not
    private boolean isUsingBackupCoordsAsDefault = false;      // CONFIG SET WHETHER ROBOT FACES HIGHGOAL USING BACKUP COORDS OR NOT
    public boolean getIsUsingBackupCoordsAsDefault() {
        return this.isUsingBackupCoordsAsDefault;
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
            // TODO CONFIG a safe overall max speed percentage for the drive motors during vision
    private double maxBothMotorOutputPercent = 0.75;      // CONFIG SET THE MAXIMUM PERCENT SPEED OF BOTH MOTORS
    public double getMaxBothMotorOutputPercent() {
        return this.maxBothMotorOutputPercent;
    }
    //=======================================================================
            // TODO CONFIG whether 1 or -1 makes the left motor turn forward
    private int leftMotorDirFlip = 1;           // CONFIG USE TO FLIP THE DIRECTION OF THE LEFT MOTOR
    public int getLeftMotorDirFlip() {
        return this.leftMotorDirFlip;
    }
    //=======================================================================
            // TODO CONFIG whether 1 or -1 makes the right motor turn forward
    private int rightMotorDirFlip = -1;         // CONFIG USE TO FLIP THE DIRECTION OF THE RIGHT MOTOR
    public int getRightMotorDirFlip() {
        return this.rightMotorDirFlip;
    }
    //=======================================================================
    private int distFromHighgoal;
    public int getDistFromHighgoal() {
        return this.distFromHighgoal;
    }
    public void setDistFromHighgoal(int distFromHighgoal) {
        this.distFromHighgoal = distFromHighgoal;
    }
    //=======================================================================
    private int backupConfirmedHighgoalRectSize;
    public int getBackupConfirmedHighgoalRectSize() {
        return this.backupConfirmedHighgoalRectSize;
    }
    public void setBackupConfirmedHighgoalRectSize(int backupConfirmedHighgoalRectSize) {
        this.backupConfirmedHighgoalRectSize = backupConfirmedHighgoalRectSize;
    }
    //#endregion    |||||||||||||||||||||||END OF MULTI-COMPONENT VARIABLES||||||||||||||||||||||||||||||||||
    
    //#region   ====================BEGINNING OF SWEEP VARIABLES SECTION=======================================
            // TODO CONFIG WITH MATH to find time for robot to do a jevois-180 deg's full left/right turn
    private int msTimeForFullSideRotation = 1000;           // CONFIG SET TIME NEEDED FOR ROBOT TO TURN A JEVOIS-180 deg's WHEN IN LEFTMOST OR RIGHTMOST
    public int getMsTimeForFullSideRotation() {
        return this.msTimeForFullSideRotation;
    }
    //=======================================================================
            // TODO CONFIG WITH MATH to find time for robot to do partial turn when in centermost
    private int msTimeForCenterSideRotation = 417;         // CONFIG SET TIME FOR ROBOT TO TURN A LITTLE TOWARDS HIGHGOAL WHEN IN CENTERMOST
    public int getMsTimeForCenterSideRotation() {
        return this.msTimeForCenterSideRotation;
    }
    //=======================================================================
            // TODO CONFIG whether the robot should go through the "sweep" stage or not
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
    //TODO WRITE needed variables for adjusting the perceived brightness/lighting of the JeVois
    //=======================================================================
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
                // TODO CONFIG good time for a consistent receival of highgoal XY coords
    private long consistentHighgoalConfirmMsTime = 1300;   // CONFIG TIME OF CONSISTENT XY RECEIVE FOR CONFIRMED HIGHGOAL
    public long getConsistentHighgoalConfirmMsTime() {
        return this.consistentHighgoalConfirmMsTime;
    }
    //=======================================================================    
                // TODO CONFIG good max time to look for a consistent stream of highgoal XY coords
    private long confirmStopwatchMsTimeout = 2500;    // CONFIG MAX TIME TO LOOK FOR CONSISTENT STREAM OF XY, MUST BE LARGER THAN MS CONFIRM TIME
    public long getConfirmStopwatchMsTimeout() {
        return this.confirmStopwatchMsTimeout;
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
                // TODO CONFIG a good x threshold for facing the center of the highgoal
    private int faceXCoordThreshold = 12;   // CONFIG DISTANCE FROM WANTED X CENTER ALLOWED
    public int getFaceXCoordThreshold() {
        return this.faceXCoordThreshold;
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
            // TODO CONFIG ms time threshold for how long to confirm it's facing a consistent highgoal
    private long faceMsTimeThreshold = 700;        // CONFIG TIME NEEDED TO CONFIRM THAT ROBOT IS CONSISTENTLY FACING THE HIGHGOAL
    public long getFaceMsTimeThreshold() {
        return this.faceMsTimeThreshold;
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
                // TODO CONFIG highgoal rect size threshold for when the robot gets close enough
    private int highgoalRectAreaThreshold = 150;         // CONFIG SET SIZE OF HIGHGOAL THRESHOLD WHEN ROBOT GETS CLOSE ENOUGH
    public int getHighgoalRectAreaThreshold() {
        return this.highgoalRectAreaThreshold;
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
            // TODO CONFIG the "close enough" distance's threshold for when the robot should stop driving towards highgoal
    private int highgoalDistThreshold = 20;     // CONFIG SET ULTRASONIC DIST THRESHOLD FROM HIGHGOAL WHEN ROBOT GETS CLOSE ENOUGH TO HIGHGOAL
    public int getHighgoalDistThreshold() {
        return this.highgoalDistThreshold;
    }
    //=======================================================================
            // TODO (IF NEEDED) CONFIG y threshold for when to stop driving towards highgoal            
    private int driveToHighgoalYThresh = 75;            // CONFIG SET Y HEIGHT HIGHGOAL SHOULD BE AT WHEN ROBOT GETS CLOSE ENOUGH TO HIGHGOAL
    public int getDriveToHighgoalYThresh() {
        return this.driveToHighgoalYThresh;
    }
    //=======================================================================
    private boolean isCloseEnoughToHighgoal = false;
    public boolean getIsCloseEnoughToHighgoal() {
        return this.isCloseEnoughToHighgoal;
    }
    public void setIsCloseEnoughToHighgoal(boolean isCloseEnoughToHighgoal) {
		this.isCloseEnoughToHighgoal = isCloseEnoughToHighgoal;
	}
    //=======================================================================
    private boolean hasReachedHighgoal = false;
    public boolean getHasReachedHighgoal() {
        return this.hasReachedHighgoal;
    }
    public void setHasReachedHighgoal(boolean hasReachedHighgoal) {
		this.hasReachedHighgoal = hasReachedHighgoal;
	}
    //#endregion    ====================END OF DRIVE VARIABLES SECTION=======================================

    //#region   ====================BEGINNING OF ADJUST VARIABLES SECTION=====================================
    //#region   -------------BEGINNING OF ADJUST-MULTI-PART-VARIABLES----------------
    public boolean stopAdjustCmd = false;
    public boolean getStopAdjustCmd() {
        return this.stopAdjustCmd;
    }
    public void setStopAdjustCmd(boolean stopAdjustCmd) {
		this.stopAdjustCmd = stopAdjustCmd;
	}
    //#endregion    -----------END OF ADJUST-MULTI-PART-VARIABLES-----------------
    //#region   -------------BEGINNING OF ADJUST-CONFIRM-VARIABLES--------------
    // TODO CONFIG correct pos for jevois servo when it points up to adjust to highgoal
    private int jevoisServoAdjustPos = 30;
    public int getJevoisServoAdjustPos() {
        return this.jevoisServoAdjustPos;
    }
    public void setJevoisServoAdjustPos(int jevoisServoAdjustPos) {
		this.jevoisServoAdjustPos = jevoisServoAdjustPos;
    }
    //=======================================================================
    private long adjustConfirmStopwatchTime = 0;
    public long getAdjustConfirmStopwatchTime() {
        return this.adjustConfirmStopwatchTime;
    }
    public void setAdjustConfirmStopwatchTime(long adjustConfirmStopwatchTime) {
		this.adjustConfirmStopwatchTime = adjustConfirmStopwatchTime;
    }
    //=======================================================================
    // TODO CONFIG time for confirming a highgoal for the adjust stage
    private long adjustHighgoalConfirmMsTime = 900;     // CONFIG SET TIME NEEDED FOR ADJUST STAGE TO CONFIRM A HIGHGOAL TO USE
    public long getAdjustHighgoalConfirmMsTime() {
        return this.adjustHighgoalConfirmMsTime;
    }
    //=======================================================================
    private boolean adjustHighgoalFound = false;
    public boolean getAdjustHighgoalFound() {
        return this.adjustHighgoalFound;
    }
    public void setAdjustHighgoalFound(boolean adjustHighgoalFound) {
		this.adjustHighgoalFound = adjustHighgoalFound;
    }
    //=======================================================================
            // TODO CONFIG if robot defaultly adjusts-face using a re-confirmation of the highgoal
    private boolean stopAdjustConfirmComponent = false;     // CONFIG SET DEFAULT WHETHER ADJUST SHOULD USE A RE-CONFIRMED HIGHGOAL TO FACE
    public boolean getStopAdjustConfirmComponent() {
        return this.stopAdjustConfirmComponent;
    }
    public void setStopAdjustConfirmComponent(boolean stopAdjustConfirmComponent) {
		this.stopAdjustConfirmComponent = stopAdjustConfirmComponent;
    }
    //=======================================================================
            // TODO CONFIG max time for adjust stage to try re-confirming a highgoal in order to re-face
    private long adjustConfirmStopwatchMsTimeout = 1200;        // CONFIG TIME GREATER THAN CONFIRM TIME FOR WHEN ADJUST SHOULD STOP TRYING TO RE-CONFIRM
    public long getAdjustConfirmStopwatchMsTimeout() {
        return this.adjustConfirmStopwatchMsTimeout;
    }
    //=======================================================================
    private long adjustConfirmStopwatchResetTime = 0;
    public long getAdjustConfirmStopwatchResetTime() {
        return this.adjustConfirmStopwatchResetTime;
    }
    public void setAdjustConfirmStopwatchResetTime(long adjustConfirmStopwatchResetTime) {
		this.adjustConfirmStopwatchResetTime = adjustConfirmStopwatchResetTime;
    }
    //#endregion    -------------END OF ADJUST-CONFIRM-VARIABLES--------------
    //#region   -------------BEGINNING OF ADJUST-FACE-VARIABLES--------------
    private long adjustFaceStopwatchTime = 0;
    public long getAdjustFaceStopwatchTime() {
        return this.adjustFaceStopwatchTime;
    }
    public void setAdjustFaceStopwatchTime(long adjustFaceStopwatchTime) {
		this.adjustFaceStopwatchTime = adjustFaceStopwatchTime;
    }
    //=======================================================================
                // TODO CONFIG time needed to know that re-face is within the x threshold
    private long adjustFaceMsTimeThreshold = 500;       // CONFIG TIME THAT RE-FACE IS WITHIN THRESHOLD
    public long getAdjustFaceMsTimeThreshold() {
        return this.adjustFaceMsTimeThreshold;
    }
    //=======================================================================
    private boolean isAdjustFaceHighgoalComplete = false;
    public boolean getIsAdjustFaceHighgoalComplete() {
        return this.isAdjustFaceHighgoalComplete;
    }
    public void setIsAdjustFaceHighgoalComplete(boolean isAdjustFaceHighgoalComplete) {
		this.isAdjustFaceHighgoalComplete = isAdjustFaceHighgoalComplete;
    }
    //=======================================================================
            // TODO CONFIG x threshold for re-facing the highgoal
    private int adjustFaceXCoordThreshold = 8;      // CONFIG CLOSE-UP X THRESHOLD FOR RE-FACING HIGHGOAL
    public int getAdjustFaceXCoordThreshold() {
        return this.adjustFaceXCoordThreshold;
    }
    //=======================================================================
    private long adjustFaceStopwatchResetTime = 0;
    public long getAdjustFaceStopwatchResetTime() {
        return this.adjustFaceStopwatchResetTime;
    }
    public void setAdjustFaceStopwatchResetTime(long adjustFaceStopwatchResetTime) {
		this.adjustFaceStopwatchResetTime = adjustFaceStopwatchResetTime;
    }
    //=======================================================================
    private long adjustFaceHighgoalStartTime;
    public long getAdjustFaceHighgoalStartTime() {
        return this.adjustFaceHighgoalStartTime;
    }
    public void setAdjustFaceHighgoalStartTime(long adjustFaceHighgoalStartTime) {
        this.adjustFaceHighgoalStartTime = adjustFaceHighgoalStartTime;
    }
    //=======================================================================
            // TODO CONFIG maximum time allowed to re-face highgoal
    private long adjustFaceHighgoalTimeMax = 800;       // CONFIG TIME GREATER THAN RE-FACE TIME WHEN TO STOP TRYING TO RE-FACE
    public long getAdjustFaceHighgoalTimeMax() {
        return this.adjustFaceHighgoalTimeMax;
    }
    //=======================================================================
    private boolean startAdjustFaceStopwatch = false;
    public boolean getStartAdjustFaceStopwatch() {
        return this.startAdjustFaceStopwatch;
    }
    public void setStartAdjustFaceStopwatch(boolean startAdjustFaceStopwatch) {
		this.startAdjustFaceStopwatch = startAdjustFaceStopwatch;
	}
    //#endregion    -------------END OF ADJUST-FACE-VARIABLES--------------
    //#region   -------------BEGINNING OF ADJUST-DRIVE-VARIABLES--------------
            // TODO CONFIG max distance from highgoal allowed when adjusting to highgoal
    private int highgoalAdjustDistMax = 20;     // CONFIG MAXIMUM ADJUST DIST FROM HIGHGOAL
    public int getHighgoalAdjustDistMax() {
        return this.highgoalAdjustDistMax;
    }
    public void setHighgoalAdjustDistMax(int highgoalAdjustDistMax) {
		this.highgoalAdjustDistMax = highgoalAdjustDistMax;
    }
    //=======================================================================
            // TODO CONFIG min dist from highgoal allowed when adjusting
    private int highgoalAdjustDistMin = 10;     // CONFIG MINIMUM ADJUST DIST FROM HIGHGOAL
	public int getHighgoalAdjustDistMin() {
		return this.highgoalAdjustDistMin;
	}
	public void setHighgoalAdjustDistMin(int highgoalAdjustDistMin) {
		this.highgoalAdjustDistMin = highgoalAdjustDistMin;
    }
    //=======================================================================
    private boolean adjustDrivedWithinHighgoal = false;
    public boolean getAdjustDrivedWithinHighgoal() {
        return this.adjustDrivedWithinHighgoal;
    }
    public void setAdjustDrivedWithinHighgoal(boolean adjustDrivedWithinHighgoal) {
		this.adjustDrivedWithinHighgoal = adjustDrivedWithinHighgoal;
	}
    //=======================================================================
    private boolean hasAdjustReachedHighgoal = false;
    public boolean getHasAdjustReachedHighgoal() {
        return this.hasAdjustReachedHighgoal;
    }
    public void setHasAdjustReachedHighgoal(boolean hasAdjustReachedHighgoal) {
		this.hasAdjustReachedHighgoal = hasAdjustReachedHighgoal;
    }
    //=======================================================================

    //#endregion    -------------END OF ADJUST-DRIVE-VARIABLES--------------------
    //#endregion    ====================END OF ADJUST VARIABLES SECTION=======================================

    //#region   ====================BEGINNING OF SHOOT VARIABLES SECTION=====================================
    
    //#endregion    ====================END OF SHOOT VARIABLES SECTION=======================================






    //#region   ||||||||||||||||||||||BEGINNING OF MULTI-COMPONENT FUNCTIONS||||||||||||||||||||||||||||||
    // Sub-function
    public boolean IsHighgoalOnLeftOfXCenter() {
        if (Robot.receiveJevoisDataSubsys.getXCoord() >= (getJevoisCamWidth()/2)) {
            return false;
        } else {
            return true;
        }
    }
    //======================================================================================================
    // Sub-function
    public void SaveBackupHighgoalXYRectSize() {
        // Set the backup highgoal coord to the parsed, found, and usable integer coordinates
        setBackupConfirmedHighgoalXCoord(Robot.receiveJevoisDataSubsys.getXCoord());
        setBackupConfirmedHighgoalYCoord(Robot.receiveJevoisDataSubsys.getYCoord());
        // Also set the backup highgoal rect size to the parsed, found, and usable integer size
        setBackupConfirmedHighgoalRectSize(Robot.receiveJevoisDataSubsys.getRectSize());
    }
    //======================================================================================================
    // Sub-function
    public void UpdateDistFromHighgoal() {
        // TODO WRITE how to get ultrasonic sensor input
        //setHighCenterPosDistFromHighgoal(highCenterPosDistFromHighgoal);

        // TODO (IF NEEDED) WRITE math here for setting true dist from highgoal using multiple dist sensors
        //setTrueHighgoalDist(getHighCenterPosDistFromHighgoal());
    }
    //#endregion    ||||||||||||||||||||||END OF MULTI-COMPONENT FUNCTIONS||||||||||||||||||||||||||||||

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
                Robot.driveSubsystem.setLeftMotorValue(     (getLeftMotorDirFlip()) * ( (-1) )    );
                Robot.driveSubsystem.setRightMotorValue(    (getRightMotorDirFlip()) * ( (1) )    );
            } else {
                // Set motors to turn to face its right
                Robot.driveSubsystem.setLeftMotorValue(     (getLeftMotorDirFlip()) * ( (50) )    );
                Robot.driveSubsystem.setRightMotorValue(    (getRightMotorDirFlip()) * ( (-50) )      );
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
    public void StopConfirmCmdIfHighgoalIsFound() {
        // If there is a constant receiving of highgoal XY coords greater than the CONFIG confirm time
        if (getConfirmStopwatchTime() >= getConsistentHighgoalConfirmMsTime()) {
            // Read the latest XY and parse it into usable integer coordinates
            Robot.receiveJevoisDataSubsys.ReadAndParseXY();

            // Store the confirmed highgoal's XY coords and rect size in the backup* vars
            SaveBackupHighgoalXYRectSize();

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
            StopConfirmCmdIfHighgoalIsFound();

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
    // Sub-function
    public void UpdateBackupCoords() {
        // TODO WRITE math calculations for updating backup coords
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
    public void FaceHighgoalTimedThreshold() {
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
    public void CheckIfReachedHighgoal() {
        if (getDistFromHighgoal() <= getHighgoalDistThreshold()) {
            setIsCloseEnoughToHighgoal(true);
        } else {
            setIsCloseEnoughToHighgoal(false);
        }
    }
    //======================================================================================================
    public void DriveUntilCloseToHighgoal() {
        // If the robot is close enough to the highgoal by the setup thresholds (ultrasonic distance AND/OR highgoal overall contour rect size)
        if (getIsCloseEnoughToHighgoal()) {
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
            // Check if we have reached the highgoal
            CheckIfReachedHighgoal();
        }
    }
    //#endregion    ====================END OF DRIVE FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF ADJUST FUNCTIONS SECTION=======================================
    //#region   --------------BEGINNING OF ADJUST-CONFIRM-FUNCTIONS------------------------------------
    // Sub-function
    public void AimJevoisServoForAdjusting() {
        // TODO WRITE code to set the jevois servo to the pos needed

        // TODO WRITE code for delaying robot to allow camera to re-adjust to new brightness and etc
    }
    //======================================================================================================
    // Sub-function
    public void StopAdjustConfirmIfHighgoalFound() {
        // If there is a constant receiving of highgoal XY coords greater than the CONFIG confirm time
        if (getAdjustConfirmStopwatchTime() >= getAdjustHighgoalConfirmMsTime()) {
            // Read the latest XY and parse it into usable integer coordinates
            Robot.receiveJevoisDataSubsys.ReadAndParseXY();

            // Store the re-confirmed highgoal's XY coords and rect size in the backup* vars
            SaveBackupHighgoalXYRectSize();

            // Save that we have found a highgoal to follow
            setAdjustHighgoalFound(true);
            // Stop the robot from trying to confirm a consistently-time highgoal
            setStopAdjustConfirmComponent(true);
        }
    }
    //======================================================================================================
    // Sub-function
    public void TryToConfirmHighgoalForAdjusting() {
        // If the robot has NOT been trying to confirm a consistent highgoal for the max time allowed
        if (getAdjustConfirmStopwatchTime() < getAdjustConfirmStopwatchMsTimeout()) {
            if (! Robot.receiveJevoisDataSubsys.IsReceivedStringValidData()) {
                setAdjustConfirmStopwatchTime(0);
                setAdjustConfirmStopwatchResetTime(System.currentTimeMillis());
            } else {
                setAdjustConfirmStopwatchTime(System.currentTimeMillis() - getAdjustConfirmStopwatchResetTime());
            }

            // Stop the robot from trying to confirm a highgoal if a constant stream of XY highgoal coords was found
            StopAdjustConfirmIfHighgoalFound();

        // If the robot has been trying to confirm a consistent highgoal for the max time allowed
        } else {
            // Save that we have NOT found a highgoal to follow
            setAdjustHighgoalFound(false);
            // Stop the robot from trying to confirm a consistently-time highgoal
            setStopAdjustConfirmComponent(true);
        }
    }
    //#endregion    --------------END OF ADJUST-CONFIRM-FUNCTIONS------------------------------------
    //#region   --------------BEGINNING OF ADJUST-FACE-FUNCTIONS------------------------------------
    // Sub-function
    public boolean IsHighgoalXCoordWithinAdjustFaceThreshold() {
        // If the highgoal's distance from the x axis' center is within threshold
        if (    (Robot.receiveJevoisDataSubsys.getXCoord() >= ((getJevoisCamWidth()/2) - getAdjustFaceXCoordThreshold())) && 
                (Robot.receiveJevoisDataSubsys.getXCoord() <= ((getJevoisCamWidth()/2) + getAdjustFaceXCoordThreshold()))    ) {
            return true;
        } else {
            return false;
        }
    }
    //======================================================================================================
    // Sub-function
    public boolean IsAdjustFaceHighgoalTimedOut() {
        if (    (   System.currentTimeMillis() - getAdjustFaceHighgoalStartTime()  ) > (getAdjustFaceHighgoalTimeMax())   ) {
            return true;
        } else {
            return false;
        }
    }
    //======================================================================================================
    // Sub-function
    public void StopResetCompleteAdjustFacing() {
        // Stop the robot's drive motors
        Robot.driveSubsystem.stopDrive();
        // Reset the adjust-face stopwatch
        setStartAdjustFaceStopwatch(false);
        // Indicate that we have finished adjust-facing the highgoal
        setIsAdjustFaceHighgoalComplete(true);
    }
    //======================================================================================================
    // Sub-function
    public void AdjustFaceHighgoalTimedThreshold() {
        if (! getStartAdjustFaceStopwatch()) {
            setStartAdjustFaceStopwatch(true);
            setAdjustFaceHighgoalStartTime(System.currentTimeMillis());
        }

        if (getAdjustFaceStopwatchTime() >= getAdjustFaceMsTimeThreshold()) {
            StopResetCompleteAdjustFacing();
            
        } else {
            // If adjust stage has NOT timed out for trying to re-face
            if (! IsAdjustFaceHighgoalTimedOut()) {
                DriveTurnToFaceHighgoal();
                if (! Robot.receiveJevoisDataSubsys.IsReceivedStringValidData() ||
                (! IsHighgoalXCoordWithinAdjustFaceThreshold())  ) {
                    
                    setAdjustFaceStopwatchTime(0);
                    setAdjustFaceStopwatchResetTime(System.currentTimeMillis());
                }

                if (IsHighgoalXCoordWithinAdjustFaceThreshold()) {
                    setAdjustFaceStopwatchTime(System.currentTimeMillis() - getAdjustFaceStopwatchResetTime());
                }
            // If adjust stage has timed out for trying to re-face
            } else {
                StopResetCompleteAdjustFacing();
            }
        }
    }
    //#endregion    --------------END OF ADJUST-FACE-FUNCTIONS------------------------------------
    //#region   --------------BEGINNING OF ADJUST-DRIVE-FUNCTIONS--------------------------------
    // Sub-function
    public void CheckIfAdjustReachedHighgoal() {
        if (    ( getDistFromHighgoal() <= getHighgoalAdjustDistMax() ) &&
        (getDistFromHighgoal() >= getHighgoalAdjustDistMin())    ) {
            setAdjustDrivedWithinHighgoal(true);
        } else {
            setAdjustDrivedWithinHighgoal(false);
        }
    }
    //======================================================================================================
    // Sub-function
    public void AdjustDriveUntilCloseToHighgoal() {
        // If the robot is close enough to the highgoal by the setup thresholds (ultrasonic distance AND/OR highgoal overall contour rect size)
        if (getAdjustDrivedWithinHighgoal()) {
            // Stop the robot's driving motors
            Robot.driveSubsystem.stopDrive();

            // Indicate that we have reached the highgoal
            setHasAdjustReachedHighgoal(true);
        } else {
            // Drive the robot's drive motors at max driving speed (config)
            Robot.driveSubsystem.setLeftMotorValue(     (getLeftMotorDirFlip()) * ( (1) * (getMaxBothMotorOutputPercent()) )    );
            Robot.driveSubsystem.setRightMotorValue(     (getRightMotorDirFlip()) * ( (1) * (getMaxBothMotorOutputPercent()) )    );
            
            // Use distance(ultrasonic) sensors to update our known distance from the highgoal
            UpdateDistFromHighgoal();
            // Check if we have reached the highgoal
            CheckIfAdjustReachedHighgoal();
        }
    }
    //======================================================================================================
    // Sub-function
    public void StopAdjustCmdOnceDrivenWithinThresh() {
        // Re-drive-to the highgoal, get within threshold of shooting dist
        if (! getHasAdjustReachedHighgoal()) {
            AdjustDriveUntilCloseToHighgoal();
        // Stop adjusting, the robot is now prepared to shoot
        } else {
            setStopAdjustCmd(true);
        }
    }
    //#endregion    ------------END OF ADJUST-DRIVE-FUNCTIONS-------------------------
    public void AdjustRobotForShooting() {
        AimJevoisServoForAdjusting();
        // If we have NOT timed out for trying to re-confirm a highgoal to re-face
        if (! getStopAdjustConfirmComponent()) {
            TryToConfirmHighgoalForAdjusting();
        // If we have timed out for trying to re-confirm a highgoal to re-face
        } else {
            // If we did re-confirm a highgoal to use to re-face and re-drive-to
            if (getAdjustHighgoalFound()) {
                // Re-face the highgoal
                if (! getIsAdjustFaceHighgoalComplete()) {
                    AdjustFaceHighgoalTimedThreshold();
                } else {
                    StopAdjustCmdOnceDrivenWithinThresh();
                }
            // If we did NOT re-confirm a highgoal to use to re-face
            } else {
                StopAdjustCmdOnceDrivenWithinThresh();
            }
        }
    }
    //#endregion    ====================END OF ADJUST-DRIVE-FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF SHOOT FUNCTIONS SECTION========================================
    
    //#endregion    ====================END OF SHOOT FUNCTIONS SECTION=======================================
}