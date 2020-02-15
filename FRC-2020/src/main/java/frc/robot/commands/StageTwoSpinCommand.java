/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

//will spin the Control Panel 3 times

public class StageTwoSpinCommand extends CommandBase {
  /**
   * Creates a new StageTwoSpinCommand.
   */
  private String[] colorArray = new String[] { "Y", "R", "G", "B" };
  private int[] colorCount = new int[4];

  public StageTwoSpinCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.colorSensorSubsystem);
    addRequirements(Robot.controlPanelManipulatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    while(colorCount[0]<=6 || colorCount[1]<=6 || colorCount[2]<=6 || colorCount[3]<=6) {
      Robot.controlPanelManipulatorSubsystem.SetMotor(20); 
      for(int i =0; i<=4; i++) {
        if(Robot.color.equals(colorArray[i])) {
          colorCount[i]++;
        }
      }
    }
    Robot.controlPanelManipulatorSubsystem.SetMotor(0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.controlPanelManipulatorSubsystem.SetMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
