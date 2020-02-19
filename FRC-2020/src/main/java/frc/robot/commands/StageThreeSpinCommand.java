/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Arrays;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

//Will spin the control panel to a specific color: note that the motor MUST spin clockwise to work

public class StageThreeSpinCommand extends CommandBase {
  /**
   * Creates a new StageThreeSpinCommand.
   */

  //creates string for the game data
  private String gameData;

  //creates an array of all the possible colors
  private String[] colorArray = new String[] { "Y", "R", "G", "B" };

  //creates a string for the desired color of the system
  private String desiredColor;

  public StageThreeSpinCommand() {
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
    //sets the game data variable to the data that is given by driverstation
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    //while the game data exists and is equal to one of the desired colors
    while((gameData.length() > 0) && ((gameData.equals("Y") || gameData.equals("B") || gameData.equals("R") || gameData.equals("G")))) {
      //if the color is G or B, then their corresponding colors to be turned to are Y and R respectively
      if (colorArray[Arrays.asList(colorArray).indexOf(gameData)].equals("G")) {
        desiredColor = "Y";
      } if (colorArray[Arrays.asList(colorArray).indexOf(gameData)].equals("B")) {
        desiredColor = "R";
      } else { // otherwise, the corresponding colors are the index of the origonal color plus 2
        desiredColor = colorArray[Arrays.asList(colorArray).indexOf(gameData) + 2];
      }

      //while the robot color that is sensed is not the desired color, the manipulator will keep spinning
      while (!Robot.color.equals(desiredColor)) {
        Robot.controlPanelManipulatorSubsystem.SetMotor(20);        
      }
      break;
    }
    //once the process is finished, the motor of the manipulator is set to zero
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
