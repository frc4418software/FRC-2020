/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class VisionCommand extends CommandBase {

  public VisionCommand() {
    addRequirements(Robot.visionSubsystem);
    addRequirements(Robot.getVisionDataSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Intialize serial port and related comms
    Robot.getVisionDataSubsystem.Init();
  }



  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (Robot.visionSubsystem.getTrackingMode()) {
      case 0:
        // Tracking and driving towards the ball
        Robot.getVisionDataSubsystem.BallExtractXandY();
        Robot.visionSubsystem.DriveTowardsBall();
        break;
      case 1:
        // Tracking and aligning robot with high-goal
        Robot.getVisionDataSubsystem.GoalExtractXandY();
        if (Robot.visionSubsystem.getAlignStage() != 3) { 
          Robot.visionSubsystem.AlignToHighgoal(); 
        } else {
          Robot.visionSubsystem.setTrackingMode(2);
        }
        break;
      case 2:
        // Shoot ball into high-goal once aligned
        //TODO shoot ball into high-goal
        break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.getVisionDataSubsystem.Cleanup();
  }
}