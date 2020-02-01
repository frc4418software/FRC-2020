/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;

public class ColorSensorSubsystem extends SubsystemBase {
  /**
   * Creates a new ColorSensorSubsystem.
   */
  private static I2C.Port i2cPort = I2C.Port.kOnboard;
  
  private static ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  public static String sensorColor = "N";
  public ColorSensorSubsystem() {
    
  }
  public static void DnDColors() {
    final Color detectedColor = m_colorSensor.getColor();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    if (detectedColor.red > 0.280 && detectedColor.red < 0.550 && detectedColor.blue < 0.150) {
      sensorColor = "R";
    }
    if (detectedColor.blue > 0.270 && detectedColor.blue > 0.450 && detectedColor.red < 0.135) {
      sensorColor = "B";
    }
    if (detectedColor.red > 0.251 && detectedColor.green > 0.480) {
      sensorColor = "Y";
    }
    if (detectedColor.green > 0.540 && detectedColor.red < 0.170 && detectedColor.blue < 0.275) {
      sensorColor= "G";
    }
    SmartDashboard.putString("Color", sensorColor);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
