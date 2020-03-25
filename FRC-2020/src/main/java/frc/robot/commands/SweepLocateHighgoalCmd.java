/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class SweepLocateHighgoalCmd extends CommandBase {
  /**
   * Creates a new SweepLocateHighgoalCmd
   */
  public SweepLocateHighgoalCmd() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getJevoisDataSubsys, 
                    Robot.visionHighgoalSubsys,
                    Robot.driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putString(Constants.visDeployCodeStatus, "INITIALIZING SweepLocateHighgoalCmd");

    //Robot.getJevoisDataSubsys.InitSerialPort();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // TODO WRITE code to make surveyMode chosen by joystick, instead of 1 as default for robot at leftmost position
    Robot.visionHighgoalSubsys.SurveyTurnForHighgoal(1);
    SmartDashboard.putString(Constants.visDeployCodeStatus, "EXECUTING SweepLocateHighgoalCmd");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      SmartDashboard.putString(Constants.visDeployCodeStatus, "INTERRUPTED SweepLocateHighgoalCmd");
    } else {
      SmartDashboard.putString(Constants.visDeployCodeStatus, "FINISHED SweepLocateHighgoalCmd");
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Robot.isSweepFinished;
  }
}