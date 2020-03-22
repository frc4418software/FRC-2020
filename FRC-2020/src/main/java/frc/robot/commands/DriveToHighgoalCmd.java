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

public class DriveToHighgoalCmd extends CommandBase {
  /**
   * Creates a new DriveToHighgoalCmd
   */
  public DriveToHighgoalCmd() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getJevoisDataSubsys,
                    Robot.visionHighgoalSubsys,
                    Robot.driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putString("CMD Init-ed", "DriveToHighgoalCmd");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.visionHighgoalSubsys.DriveUntilCloseToHighgoal();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Reset drive detection to get close enough to highgoal using dist thresh AND/OR highgoal rect size
    Robot.visionHighgoalSubsys.setHasReachedHighgoal(false);

    SmartDashboard.putString("CMD Done", "DriveToHighgoalCmd");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Robot.visionHighgoalSubsys.getHasReachedHighgoal();
  }
}