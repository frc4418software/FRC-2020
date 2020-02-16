/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class JevoisCommand extends CommandBase {

  public JevoisCommand() {
    addRequirements(Robot.jevoisSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.jevoisSubsystem.Init();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    try {
      System.out.println(Robot.jevoisSubsystem.ReadString());
    }
    catch(Exception e) {
      System.out.println("Error");
    }
    
    Timer.delay(0.005);
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
