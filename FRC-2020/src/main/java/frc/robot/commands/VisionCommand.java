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
    // Get EITHER the x or y coord of the ball from every received string
    Robot.getVisionDataSubsystem.ExtractXandY();

    if (Robot.visionSubsystem.getTrackingMode() == 0) {
      Robot.visionSubsystem.DriveTowardsBall();
    } else if (Robot.visionSubsystem.getTrackingMode() == 1) {
      Robot.visionSubsystem.AlignWithHighgoal();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.getVisionDataSubsystem.Cleanup();
  }
}