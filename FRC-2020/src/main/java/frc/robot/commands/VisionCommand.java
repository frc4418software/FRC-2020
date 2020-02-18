/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;

public class VisionCommand extends CommandBase {

  public VisionCommand() {
    addRequirements(Robot.visionSubsystem);
  }



  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }



  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //#region Test serial connection from JeVois to roboRio
    try {
      Robot.visionSubsystem.ReadString();
      System.out.println(Robot.visionSubsystem.getReceivedString());
    }
    catch(NullPointerException npe) {
      System.out.println("NullPointerException: Did not read string from JeVois correctly");
    }
    //#endregion


    // Robot.visionSubsystem.ReadString();
    // if (Robot.visionSubsystem.getReceivedString() != "nah") {

    //   try {
    //     if (Robot.visionSubsystem.getReceivedString().charAt(0) == 'x') {
    //       Robot.visionSubsystem.setBallXcenter(
    //         Integer.parseInt(Robot.visionSubsystem.getReceivedString().substring(1))
    //                                                   );  
    //     }
  
    //     else if (Robot.visionSubsystem.getReceivedString().charAt(0) == 'y') {
    //       Robot.visionSubsystem.setBallYcenter(
    //         Integer.parseInt(Robot.visionSubsystem.getReceivedString().substring(1))
    //                                                   );
    //     }
    //   }
    //   catch (NumberFormatException nfe) {
    //     System.out.println("NumberFormatException: Did not receive string from JeVois correctly");
    //   }
    //   catch (NullPointerException npe) {
    //     System.out.println("NullPointerException: Did not receive string from JeVois correctly");
    //   }

    // }
  }



  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.visionSubsystem.Cleanup();
  }



  // Returns true WHEN the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
