/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class HSVDetectBallCommand extends CommandBase {

  public HSVDetectBallCommand() {
    addRequirements(Robot.hsvDetectBallSubsystem);
  }



  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }



  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //#region Test if receiving strings from JeVois correctly
    // try {
    //   System.out.println(Robot.jevoisSubsystem.ReadString());
    // }
    // catch(Exception e) {
    //   System.out.println("Error");
    // }
    
    // Timer.delay(0.005);
    //#endregion
    Robot.hsvDetectBallSubsystem.ReadString();
    if (Robot.hsvDetectBallSubsystem.getReceivedString() != "nah") {

      try {
        if (Robot.hsvDetectBallSubsystem.getReceivedString().charAt(0) == 'x') {
          Robot.hsvDetectBallSubsystem.setBallXcenter(
            Integer.parseInt(Robot.hsvDetectBallSubsystem.getReceivedString().substring(1))
                                                      );  
        }
  
        else if (Robot.hsvDetectBallSubsystem.getReceivedString().charAt(0) == 'y') {
          Robot.hsvDetectBallSubsystem.setBallYcenter(
            Integer.parseInt(Robot.hsvDetectBallSubsystem.getReceivedString().substring(1))
                                                      );
        }
      }
      catch (NumberFormatException nfe) {
        System.out.println("JEVOIS NumberFormatException: " + nfe.getMessage());
      }

    }
  }



  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.hsvDetectBallSubsystem.Cleanup();
  }



  // Returns true WHEN the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
