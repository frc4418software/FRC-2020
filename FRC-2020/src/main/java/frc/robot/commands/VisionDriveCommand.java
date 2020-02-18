/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class VisionDriveCommand extends CommandBase {

  public VisionDriveCommand() {
    addRequirements(Robot.visionDriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Perform necessary calculations for motor speeds of each motor
    Robot.visionDriveSubsystem.CalculateCoordError();
    Robot.visionDriveSubsystem.CalculateMotorPivot();

    // Set and actually drive towards a single ball
    Robot.visionDriveSubsystem.DriveTowardsBall();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop the motors
    Robot.driveSubsystem.stopDrive();
  }

  // Returns true WHEN the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}