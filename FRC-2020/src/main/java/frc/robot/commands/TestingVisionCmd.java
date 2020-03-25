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
import frc.robot.RobotContainer;

public class TestingVisionCmd extends CommandBase {
  public TestingVisionCmd() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getJevoisDataSubsys, Robot.visionHighgoalSubsys, Robot.testingVisionSubsys);
  }

  @Override
  public void initialize() {
    SmartDashboard.putString(Constants.visTestCodeStatus, "INITIALIZED TestingVisionCmd");
    
    // Robot.getJevoisDataSubsys.InitJevois();
  }

  @Override
  public void execute() {
    // Robot.getJevoisDataSubsys.ReadAndParseXYSize();

    double leftJoyXAxis = (((RobotContainer.getXboxOneLeftJoyYAxis() + 1.0) / (1.0 + 1.0))
                          * 1.0);

    Robot.visionHighgoalSubsys.setPitchAngle(leftJoyXAxis * 180.0);
    
    SmartDashboard.putString(Constants.visTestCodeStatus, "EXECUTING TestingVisionCmd");
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      SmartDashboard.putString(Constants.visTestCodeStatus, "INTERRUPTED TestingVisionCmd");
    } else {
      SmartDashboard.putString(Constants.visTestCodeStatus, "FINISHED TestingVisionCmd");
    }
  }
}
