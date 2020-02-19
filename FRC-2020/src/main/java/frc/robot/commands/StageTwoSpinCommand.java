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

  //creates an array of all the possible colors
  private String[] colorArray = new String[] { "Y", "R", "G", "B" };

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
    //while the color sensor has not seen at least one of the colors more than 6 times:
    while(Robot.colorCount[0] <= 6.0 || Robot.colorCount[1] <= 6.0 || Robot.colorCount[2] <= 6.0
        || Robot.colorCount[3] <= 6.0) {
      // have the motor contineously spin
      Robot.controlPanelManipulatorSubsystem.SetMotor(20);
      // for each of the colors, if they are equal to the value that is sensed, add to
      // the count for that color
      for (int i = 0; i <= 4; i++) {
        if (Robot.color.equals(colorArray[i])) {
          Robot.colorCount[i]++;
        }
      }
    }

    // stops the motor
    Robot.controlPanelManipulatorSubsystem.SetMotor(0);
    // sets the counts back to zero
    for (int i = 0; i <= 4; i++)
      Robot.colorCount[i] = 0;
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
