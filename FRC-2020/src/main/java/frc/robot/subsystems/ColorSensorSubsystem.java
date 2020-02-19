/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;

import com.revrobotics.ColorSensorV3;

public class ColorSensorSubsystem extends SubsystemBase {
  /**
   * Creates a new ColorSensorSubsystem.
   */

  //establishes colorsensor and sets up port
  private static ColorSensorV3 m_colorSensor;

  public ColorSensorSubsystem() {
    //assignes the color sensor's port
    m_colorSensor = new ColorSensorV3(Constants.i2cPort);

  }


  //*************Color Sensing*************//

  public static void DnDColors() {
    final Color detectedColor = m_colorSensor.getColor();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    if (detectedColor.red > 0.280 && detectedColor.red < 0.550 && detectedColor.blue < 0.150) {
      Robot.color = "R";
    }
    if (detectedColor.blue > 0.270 && detectedColor.blue > 0.450 && detectedColor.red < 0.135) {
      Robot.color = "B";
    }
    if (detectedColor.red > 0.251 && detectedColor.green > 0.480) {
      Robot.color= "Y";
    }
    if (detectedColor.green > 0.540 && detectedColor.red < 0.170 && detectedColor.blue < 0.275) {
      Robot.color= "G";
    }
    else {
      Robot.color = "N";
    }
    SmartDashboard.putString("Color", Robot.color);
  }
  

  //*************Periodic Actions*************//
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
