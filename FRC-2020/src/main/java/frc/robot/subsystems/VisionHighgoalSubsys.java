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
    private boolean isSweepFinished = false;
    public boolean getIsSweepFinished() {
        return this.isSweepFinished;
    }
    public void setIsSweepFinished(boolean isSweepFinished) {
		this.isSweepFinished = isSweepFinished;
	}
    //#endregion ====================END OF SWEEP VARIABLES SECTION=======================================

    //#region   ====================BEGINNING OF CONFIRM VARIABLES SECTION=======================================
    
    //#endregion    ====================END OF CONFIRM VARIABLES SECTION=======================================



    //#region   ====================BEGINNING OF SWEEP FUNCTIONS SECTION=======================================
    // Sub-function
    public void DelayedTurnWithStop(int msDelayTime, boolean turningLeft) {
        if (turningLeft) {
            // Set motors to turn to face its left
            Robot.driveSubsystem.setLeftMotorValue(-50 * getSweepLeftMotorMaxPercent());
            Robot.driveSubsystem.setRightMotorValue(50 * getSweepRightMotorMaxPercent());
        } else {
            // Set motors to turn to face its right
            Robot.driveSubsystem.setLeftMotorValue(50 * getSweepLeftMotorMaxPercent());
            Robot.driveSubsystem.setRightMotorValue(-50 * getSweepRightMotorMaxPercent());
        }

        try {
            TimeUnit.MILLISECONDS.sleep(msDelayTime);
        }
        catch (InterruptedException ie) {
            SmartDashboard.putString("TurnDelayError", "InterruptedException: check delay code in VisionHighgoalSubsys");
        }

        // TODO figure out how to properly stop the robot's drive motors
        Robot.driveSubsystem.setLeftMotorValue(0);
        Robot.driveSubsystem.setRightMotorValue(0);
    }
    public void SurveyTurnForHighgoal(int surveyMode) {
        switch (surveyMode) {
            case 1:
                // Surveying mode for when the robot is on the leftmost position
                DelayedTurnWithStop(getMsTimeForFullSideRotation(), false);
                break;
            case 2:
                // Surveying mode for when the robot is on the centermost position
                DelayedTurnWithStop(getMsTimeForCenterSideRotation(), false);
                break;
            case 3:
                // Surveying mode for when the robot is on the rightmost position
                DelayedTurnWithStop(getMsTimeForFullSideRotation(), true);
                break;
        }

        setIsSweepFinished(true);
    }
    //#endregion    ====================END OF SWEEP FUNCTIONS SECTION=======================================

    //#region   ====================BEGINNING OF CONFIRM FUNCTIONS SECTION=======================================

    //#endregion    ====================END OF CONFIRM FUNCTIONS SECTION=======================================

}