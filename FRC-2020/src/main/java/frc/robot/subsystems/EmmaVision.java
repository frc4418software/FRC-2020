// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/



// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.command.Subsystem;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// //import org.json.simple.parser.ParseException;

// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.DigitalOutput;
// import edu.wpi.first.wpilibj.PWM;
// import edu.wpi.first.wpilibj.SerialPort;
// import frc.robot.Robot;
// import frc.robot.RobotMap;
// /**
//  * Add your docs here.
//  */
// public class EmmaVision extends Subsystem {
//   // Put methods for controlling this subsystem
//   // here. Call these from Commands.

//   private DigitalOutput limeLight;
//   private static SerialPort jevois;

//   public EmmaVision() {
//     //jevois = new SerialPort(111520, SerialPort.Port.values()[1]);
//     // jevois = new SerialPort(111520, SerialPort.Port.kUSB1);
//     limeLight = new DigitalOutput(RobotMap.VISION_LIGHT_ID);
//     limeLight.set(true);
//     jevois = new SerialPort(115200, SerialPort.Port.kUSB1);
//   }
//   //need to edit k in kUSB to be correct port number
//   @Override
//   public void initDefaultCommand() {
//     // Set the default command for a subsystem here.
//     setDefaultCommand(new MySpecialCommand());
//   }

//   public void swapLimeLight() {
//       if(limeLight.get()){
//           limeLight.set(false);
//       }else{
//           limeLight.set(true);
//       }
//   }
//   //private static DriveSubsystem driveSubsystem = new DriveSubsystem();
//   //gets current xy coordinates and adjusts according to those coordinates
//   public void startTracking() {
//       int [] xy = getXY();
//       while (xy[0] != 800) {
//           moveAccordingToXY(xy);
//       }
//   }

//   //returns the data derived from serial message sent by camera
//   public int[] getXY() {
//       //creates xy array with nonsense values originally and parses string containing data to adjust nonsensical values
//       int[] xy = {800,800};
//       String delims = "[,]";
//       String data = jevois.readString();
//       SmartDashboard.putString("JeVois XY:", data);
//       String[] parsedData = data.split(delims);
//       //tests if tracking in the first place
//       if (parsedData[0] == "Trk: 1") {
//           String stringXWithLabel = parsedData[1];
//           String stringX = stringXWithLabel.substring(8);
//           String stringYWithLabel = parsedData[2];
//           String stringY = stringYWithLabel.substring(8);
//           int x = Integer.valueOf(stringX);
//           int y = Integer.valueOf(stringY);
//           xy[0] = x;
//           xy[1] = y;
//       }
//     try {

//     } catch (ParseException pe) {
//       //returns array of coordinates nonsensical or not
//       return xy;
//     }
//   }

//   //places the target in a correct position of the camera mapping
//   public void moveAccordingToXY(int xy[]){
//       //must be changed to ideal value
//       while (xy[0] != 64 || xy[0] != 192) {
//           //checks which ideal location is closer to determine which way to turn
//           int distanceTargetLeft = 9 - xy[0];
//           int distanceTargetRight = 8 - xy[0];
//           if (distanceTargetLeft > distanceTargetRight) {
//               Robot.driveSubsystem.setLeftMotorValue(10);
//           } else {
//               Robot.driveSubsystem.setRightMotorValue(10);
//           }
//           //get updated xy values
//           int newXY[] = getXY();
//           //recursively call function again with updated xy values
//           moveAccordingToXY(newXY);
//       }
//       //drives forward until y-coordinate is ideal
//       while (xy[1] != 0) {
//           Robot.driveSubsystem.tankDrive(5, 5);
//           int newXY[] = getXY();
//           moveAccordingToXY(newXY);
//       }
//   }
  
// }