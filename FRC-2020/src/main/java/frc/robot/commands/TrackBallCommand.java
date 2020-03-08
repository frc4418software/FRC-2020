/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class TrackBallCommand extends CommandBase {

  public TrackBallCommand() {
    addRequirements(Robot.visionSubsystem);
    addRequirements(Robot.getVisionDataSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.visionSubsystem.setLockedOntoBall(false);
  }



  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Tracking and driving towards the ball
    Robot.getVisionDataSubsystem.BallExtractXandY();
    if (Robot.visionSubsystem.CheckValidBall()) {
      Robot.visionSubsystem.setLockedOntoBall(true);
    }
    if (Robot.visionSubsystem.getLockedOntoBall()) {
      Robot.visionSubsystem.DriveTowardsBall();
    }

    //TODO Task: Write code for intaking ball after confirming that it is within range
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }
}