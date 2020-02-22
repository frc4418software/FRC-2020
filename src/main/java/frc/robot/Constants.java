/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    private static final int //GAMEPAD_AXIS_LEFT_X = 0, // Gamepad axis
    GAMEPAD_AXIS_LEFT_Y = 1,
    GAMEPAD_BUTTON_A = 1,
    GAMEPAD_AXIS_LEFT_TRIGGER = 2,
    GAMEPAD_AXIS_RIGHT_TRIGGER = 3,
    /*GAMEPAD_AXIS_RIGHT_X = 4,
    GAMEPAD_AXIS_RIGHT_Y = 5,
    GAMEPAD_AXIS_RIGHT_TRIGGER = 3,
    // Gamepad buttons
    GAMEPAD_BUTON_B = 2,
    GAMEPAD_BUTON_X = 3,
    GAMEPAD_BUTTON_Y = 4,
    GAMEPAD_BUTON_LEFT_BUMPER = 5,
    GAMEPAD_BUTON_RIGHT_BUMPER = 6,
    GAMEPAD_BUTTON_BACK = 7,
    GAMEPAD_BUTTON_START = 8,
    GAMEPAD_BUTTON_LOGITECH = 9,
    GAMEPAD_BUTTON_LEFT_JS = 10,
    GAMEPAD_BUTTON_RIGHT_JS = 11,*/
    X3D_AXIS_PITCH = 1, // X3d axis
    X3D_AXIS_ROLL = 0,
    /*X3D_AXIS_YAW = 2,
    X3D_AXIS_OTHER = 3,*/
    X3D_BUTTON_TRIGGER = 1, // X3d buttons
    X3D_BUTTON_GRIP = 2,
    X3D_BUTTON_3 = 3,
    //X3D_BUTTON_4 = 4,
    X3D_BUTTON_5 = 5,
    X3D_BUTTON_6 = 6;
    //X3D_BUTTON_7 = 7,
    //X3D_BUTTON_8 = 8,
    //X3D_BUTTON_9 = 9,
    //X3D_BUTTON_10 = 10,
     
    // Controller IDs
    public static final int X3D_LEFT_JOYSTICK_ID = 0, // Joysticks IDs
    X3D_RIGHT_JOYSTICK_ID = 1,
    GAMEPAD_JOYSTICK_ID = 2,
    LEFT_TANK_DRIVE_AXIS_ID = X3D_AXIS_PITCH, // Tank drive axis
    RIGHT_TANK_DRIVE_AXIS_ID = X3D_AXIS_PITCH,
    FORWARD_ARCADE_DRIVE_AXIS_ID = X3D_AXIS_PITCH, // Arcade drive axis
    ANGLE_ARCADE_DRIVE_AXIS_ID = X3D_AXIS_ROLL,
    TOGGLE_ARCADE_DRIVE_BUTOON_ID = X3D_BUTTON_5,
    DRIVE_STRAIGHT_BUTTON_ID = X3D_BUTTON_GRIP,
    LAUNCH_BUTTON_ID = X3D_BUTTON_3, //Manipulator buttons
    LOAD_BUTTON_ID = X3D_BUTTON_TRIGGER,
    //PIVOT_BUTTON_ID = X3D_BUTTON_12,
    INTAKE_BUTTON_ID = X3D_BUTTON_6,
    CLIMB_AXIS_ID = GAMEPAD_AXIS_LEFT_Y,
    MOVE_CONT_ARM_UP_ID = GAMEPAD_AXIS_LEFT_TRIGGER,
    MOVE_CONT_ARM_DOWN_ID = GAMEPAD_AXIS_RIGHT_TRIGGER,
    SPIN_CONT_WHEEL_ID = GAMEPAD_BUTTON_A;


    // Control Panel IDs
    public static final int CON_SPIN_VICTOR_SPX_ID = 40,
    CON_ARM_MOV_VICTOR_SPIX_ID = 41;
}
