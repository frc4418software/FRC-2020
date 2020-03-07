/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class TrackHighgoalCommand extends CommandBase {

  public TrackHighgoalCommand() {
    addRequirements(Robot.visionSubsystem);
    addRequirements(Robot.getVisionDataSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Intialize serial port and related comms
    
    //TODO tracking higoal init ignored
    //Robot.getVisionDataSubsystem.Init();
  }



  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Tracking and aligning robot with high-goal
    Robot.getVisionDataSubsystem.GoalExtractXandY();
    
    //DEBUG
    SmartDashboard.putString("Track higoal", "extracting goal xy");

    /*
    if (Robot.visionSubsystem.getAlignStage() != 3) { 
      Robot.visionSubsystem.AlignToHighgoal();
      
      //DEBUG
      SmartDashboard.putString("Track Highgoal", "aligning to highgoal");
    } else {
      Robot.visionSubsystem.setTrackingMode(2);
    }

    if (Robot.visionSubsystem.getTrackingMode() == 2) {
      Robot.manipulatorsubsystem.semiAutoFire();
      
      //DEBUG
      SmartDashboard.putString("Track Highgoal", "firing ball into highgoal");
    }
    */
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //TODO higoal cleanup ignored
    //Robot.getVisionDataSubsystem.Cleanup();
    
    //DEBUG
    //SmartDashboard.putString("Track Highgoal", "cleaning up");
  }
}