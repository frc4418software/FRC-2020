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

public class TestingVisionCmd extends CommandBase {
  public TestingVisionCmd() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getJevoisDataSubsys, Robot.visionHighgoalSubsys, Robot.testingVisionSubsys);
  }

  @Override
  public void initialize() {
    SmartDashboard.putString("TESTING VISION", "STARTED");
    // Robot.getJevoisDataSubsys.InitJevois();
    Robot.testingVisionSubsys.setPitchAngle(70.0);
  }

  @Override
  public void execute() {
    // Robot.getJevoisDataSubsys.ReadAndParseXYSize();
    if (Robot.testingVisionSubsys.getPitchAngle() != 180.0) {
      Robot.testingVisionSubsys.setPitchAngle(180.0);
    }

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (Robot.testingVisionSubsys.getPitchAngle() != 0.0) {
      Robot.testingVisionSubsys.setPitchAngle(0.0);
    }

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putString("TESTING CODE", "FINISHED");
  }
}
