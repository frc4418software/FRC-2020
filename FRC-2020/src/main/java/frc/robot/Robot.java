/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ManipulatorSubsystem;
import frc.robot.subsystems.ReceiveJevoisDataSubsys;
import frc.robot.subsystems.VisionHighgoalSubsys;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  
  //#region ===================BEGINNING OF ROBOT SUBSYSTEMS====================
  public static DriveSubsystem driveSubsystem = new DriveSubsystem();
  public static ClimbSubsystem climbSubsystem = new ClimbSubsystem();
  public static ManipulatorSubsystem manipulatorsubsystem = new ManipulatorSubsystem();
  
  public static ReceiveJevoisDataSubsys receiveJevoisDataSubsys = new ReceiveJevoisDataSubsys();
  public static VisionHighgoalSubsys visionHighgoalSubsys = new VisionHighgoalSubsys();
  //#endregion  =======================END OF ROBOT SUBSYSTEMS======================
  
  //#region ==================BEGINNING OF VISION ROBOT VARIABLES======================
  //#region -----------BEGINNING OF *JEVOIS RECEIVE* ROBOT VARS----------------
  public static String 
        jevoisString;

  public static int 
        xCoord, 
        yCoord, 
        rectSize;
  //#endregion  ------------END OF *JEVOIS RECEIVE* ROBOT VARS----------------
  
  //#region -----------BEGINNING OF MULTI-COMPONENT ROBOT VARS----------------
  public static int
        backupHighgoalXCoord, 
        backupHighgoalYCoord,
        distFromHighgoal,
        backupHighgoalRectSize;

  public static boolean 
        consistentHighgoalFound = false;
  //#endregion  -----------END OF MULTI-COMPONENT ROBOT VARS----------------

  //#region -----------BEGINNING OF SWEEP ROBOT VARS----------
  public static boolean
          isSweepFinished = false;                    // TODO CONFIG whether the robot should go through the "sweep" stage or not
  //#endregion  -----------END OF SWEEP ROBOT VARS------------

  //#region -----------BEGINNING OF ADJUST ROBOT VARS----------
  public static boolean 
          stopAdjustConfirmComponent = false;      // TODO CONFIG if robot defaultly adjusts-face using a re-confirmation of the highgoal
  //#endregion  -----------END OF ADJUST ROBOT VARS------------
  //#endregion  =====================END OF VISION ROBOT VARIBLES=======================
  
  
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
