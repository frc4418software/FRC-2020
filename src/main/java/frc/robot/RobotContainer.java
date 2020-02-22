/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.Constants;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  //private final ExampleCommand spinControlWheelCommand = new ExampleCommand(m_exampleSubsystem);
  private final ControlPanelSubsystem m_controlPanelSubsystem = new ControlPanelSubsystem();

  //Create joysticks
  private static final Joystick X3D_LEFT = new Joystick(Constants.X3D_LEFT_JOYSTICK_ID),
                                X3D_RIGHT = new Joystick(Constants.X3D_RIGHT_JOYSTICK_ID),
                                GAMEPAD = new Joystick(Constants.GAMEPAD_JOYSTICK_ID);

  //Create and Assign buttons
  public static JoystickButton moveControlArmUpButton = new JoystickButton(GAMEPAD, Constants.MOVE_CONT_ARM_UP_ID);
  public static JoystickButton moveControlArmDownButton =  new JoystickButton(GAMEPAD, Constants.MOVE_CONT_ARM_DOWN_ID);
  public static JoystickButton spinControlWheelButton = new JoystickButton (GAMEPAD, Constants.SPIN_CONT_WHEEL_ID);
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings(){
    moveControlArmUpButton.whileHeld(new moveControlArmUpCommand(m_controlPanelSubsystem));
    moveControlArmDownButton.whileHeld(new moveControlArmDownCommand(m_controlPanelSubsystem));
    spinControlWheelButton.whileHeld(new spinControlWheelCommand(m_controlPanelSubsystem));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
    // An ExampleCommand will run in autonomous
  }
}
