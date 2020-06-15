/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;


public class VisionHighgoalSubsys extends SubsystemBase {
    //#region   ====================BEGINNING OF SUBSYS CONSTRUCTOR===========================================
    private Servo jevoisPitchServo;
    
    public VisionHighgoalSubsys() {
        // JeVois pitch servo setup
        jevoisPitchServo = new Servo(Constants.JEVOIS_PITCH_SERVO_PWM_PIN);
        jevoisPitchServo.setBounds(2.4, 0., 0., 0., 0.9);
    }
    //#endregion
    
    //#region   ====================BEGINNING OF SWEEP VARIABLES SECTION=======================================
    private boolean startSweepTimer = false;
    public boolean isStartSweepTimer() {
        return this.startSweepTimer;
    }
    public void setStartSweepTimer(boolean startSweepTimer) {
		this.startSweepTimer = startSweepTimer;
	}
    //#endregion ====================END OF SWEEP VARIABLES SECTION=======================================

    //#region   ====================BEGINNING OF CONFIRM VARIABLES SECTION=======================================
    
                //TODO WRITE needed variables for adjusting the perceived brightness/lighting of the JeVois
    
    
    //=======================================================================
    private boolean startConfirmTimer = false;
    public boolean isStartConfirmTimer() {
        return startConfirmTimer;
    }
    public void setStartConfirmTimer(boolean startConfirmTimer) {
        this.startConfirmTimer = startConfirmTimer;
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
    private boolean startFaceTimer = false;
    public boolean isStartFaceTimer() {
        return startFaceTimer;
    }
    public void setStartFaceTimer(boolean startFaceTimer) {
        this.startFaceTimer = startFaceTimer;
    }
    //=======================================================================
    private boolean faceHighgoalComplete = false;
    public boolean isFaceHighgoalComplete() {
        return this.faceHighgoalComplete;
    }
    public void setFaceHighgoalComplete(boolean faceHighgoalComplete) {
		this.faceHighgoalComplete = faceHighgoalComplete;
    }
    //=======================================================================
    private boolean facingHighgoal = false;           // TODO WRITE (IF NEEDED) what to do if we did or did NOT successfully "face" before timeout
    public boolean isFacingHighgoal() {
        return facingHighgoal;
    }
    public void setFacingHighgoal(boolean facingHighgoal) {
        this.facingHighgoal = facingHighgoal;
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
    private boolean stopAdjustCmd = false;
    public boolean getStopAdjustCmd() {
        return this.stopAdjustCmd;
    }
    public void setStopAdjustCmd(boolean stopAdjustCmd) {
		this.stopAdjustCmd = stopAdjustCmd;
    }
    //=======================================================================
    private boolean adjustServoPrepared = false;
    public boolean isAdjustServoPrepared() {
        return adjustServoPrepared;
    }
    public void setAdjustServoPrepared(boolean adjustServoPrepared) {
        this.adjustServoPrepared = adjustServoPrepared;
    }
    
    //#endregion    -----------END OF ADJUST-MULTI-PART-VARIABLES-----------------
    //#region   -------------BEGINNING OF ADJUST-CONFIRM-VARIABLES--------------
    private boolean startAdjustConfirmTimer = false;
    public boolean isStartAdjustConfirmTimer() {
        return startAdjustConfirmTimer;
    }
    public void setStartAdjustConfirmTimer(boolean startAdjustConfirmTimer) {
        this.startAdjustConfirmTimer = startAdjustConfirmTimer;
    }
    //=======================================================================
    private boolean adjustHighgoalFound = false;
    public boolean getAdjustHighgoalFound() {
        return this.adjustHighgoalFound;
    }
    public void setAdjustHighgoalFound(boolean adjustHighgoalFound) {
		this.adjustHighgoalFound = adjustHighgoalFound;
    }
    //#endregion    -------------END OF ADJUST-CONFIRM-VARIABLES--------------
    //#region   -------------BEGINNING OF ADJUST-FACE-VARIABLES--------------
    private boolean startAdjustFaceTimer = false;
    public boolean isStartAdjustFaceTimer() {
        return this.startAdjustFaceTimer;
    }
    public void setStartAdjustFaceTimer(boolean startAdjustFaceTimer) {
		this.startAdjustFaceTimer = startAdjustFaceTimer;
    }
    //=======================================================================
    private boolean adjustFacingHighgoal = false;
    public boolean isAdjustFacingHighgoal() {
        return adjustFacingHighgoal;
    }
    public void setAdjustFacingHighgoal(boolean adjustFacingHighgoal) {
        this.adjustFacingHighgoal = adjustFacingHighgoal;
    }
    //=======================================================================
    private boolean adjustFaceComplete = false;                     // TODO WRITE (IF NEEDED) if vision algo adapts with info that we have adjust-faced successfully
    public boolean isAdjustFaceComplete() {
        return this.adjustFaceComplete;
    }
    public void setAdjustFaceComplete(boolean adjustFaceComplete) {
		this.adjustFaceComplete = adjustFaceComplete;
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





    //#region   ||||||||||||||||||||||BEGINNING OF MULTI-COMPONENT FUNC=TIONS||||||||||||||||||||||||||||||
    public void setPitchAngle(double degrees) {
        if (degrees > Constants.jevoisServoMaxPos) {
            jevoisPitchServo.setAngle(Constants.jevoisServoMaxPos);
        } else if (degrees < Constants.jevoisServoMinPos) {
            jevoisPitchServo.setAngle(Constants.jevoisServoMinPos);
        } else {
            jevoisPitchServo.setAngle(degrees);
        }
    }
    //======================================================================================================
    public double getPitchAngle() {
        return jevoisPitchServo.getAngle();
    }
    //======================================================================================================
    public boolean IsHighgoalOnLeftOfXCenter() {
        if (Robot.xCoord >= (Constants.jevoisCamWidth/2)) {
            return false;
        } else {
            return true;
        }
    }
    //======================================================================================================
    public void SaveBackupHighgoalXYRectSize() {
        // Set the backup highgoal coord to the parsed, found, and usable integer coordinates
        Robot.backupHighgoalXCoord = Robot.xCoord;
        Robot.backupHighgoalYCoord = Robot.yCoord;

        // Also set the backup highgoal rect size to the parsed, found, and usable integer size
        Robot.backupHighgoalRectSize = Robot.rectSize;
    }
    //======================================================================================================
    public void UpdateDistFromHighgoal() {
        // TODO WRITE how to get ultrasonic sensor input
        //setHighCenterPosDistFromHighgoal(highCenterPosDistFromHighgoal);

        // TODO (IF NEEDED) WRITE math here for setting true dist from highgoal using multiple dist sensors
        //setTrueHighgoalDist(getHighCenterPosDistFromHighgoal());
    }
    //======================================================================================================
    public void StopResetVisionTimer() {
        Robot.visionTimer.stop();
        Robot.visionTimer.reset();
    }
    //#endregion    ||||||||||||||||||||||END OF MULTI-COMPONENT FUNCTIONS||||||||||||||||||||||||||||||

    //#region   ====================BEGINNING OF SWEEP FUNCTIONS SECTION=======================================
    
    public void StopwatchTurnAndStop(double secDelayTime, boolean turningLeft) {
        // If sweep stopwatch is not started
        if (isStartSweepTimer() == false) {
            Robot.visionTimer.start();
            setStartSweepTimer(true);
        // If sweep stopwatch is started
        }

        if (isStartSweepTimer() == true) {
            if (turningLeft) {
                // Set motors to turn to face its left
                Robot.driveSubsystem.setLeftMotorValue(     (Constants.leftMotorDirFlip) * ( (-1) )    );
                Robot.driveSubsystem.setRightMotorValue(    (Constants.rightMotorDirFlip) * ( (1) )    );
            } else {
                // Set motors to turn to face its right
                Robot.driveSubsystem.setLeftMotorValue(     (Constants.leftMotorDirFlip) * ( (50) )    );
                Robot.driveSubsystem.setRightMotorValue(    (Constants.rightMotorDirFlip) * ( (-50) )      );
            }
        }

        // If sweep duration is fulfilled
        if (Robot.visionTimer.get() >= secDelayTime) {
            Robot.driveSubsystem.stopDrive();

            // Reset the sweep stopwatch
            StopResetVisionTimer();
            setStartSweepTimer(false);

            // Confirm that the sweep has fulfilled its delay time
            Robot.isSweepFinished = true;
        }
    }
    //======================================================================================================
    public void SurveyTurnForHighgoal(int surveyMode) {
        switch (surveyMode) {
            case 1:
                // Surveying mode for when the robot is on the leftmost position
                StopwatchTurnAndStop(Constants.secTimeFullSideRotation, false);
                break;
            case 2:
                // Surveying mode for when the robot is on the centermost position
                StopwatchTurnAndStop(Constants.secTimeCenterSideRotation, false);
                break;
            case 3:
                // Surveying mode for when the robot is on the rightmost position
                StopwatchTurnAndStop(Constants.secTimeFullSideRotation, true);
                break;
        }
    }
    //#endregion    ====================END OF SWEEP FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF CONFIRM FUNCTIONS SECTION=======================================
    
    public void StopConfirmIfFound(boolean isConfirmStage, double timeoutThreshold) {
        // If there is a constant receiving of highgoal XY coords greater than the CONFIG confirm time
        if (Robot.visionTimer.get() >= timeoutThreshold) {
            // Read the latest XY and parse it into usable integer coordinates
            Robot.getJevoisDataSubsys.ReadAndParseXYSize();

            // Store the confirmed highgoal's XY coords and rect size in the backup* vars
            SaveBackupHighgoalXYRectSize();

            if (isConfirmStage) {
                // Reset the confirm timer
                StopResetVisionTimer();
                setStartConfirmTimer(false);

                // Save that we have found a highgoal to follow
                Robot.consistentHighgoalFound = true;
                // Stop the robot from trying to confirm a consistently-time highgoal
                setStopConfirmCmd(true);    
            } else {
                AdjustConfirmCompleteStopReset(true);
            }
            
        }
    }
    //======================================================================================================
    public void ConfirmHighgoalXYWithTimeout() {
        if (isStartConfirmTimer() == false) {
            Robot.visionTimer.start();
            setStartConfirmTimer(true);
        } else {
            // If the robot has NOT been trying to confirm a consistent highgoal for the max time allowed
            if (Robot.visionTimer.get() < Constants.secTimeConfirmTimeout) {
                // Reset the confirm timer to zero if the string is invalid
                if (! Robot.getJevoisDataSubsys.IsReceivedStringValidData()) {
                    Robot.visionTimer.reset();
                }

                // Stop trying to confirm highgoal IF a constant stream of XY highgoal coords was found
                StopConfirmIfFound(true, Constants.secTimeConfirmHighgoal);

            // If the robot has been trying to confirm a consistent highgoal for the max time allowed
            } else {
                // Stop and reset the confirm timer
                StopResetVisionTimer();
                setStartConfirmTimer(false);

                // Save that we have NOT found a highgoal to follow
                Robot.consistentHighgoalFound = false;
                // Stop the robot from trying to confirm a consistently-time highgoal
                setStopConfirmCmd(true);
            }
        }
    }
    //#endregion    ====================END OF CONFIRM FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF FACE FUNCTIONS SECTION=======================================
    
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
    
    public void UpdateBackupCoords() {
        // TODO WRITE math calculations for updating backup coords
        //setBackupConfirmedHighgoalXCoord(backupConfirmedHighgoalXCoord);
        //setBackupConfirmedHighgoalYCoord(backupConfirmedHighgoalYCoord);
    }
    //======================================================================================================
    
    public void DriveTurnToFaceHighgoal() {
        // If the robot has backup coords to use, and is by default set to face highgoal based on backup coords
        if (Constants.usingBackupCoordsAsDefault) {
            UpdateBackupCoords();
            TurnToHighgoalWithOtherDiffPercent(true);
        // If the robot does NOT (have backup coords AND default setting to face highgoal based on backup coords)
        } else {
            TurnToHighgoalWithOtherDiffPercent(false);
        }
    }
    //======================================================================================================
    public void FaceCompleteStopAndReset() {
        Robot.driveSubsystem.stopDrive();

        // Reset face timer
        StopResetVisionTimer();
        setStartFaceTimer(false);

        setFaceHighgoalComplete(true);
    }
    //======================================================================================================
    public void FaceHighgoalTimedThreshold() {
        if (isStartFaceTimer() == false) {
            Robot.visionTimer.start();
            setStartFaceTimer(true);
        }

        if (isStartFaceTimer() == true) {
            // If we have NOT timed out for trying to face highgoal
            if (Robot.visionTimer.get() < Constants.secTimeFaceTimeout) {
                if (Robot.visionTimer.get() >= Constants.secTimeFaceThreshold) {
                    setFacingHighgoal(true);
                    FaceCompleteStopAndReset();
                } else {
                    DriveTurnToFaceHighgoal();
    
                    if (! Robot.getJevoisDataSubsys.IsReceivedStringValidData() ||
                    (! IsHighgoalXCoordWithinFaceThreshold())  ) {
                        Robot.visionTimer.reset();
                    }
                }
            // If we have timed out for trying to face highgoal
            } else {
                setFacingHighgoal(false);
                FaceCompleteStopAndReset();
            }         
        }
    }
    //#endregion    ====================END OF FACE FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF DRIVE FUNCTIONS SECTION========================================
    
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

    //#region   ====================BEGINNING OF ADJUST FUNCTIONS SECTION======================================
    //#region   --------------BEGINNING OF ADJUST-CONFIRM-FUNCTIONS------------------------------------
    public void AdjustAimJevoisServo() {
        setPitchAngle(Constants.jevoisServoAdjustPos);
        Timer.delay(Constants.secTimeAdjustServoDelay);
    }
    //======================================================================================================
    public void ResetJevoisServo() {
        setPitchAngle(Constants.jevoisServoResetPos);
    }
    //======================================================================================================
    public void AdjustConfirmCompleteStopReset(boolean foundHighgoal) {
        // Reset the adjust-confirm timer
        StopResetVisionTimer();
        setStartAdjustConfirmTimer(false);

        // Save that we have found a highgoal to follow
        setAdjustHighgoalFound(foundHighgoal);
        // Stop the robot from trying to confirm a consistently-time highgoal
        Robot.stopAdjustConfirmComponent = true;
    }
    //======================================================================================================
    public void TryToConfirmHighgoalForAdjusting() {
        // Start the adjust-confirm timer for timing out and tracking confirm threshold
        if (isStartAdjustConfirmTimer() == false) {
            Robot.visionTimer.start();
            setStartAdjustConfirmTimer(true);
        }

        // If the adjust-confirm is started
        if (isStartAdjustConfirmTimer() == true) {
            // If the robot has NOT been trying to confirm a consistent highgoal for the max time allowed
            if (Robot.visionTimer.get() < Constants.secTimeAdjustConfirmTimeout) {
                // Reset the adjust-confirm timer if the string is invalid
                if (! Robot.getJevoisDataSubsys.IsReceivedStringValidData()) {
                    Robot.visionTimer.reset();
                }

                // Stop the robot from trying to confirm a highgoal if a constant stream of XY highgoal coords was found
                StopConfirmIfFound(false, Constants.secTimeAdjustConfirmTimeout);

            // If the robot has been trying to confirm a consistent highgoal for the max time allowed
            } else {
                AdjustConfirmCompleteStopReset(false);
            }
        }
    }
    //#endregion    --------------END OF ADJUST-CONFIRM-FUNCTIONS------------------------------------
    //#region   --------------BEGINNING OF ADJUST-FACE-FUNCTIONS------------------------------------
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
    public void StopResetCompleteAdjustFacing(boolean adjustFacingHighgoal) {
        // Stop the robot's drive motors
        Robot.driveSubsystem.stopDrive();

        StopResetVisionTimer();
        // Reset the adjust-face stopwatch
        setStartAdjustFaceTimer(false);

        setAdjustFacingHighgoal(adjustFacingHighgoal);

        // Indicate that we have finished adjust-facing the highgoal
        setAdjustFaceComplete(true);
    }
    //======================================================================================================
    public void AdjustFaceHighgoalTimedThreshold() {
        if (isStartAdjustFaceTimer() == false) {
            Robot.visionTimer.start();
            setStartAdjustFaceTimer(true);
        }

        if (isStartAdjustFaceTimer() == true) {
            // If we DID reach the threshold for adjust-facing
            if (Robot.visionTimer.get() >= Constants.secTimeAdjustFaceThreshold) {
                StopResetCompleteAdjustFacing(true);
                
            } else {
                // If adjust stage has NOT timed out for trying to re-face
                if (Robot.visionTimer.get() < Constants.secTimeAdjustFaceTimeout) {
                    DriveTurnToFaceHighgoal();
                    if (! Robot.getJevoisDataSubsys.IsReceivedStringValidData() ||
                    (! IsHighgoalXCoordWithinAdjustFaceThreshold())  ) {
                        Robot.visionTimer.reset();
                    }
                // If adjust stage has timed out for trying to re-face
                } else {
                    StopResetCompleteAdjustFacing(false);
                }
            }
        }
    }
    //#endregion    --------------END OF ADJUST-FACE-FUNCTIONS------------------------------------
    //#region   --------------BEGINNING OF ADJUST-DRIVE-FUNCTIONS--------------------------------
    public void CheckIfAdjustReachedHighgoal() {
        if (    ( Robot.distFromHighgoal <= Constants.highgoalAdjustDistMax ) &&
        (Robot.distFromHighgoal >= Constants.highgoalAdjustDistMin)    ) {
            setAdjustDrivedWithinHighgoal(true);
        } else {
            setAdjustDrivedWithinHighgoal(false);
        }
    }
    //======================================================================================================
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
        // Use JeVois servo to angle the JeVois view to prepare for adjusting
        if (isAdjustServoPrepared() == false) {
            AdjustAimJevoisServo();
        }

        // Once the JeVois has been angled properly, we can continue on with adjusting the robot before shooting into the highgoal
        if (isAdjustServoPrepared() == true) {
            // If the adjust-confirm component of the adjust stage is NOT complete
            if (Robot.stopAdjustConfirmComponent == false) {
                TryToConfirmHighgoalForAdjusting();
            }

            // If the adjust-confirm component of the adjust stage is complete
            if (Robot.stopAdjustConfirmComponent == true) {
                // If we did re-confirm a highgoal to use to re-face and re-drive-to
                if (getAdjustHighgoalFound()) {
                    // Re-face the highgoal
                    if (isAdjustFaceComplete() == false) {
                        AdjustFaceHighgoalTimedThreshold();
                    }

                    // Drive until we've reached the adjust-drive component thresholds
                    if (isAdjustFaceComplete() == true) {
                        StopAdjustCmdOnceDrivenWithinThresh();
                    }
                // If we did NOT re-confirm a highgoal to use to re-face
                } else {
                    StopAdjustCmdOnceDrivenWithinThresh();
                }
            }
        }
    }
    //#endregion    ====================END OF ADJUST-DRIVE-FUNCTIONS SECTION=======================================
}