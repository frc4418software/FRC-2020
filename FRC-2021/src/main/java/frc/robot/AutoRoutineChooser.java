/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

/**
 * How this Routine works:
 * This routine sets all of the posible trajectories that the robot can go through in the Auto period.
 * It does this by setting different trajectories for each position/goal that is either selected by Driverstation/FMS or by Smartdashboard.
 * In the DriveTrajectory start command, depending on the information that the robot got from either DS or SD the robot will funnel the 
 * correct Trajectory into DriveTrajectory which is the base Trajectory that is referenced in the AutoCommand in robotContainer.
 */
public class AutoRoutineChooser {
    private static Trajectory driveTrajectory;

    private static final double ROBOTHALFLENGTH = 0.5; // length of half/ to the rio ... actual value tbd

    public static void initializeConstraints() {
      // Create a voltage constraint to ensure we don't accelerate too fast
      DifferentialDriveVoltageConstraint autoVoltageConstraint =
      new DifferentialDriveVoltageConstraint(
          new SimpleMotorFeedforward(Constants.ksVolts,
                                    Constants.kvVoltSecondsPerMeter,
                                    Constants.kaVoltSecondsSquaredPerMeter),
          Constants.kDriveKinematics,
          10);

      // Create config for trajectory
      TrajectoryConfig config = new TrajectoryConfig(Constants.kMaxSpeedMetersPerSecond,
                                        Constants.kMaxAccelerationMetersPerSecondSquared)
          // Add kinematics to ensure max speed is actually obeyed
          .setKinematics(Constants.kDriveKinematics)
          // Apply the voltage constraint
          .addConstraint(autoVoltageConstraint);
    }

    public static Trajectory getDriveTrajectory() {
      return driveTrajectory;
    }
    
    //this command is run at the start of the AutoCommand, it sets the DriveTrajectory to one of the 6 posible trajectories based on the goal and the location of the robot
    /*Current possible configurations: (default decided by DS/FMS, can be changed in SmartDashboard; low goal is also the default and can be changed in SD)
    - Low goal shooting: left station, right station, center station
    - High goal shooting: left station, right station, center station (tbd whether or not this will be functional)
    */
    public static void selectDriveTrajectory () {
        int trajectoryNum = Robot.trajectoryChooser.getSelected();
        switch (trajectoryNum) {
          case 0: // Barrel Racing Path
            driveTrajectory = Constants.BarrelRacingPathTrajectory;
            break;
          case 1:
            driveTrajectory = Constants.SlalomPathTrajectory;
            break;
          case 2:
            driveTrajectory = Constants.BouncePathTrajectory;
            break;
        }
    }
}
