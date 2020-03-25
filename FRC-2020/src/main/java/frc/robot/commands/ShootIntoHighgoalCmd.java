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

public class ShootIntoHighgoalCmd extends CommandBase {

  // LITERALLY COPY AND PASTED FROM THE SEMIAUTOFIRECOMMAND THAT BEN WROTE XD


  /**
   * Creates a new ShootIntoHighgoalCmd
   */
  public ShootIntoHighgoalCmd() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getJevoisDataSubsys,
                    Robot.visionHighgoalSubsys,
                    Robot.driveSubsystem,
                    Robot.manipulatorsubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putString("CMD Init", "ShootIntoHighgoalCmd");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.manipulatorsubsystem.semiAutoFire(true, Constants.shootLoadDelayTime);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop manipulator motors and reset timers
    Robot.manipulatorsubsystem.StopAndResetManipulator();
    // Reset the JeVois servo pitch angle
    Robot.visionHighgoalSubsys.ResetJevoisServo();
    // Print to smartdash that the "shoot" stage of highgoal-vision is done
    SmartDashboard.putString("CMD Done", "ShootIntoHighgoalCmd");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Is based on the fact that manipulator motors' speed are ready for ball once loading phase is finished
    return Robot.manipulatorsubsystem.getStopShootLoadDelay();
  }
}