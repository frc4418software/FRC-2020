/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class FaceHighgoalCmd extends CommandBase {
  /**
   * Creates a new FaceHighgoalCmd
   */
  public FaceHighgoalCmd() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getJevoisDataSubsys,
                    Robot.visionHighgoalSubsys,
                    Robot.driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putString("CMD Init-ed", "FaceHighgoalCmd");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.visionHighgoalSubsys.FaceHighgoalTimedThreshold();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putString("CMD Done", "FaceHighgoalCmd");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Robot.visionHighgoalSubsys.getIsFaceHighgoalComplete();
  }
}