/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ClearCommand;
import frc.robot.commands.DriveStraightCommand;
import frc.robot.commands.HighgoalVisionCmdGroup;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.SelectLaunch1Command;
import frc.robot.commands.SelectLaunch2Command;
import frc.robot.commands.SelectLaunch3Command;
import frc.robot.commands.SemiAutoFireCommand;
import frc.robot.commands.TestingVisionCmd;
import frc.robot.commands.ToggleArcadeDriveCommand;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //#region Create joysticks
  // Create joysticks
  private static final Joystick 
      X3D_LEFT = new Joystick(Constants.X3D_LEFT_JOYSTICK_ID),
      X3D_RIGHT = new Joystick(Constants.X3D_RIGHT_JOYSTICK_ID) ,
      GAMEPAD = new Joystick(Constants.GAMEPAD_JOYSTICK_ID),
      XBOX_ONE = new Joystick(Constants.XBOX_ONE_JOYSTICK_ID);              // FOR VISION TESTING AND FUN LOL
  //#endregion
                                
  //#region Get axis for specific functions
  // Get axis for specific functions
  public static double getLeftTankDriveAxis() {
    return X3D_LEFT.getRawAxis(Constants.LEFT_TANK_DRIVE_AXIS_ID);
  }
  public static double getRightTankDriveAxis() {
    return X3D_RIGHT.getRawAxis(Constants.RIGHT_TANK_DRIVE_AXIS_ID);
  }

  public static double getForwardArcadeDriveAxis() {
    return X3D_RIGHT.getRawAxis(Constants.FORWARD_ARCADE_DRIVE_AXIS_ID);
  }
  public static double getAngleArcadeDriveAxis() {
    return X3D_RIGHT.getRawAxis(Constants.ANGLE_ARCADE_DRIVE_AXIS_ID);
  }
  public static double getClimberAxis() {
    return GAMEPAD.getRawAxis(Constants.CLIMB_AXIS_ID);
  }

  // Get Xbox one left joystick axes
  public static double getXboxOneLeftJoyXAxis() {
    return XBOX_ONE.getRawAxis(Constants.XBOX_ONE_LEFT_JOY_X_AXIS_ID);
  }
  public static double getXboxOneLeftJoyYAxis() {
    return XBOX_ONE.getRawAxis(Constants.XBOX_ONE_LEFT_JOY_Y_AXIS_ID);
  }
  
  // Get Xbox one left and right trigger axes
  public static double getXboxOneLeftTrigAxis() {
    return XBOX_ONE.getRawAxis(Constants.XBOX_ONE_LEFT_TRIGGER_AXIS_ID);
  }
  public static double getXboxOneRightTrigAxis() {
    return XBOX_ONE.getRawAxis(Constants.XBOX_ONE_RIGHT_TRIGGER_AXIS_ID);
  }

  // Get Xbox one right joystick axes
  public static double getXboxOneRightJoyXAxis() {
    return XBOX_ONE.getRawAxis(Constants.XBOX_ONE_RIGHT_JOY_X_AXIS_ID);
  }
  public static double getXboxOneRightJoyYAxis() {
    return XBOX_ONE.getRawAxis(Constants.XBOX_ONE_RIGHT_JOY_Y_AXIS_ID);
  }
  //#endregion

  //#region Create and assign default buttons
  // Create and assign default buttons
  public static JoystickButton toggleArcadeDriveButton = new JoystickButton(X3D_RIGHT, Constants.TOGGLE_ARCADE_DRIVE_BUTOON_ID);
  public static JoystickButton driveStraightButton = new JoystickButton(X3D_RIGHT, Constants.DRIVE_STRAIGHT_BUTTON_ID);
  
  // Gamepad button B
  public static JoystickButton testCodeButton = new JoystickButton(GAMEPAD, Constants.TRACK_BALL_VISION_BUTTON_ID);
  
  // Gamepad button X
  public static JoystickButton visionTrackHighgoalButton = new JoystickButton(GAMEPAD, Constants.TRACK_HIGHGOAL_VISION_BUTTON_ID);

  public static JoystickButton launchButton = new JoystickButton(X3D_RIGHT, Constants.LAUNCH_BUTTON_ID);
  public static JoystickButton intakeButton = new JoystickButton(X3D_RIGHT, Constants.INTAKE_BUTTON_ID);
  public static JoystickButton clearButton = new JoystickButton(X3D_RIGHT, Constants.CLEAR_BUTTON_ID);
  public static JoystickButton selectButton1 = new JoystickButton(X3D_RIGHT, Constants.SELECT_BUTTON_1_ID);
  public static JoystickButton selectButton2 = new JoystickButton(X3D_RIGHT, Constants.SELECT_BUTTON_2_ID);
  public static JoystickButton selectButton3 = new JoystickButton(X3D_RIGHT, Constants.SELECT_BUTTON_3_ID);
  //#endregion

  //#region Robot Container
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }
  //#endregion

  //#region Button bindings
  public int selection;
  private void configureButtonBindings() {
    toggleArcadeDriveButton.whenPressed(new ToggleArcadeDriveCommand());
    driveStraightButton.whileHeld(new DriveStraightCommand());
    
    // Run test code when gamepad button B is pressed
    testCodeButton.whenPressed(new TestingVisionCmd());

    // Run vision code for shooting into high goal when gamepad button X is pressed
    visionTrackHighgoalButton.whenPressed(new HighgoalVisionCmdGroup());

    intakeButton.whileHeld(new IntakeCommand());
    launchButton.whileHeld(new SemiAutoFireCommand());
    clearButton.whileHeld(new ClearCommand());
    selectButton1.whenPressed(new SelectLaunch1Command());
    selectButton2.whenPressed(new SelectLaunch2Command());
    selectButton3.whenPressed(new SelectLaunch3Command());
  }
  //#endregion

  //#region Pass Autonomous Command to Main
  public Command getAutonomousCommand() {
    SequentialCommandGroup m_autoCommand = new HighgoalVisionCmdGroup();
    return m_autoCommand;
  }
  //#endregion

  //#region Pass Teleop Command to Main
  public Command getTeleopCommand() {
    SequentialCommandGroup m_teleopCommand = new HighgoalVisionCmdGroup();
    return m_teleopCommand;
  }
  //#endregion
}