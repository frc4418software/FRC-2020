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


public class VisionHighgoalSubsys extends SubsystemBase {
    //#region   ====================BEGINNING OF SWEEP VARIABLES SECTION=======================================
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
    private boolean stopConfirmCmd = false;
    public boolean getStopConfirmCmd() {
        return this.stopConfirmCmd;
    }
    public void setStopConfirmCmd(boolean stopConfirmCmd) {
		this.stopConfirmCmd = stopConfirmCmd;
	}
    //#endregion    ====================END OF CONFIRM VARIABLES SECTION=======================================

    //#region   ====================BEGINNING OF FACE VARIABLES SECTION=======================================
    private long faceStopwatchTime = 0;
    public long getFaceStopwatchTime() {
        return this.faceStopwatchTime;
    }
    public void setFaceStopwatchTime(long faceStopwatchTime) {
		this.faceStopwatchTime = faceStopwatchTime;
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
    private int trueHighgoalDist;
    public int getTrueHighgoalDist() {
        return this.trueHighgoalDist;
    }
    public void setTrueHighgoalDist(int trueHighgoalDist) {
        this.trueHighgoalDist = trueHighgoalDist;
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
    private long adjustConfirmStopwatchTime = 0;
    public long getAdjustConfirmStopwatchTime() {
        return this.adjustConfirmStopwatchTime;
    }
    public void setAdjustConfirmStopwatchTime(long adjustConfirmStopwatchTime) {
		this.adjustConfirmStopwatchTime = adjustConfirmStopwatchTime;
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
    private boolean isAdjustFaceHighgoalComplete = false;
    public boolean getIsAdjustFaceHighgoalComplete() {
        return this.isAdjustFaceHighgoalComplete;
    }
    public void setIsAdjustFaceHighgoalComplete(boolean isAdjustFaceHighgoalComplete) {
		this.isAdjustFaceHighgoalComplete = isAdjustFaceHighgoalComplete;
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
    private boolean startAdjustFaceStopwatch = false;
    public boolean getStartAdjustFaceStopwatch() {
        return this.startAdjustFaceStopwatch;
    }
    public void setStartAdjustFaceStopwatch(boolean startAdjustFaceStopwatch) {
		this.startAdjustFaceStopwatch = startAdjustFaceStopwatch;
	}
    //#endregion    -------------END OF ADJUST-FACE-VARIABLES--------------
    //#region   -------------BEGINNING OF ADJUST-DRIVE-VARIABLES--------------
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
    //#endregion    -------------END OF ADJUST-DRIVE-VARIABLES--------------------
    //#endregion    ====================END OF ADJUST VARIABLES SECTION=======================================





    //#region   ||||||||||||||||||||||BEGINNING OF MULTI-COMPONENT FUNCTIONS||||||||||||||||||||||||||||||
    // Sub-function
    public boolean IsHighgoalOnLeftOfXCenter() {
        if (Robot.xCoord >= (Constants.jevoisCamWidth/2)) {
            return false;
        } else {
            return true;
        }
    }
    //======================================================================================================
    // Sub-function
    public void SaveBackupHighgoalXYRectSize() {
        // Set the backup highgoal coord to the parsed, found, and usable integer coordinates
        Robot.backupHighgoalXCoord = Robot.xCoord;
        Robot.backupHighgoalYCoord = Robot.yCoord;

        // Also set the backup highgoal rect size to the parsed, found, and usable integer size
        Robot.backupHighgoalRectSize = Robot.rectSize;
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
                Robot.driveSubsystem.setLeftMotorValue(     (Constants.leftMotorDirFlip) * ( (-1) )    );
                Robot.driveSubsystem.setRightMotorValue(    (Constants.rightMotorDirFlip) * ( (1) )    );
            } else {
                // Set motors to turn to face its right
                Robot.driveSubsystem.setLeftMotorValue(     (Constants.leftMotorDirFlip) * ( (50) )    );
                Robot.driveSubsystem.setRightMotorValue(    (Constants.rightMotorDirFlip) * ( (-50) )      );
            }

            setSweepStopwatchTime(System.currentTimeMillis() - getSweepStopwatchStartTime());
        }

        // If sweep duration is fulfilled
        if (getSweepStopwatchTime() >= msDelayTime) {
            Robot.driveSubsystem.stopDrive();

            // Reset the sweep stopwatch
            setStartSweepStopwatch(false);

            // Confirm that the sweep has fulfilled its delay time
            Robot.isSweepFinished = true;
        }
    }
    //======================================================================================================
    public void SurveyTurnForHighgoal(int surveyMode) {
        switch (surveyMode) {
            case 1:
                // Surveying mode for when the robot is on the leftmost position
                StopwatchTurnAndStop(Constants.msTimeForFullSideRotation, false);
                break;
            case 2:
                // Surveying mode for when the robot is on the centermost position
                StopwatchTurnAndStop(Constants.msTimeForCenterSideRotation, false);
                break;
            case 3:
                // Surveying mode for when the robot is on the rightmost position
                StopwatchTurnAndStop(Constants.msTimeForFullSideRotation, true);
                break;
        }
    }
    //#endregion    ====================END OF SWEEP FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF CONFIRM FUNCTIONS SECTION=======================================
    // Sub-function
    public void StopConfirmCmdIfHighgoalIsFound() {
        // If there is a constant receiving of highgoal XY coords greater than the CONFIG confirm time
        if (getConfirmStopwatchTime() >= Constants.consistentHighgoalConfirmMsTime) {
            // Read the latest XY and parse it into usable integer coordinates
            Robot.receiveJevoisDataSubsys.ReadAndParseXY();

            // Store the confirmed highgoal's XY coords and rect size in the backup* vars
            SaveBackupHighgoalXYRectSize();

            // Save that we have found a highgoal to follow
            Robot.consistentHighgoalFound = true;
            // Stop the robot from trying to confirm a consistently-time highgoal
            setStopConfirmCmd(true);
        }
    }
    //======================================================================================================
    public void ConfirmHighgoalXYWithTimeout() {
        // If the robot has NOT been trying to confirm a consistent highgoal for the max time allowed
        if (getConfirmStopwatchTime() < Constants.confirmStopwatchMsTimeout) {
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
            Robot.consistentHighgoalFound = false;
            // Stop the robot from trying to confirm a consistently-time highgoal
            setStopConfirmCmd(true);
        }
    }
    //#endregion    ====================END OF CONFIRM FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF FACE FUNCTIONS SECTION=======================================
    // Sub-function
    public boolean IsHighgoalXCoordWithinFaceThreshold() {
        // If the highgoal's distance from the x axis' center is within threshold
        if (    (Robot.xCoord >= ((Constants.jevoisCamWidth/2) - Constants.faceXCoordThreshold)) && 
                (Robot.xCoord <= ((Constants.jevoisCamWidth/2) + Constants.faceXCoordThreshold))    ) {
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
            return (    (1 - (highgoalXCoord / (Constants.jevoisCamWidth/2))  )   );
        // If the found highgoal x coord is on the right side of the cam's x-center line
        } else {
            return (    (1 - ((highgoalXCoord - (Constants.jevoisCamWidth/2)) / (Constants.jevoisCamWidth/2))  )   );
        }
    }
    //======================================================================================================
    // Sub-function
    public void TurnToHighgoalWithOtherDiffPercent(boolean usingBackupCoords) {
        if (usingBackupCoords) {
            // If the highgoal is on the left half of the jevois cam's view
            if (IsHighgoalOnLeftOfXCenter()) {
                Robot.driveSubsystem.setLeftMotorValue(     (Constants.leftMotorDirFlip) * ( (-1) * (HighgoalXCenterOtherDiffPercentage(Robot.backupHighgoalXCoord)) )  );
                Robot.driveSubsystem.setRightMotorValue(    (Constants.rightMotorDirFlip) * ( (1) * (HighgoalXCenterOtherDiffPercentage(Robot.backupHighgoalXCoord)) )   );
            // If the highgoal is on the right half of the jevois cam's view
            } else {
                Robot.driveSubsystem.setLeftMotorValue(     (Constants.leftMotorDirFlip) * ( (1) * (HighgoalXCenterOtherDiffPercentage(Robot.backupHighgoalXCoord)) )  );
                Robot.driveSubsystem.setRightMotorValue(    (Constants.rightMotorDirFlip) * ( (-1) * (HighgoalXCenterOtherDiffPercentage(Robot.backupHighgoalXCoord)) )   );
            }
        } else {
            // If the highgoal is on the left half of the jevois cam's view
            if (IsHighgoalOnLeftOfXCenter()) {
                Robot.driveSubsystem.setLeftMotorValue(     (Constants.leftMotorDirFlip) * ( (-1) * (HighgoalXCenterOtherDiffPercentage(Robot.xCoord)) )     );
                Robot.driveSubsystem.setRightMotorValue(    (Constants.rightMotorDirFlip) * ( (1) * (HighgoalXCenterOtherDiffPercentage(Robot.xCoord)) )     );
            // If the highgoal is on the right half of the jevois cam's view
            } else {
                Robot.driveSubsystem.setLeftMotorValue(     (Constants.leftMotorDirFlip) * ( (1) * (HighgoalXCenterOtherDiffPercentage(Robot.xCoord)) )      );
                Robot.driveSubsystem.setRightMotorValue(    (Constants.rightMotorDirFlip) * ( (-1) * (HighgoalXCenterOtherDiffPercentage(Robot.xCoord)) )    );
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
        if (Constants.isUsingBackupCoordsAsDefault && Robot.consistentHighgoalFound) {
            UpdateBackupCoords();
            TurnToHighgoalWithOtherDiffPercent(true);
        // If the robot does NOT (have backup coords AND default setting to face highgoal based on backup coords)
        } else {
            TurnToHighgoalWithOtherDiffPercent(false);
        }
    }
    //======================================================================================================
    public void FaceHighgoalTimedThreshold() {
        if (getFaceStopwatchTime() >= Constants.faceMsTimeThreshold) {
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
        if (Robot.distFromHighgoal <= Constants.highgoalDistThreshold) {
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
            Robot.driveSubsystem.setLeftMotorValue(     (Constants.leftMotorDirFlip) * ( (1) * (Constants.maxBothMotorOutputPercent) )    );
            Robot.driveSubsystem.setRightMotorValue(     (Constants.rightMotorDirFlip) * ( (1) * (Constants.maxBothMotorOutputPercent) )    );
            
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
        if (getAdjustConfirmStopwatchTime() >= Constants.adjustHighgoalConfirmMsTime) {
            // Read the latest XY and parse it into usable integer coordinates
            Robot.receiveJevoisDataSubsys.ReadAndParseXY();

            // Store the re-confirmed highgoal's XY coords and rect size in the backup* vars
            SaveBackupHighgoalXYRectSize();

            // Save that we have found a highgoal to follow
            setAdjustHighgoalFound(true);
            // Stop the robot from trying to confirm a consistently-time highgoal
            Robot.stopAdjustConfirmComponent = true;
        }
    }
    //======================================================================================================
    // Sub-function
    public void TryToConfirmHighgoalForAdjusting() {
        // If the robot has NOT been trying to confirm a consistent highgoal for the max time allowed
        if (getAdjustConfirmStopwatchTime() < Constants.adjustConfirmStopwatchMsTimeout) {
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
            Robot.stopAdjustConfirmComponent = true;
        }
    }
    //#endregion    --------------END OF ADJUST-CONFIRM-FUNCTIONS------------------------------------
    //#region   --------------BEGINNING OF ADJUST-FACE-FUNCTIONS------------------------------------
    // Sub-function
    public boolean IsHighgoalXCoordWithinAdjustFaceThreshold() {
        // If the highgoal's distance from the x axis' center is within threshold
        if (    (Robot.xCoord >= ((Constants.jevoisCamWidth/2) - Constants.adjustFaceXCoordThreshold)) && 
                (Robot.xCoord <= ((Constants.jevoisCamWidth/2) + Constants.adjustFaceXCoordThreshold))    ) {
            return true;
        } else {
            return false;
        }
    }
    //======================================================================================================
    // Sub-function
    public boolean IsAdjustFaceHighgoalTimedOut() {
        if (    (   System.currentTimeMillis() - getAdjustFaceHighgoalStartTime()  ) > (Constants.adjustFaceHighgoalTimeMax)   ) {
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

        if (getAdjustFaceStopwatchTime() >= Constants.adjustFaceMsTimeThreshold) {
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
        if (    ( Robot.distFromHighgoal <= Constants.highgoalAdjustDistMax ) &&
        (Robot.distFromHighgoal >= Constants.highgoalAdjustDistMin)    ) {
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
            Robot.driveSubsystem.setLeftMotorValue(     (Constants.leftMotorDirFlip) * ( (1) * (Constants.maxBothMotorOutputPercent) )    );
            Robot.driveSubsystem.setRightMotorValue(     (Constants.rightMotorDirFlip) * ( (1) * (Constants.maxBothMotorOutputPercent) )    );
            
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
        if (! Robot.stopAdjustConfirmComponent) {
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
}