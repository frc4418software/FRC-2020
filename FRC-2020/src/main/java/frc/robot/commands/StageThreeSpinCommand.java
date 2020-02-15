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

//Will spin the control panel to a specific color

public class StageThreeSpinCommand extends CommandBase {
  /**
   * Creates a new StageThreeSpinCommand.
   */
  private String gameData;
  private String[] colorArray = new String[] { "Y", "R", "G", "B" };
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
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    while((gameData.length() > 0) && (gameData.equals("Y") || gameData.equals("B") || gameData.equals("R") || gameData.equals("G"))) {
      if (colorArray[Arrays.asList(colorArray).indexOf(gameData)].equals("G")) {
        desiredColor = "Y";
      } if (colorArray[Arrays.asList(colorArray).indexOf(gameData)].equals("B")) {
        desiredColor = "R";
      } else {
        desiredColor = colorArray[Arrays.asList(colorArray).indexOf(gameData) + 2];
      }
      Robot.controlPanelManipulatorSubsystem.setBrakemodeWheel(false);
      while (!Robot.color.equals(desiredColor)) {
        Robot.controlPanelManipulatorSubsystem.SetMotor(20);        
      }
      break;
    }
    Robot.controlPanelManipulatorSubsystem.SetMotor(0);  
    Robot.controlPanelManipulatorSubsystem.setBrakemodeWheel(true);
    

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
