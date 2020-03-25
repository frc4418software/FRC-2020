/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TestingVisionSubsys extends SubsystemBase {

  //private Servo jevoisPitchServo;

  //#region Constructor
  public TestingVisionSubsys() {
    //jevoisPitchServo = new Servo(Constants.JEVOIS_PITCH_SERVO_PWM_PIN);
    //jevoisPitchServo.setBounds(1950, 1504, 1500, 1496, 1050);
    //jevoisPitchServo.setBounds(2.4, 0., 0., 0., 0.9);
  }
  //#endregion End of Constructor

  //#region Set and Read Servo Angle
  // public void setPitchAngle(double degrees) {
  //   jevoisPitchServo.setAngle(degrees);
  // }
  // //=========================================================
  // public double getPitchAngle() {
  //   return jevoisPitchServo.getAngle();
  // }
  //#endregion
}