/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;

public class VisionCommand extends CommandBase {

  public VisionCommand() {
    addRequirements(Robot.visionSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    Robot.visionSubsystem.Init();   // intialize serial port and related comms
  }



  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    Robot.visionSubsystem.ExtractXandY();   // get EITHER the x or y coord of the ball from every received string
  }



  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    Robot.visionSubsystem.Cleanup();    // cleanup serial connection and related comms
  }



  // Returns true WHEN the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
