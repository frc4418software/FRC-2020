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

public class OutputAllDataCommand extends CommandBase {
  /**
   * Creates a new OutputAllDataCommand.
   */
  public OutputAllDataCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //executed throughout match, puts all of the data from the motors and sensors on to smartdashboard
    SmartDashboard.putNumber("Drive Left Drive Value", Robot.driveSubsystem.getLeftDriveValue());
    SmartDashboard.putNumber("Drive Right Drive Value", Robot.driveSubsystem.getRightDriveValue());
    SmartDashboard.putNumber("Drive Gyro Value", Robot.driveSubsystem.getGyroValue());
    SmartDashboard.putNumber("Drive Left Encoder", Robot.driveSubsystem.getLeftDriveEncoder());
    SmartDashboard.putNumber("Drive Right Encoder", Robot.driveSubsystem.getRightDriveEncoder());
    SmartDashboard.putNumber("Drive Accel X", Robot.driveSubsystem.getDriveAccelX());
    SmartDashboard.putNumber("Drive Accel Y", Robot.driveSubsystem.getDriveAccelY());
    SmartDashboard.putNumber("Drive Accel Z", Robot.driveSubsystem.getDriveAccelZ());
    SmartDashboard.putNumber("Front Distance", Robot.driveSubsystem.getFrontDriveDistance());
    SmartDashboard.putNumber("Back Distance", Robot.driveSubsystem.getBackDriveDistance());
    SmartDashboard.putNumberArray("Color Count (Y, R, G, B)", Robot.colorCount);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
