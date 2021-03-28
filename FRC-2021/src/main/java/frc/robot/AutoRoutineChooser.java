/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.trajectory.Trajectory;

/**
 * How this Routine works:
 * This routine sets all of the posible trajectories that the robot can go through in the Auto period.
 * It does this by setting different trajectories for each position/goal that is either selected by Driverstation/FMS or by Smartdashboard.
 * In the DriveTrajectory start command, depending on the information that the robot got from either DS or SD the robot will funnel the 
 * correct Trajectory into DriveTrajectory which is the base Trajectory that is referenced in the AutoCommand in robotContainer.
 */
public class AutoRoutineChooser {
    // private static final double ROBOTHALFLENGTH = 0.5; // length of half/ to the rio ... actual value tbd
    
    //this command is run at the start of the AutoCommand, it sets the DriveTrajectory to one of the 6 posible trajectories based on the goal and the location of the robot
    /*Current possible configurations: (default decided by DS/FMS, can be changed in SmartDashboard; low goal is also the default and can be changed in SD)
    - Low goal shooting: left station, right station, center station
    - High goal shooting: left station, right station, center station (tbd whether or not this will be functional)
    */
    public static Trajectory getDriveTrajectory() {
        int trajectoryNum = Robot.trajectoryChooser.getSelected();
        switch (trajectoryNum) {
          default: // Barrel Racing Path
            return Constants.BarrelRacingPathTrajectory;
          case 1:
            return Constants.SlalomPathTrajectory;
          case 2:
            return Constants.BouncePathTrajectory;
        }
    }
}
