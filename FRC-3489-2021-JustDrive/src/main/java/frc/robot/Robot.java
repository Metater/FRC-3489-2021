package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends TimedRobot {

  private WPI_TalonSRX driveLeftFront = new WPI_TalonSRX(1);
  private WPI_TalonSRX driveRightFront = new WPI_TalonSRX(2);
  private WPI_TalonSRX driveLeftFollower = new WPI_TalonSRX(3);
  private WPI_TalonSRX driveRightFollower = new WPI_TalonSRX(4);

  private Joystick joystickDriveLeft = new Joystick(0);
  private Joystick joystickDriveRight = new Joystick(1);

  private DifferentialDrive differentialDrive;

  private boolean frontSwitched = false;

  private boolean shouldSwitchFront() {
    return joystickDriveLeft.getRawButtonPressed(13) || joystickDriveRight.getRawButtonPressed(13);
  }

  @Override
  public void robotInit() {

    if (FileUtils.fileExists("frontSwitched.txt")) {
      frontSwitched = FileUtils.readLocalFile("frontSwitched.txt").get(0) == "1";
    }
    else {
      FileUtils.createLocalFile("frontSwitched.txt");
    }

    driveLeftFollower.follow(driveLeftFront);
    driveRightFollower.follow(driveRightFront);
    differentialDrive = new DifferentialDrive(driveLeftFront, driveRightFront);

  }

  @Override
  public void robotPeriodic() {

  }

  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {

  }

  @Override
  public void teleopPeriodic() {
    if (shouldSwitchFront()) {
      frontSwitched = !frontSwitched;
    }

    double leftY = joystickDriveLeft.getY();
    double rightY = joystickDriveRight.getY();

    double leftSpeed = 0;
    double rightSpeed = 0;

    if (Math.abs(leftY) >= 0.1) leftSpeed = leftY;
    if (Math.abs(rightY) >= 0.1) rightSpeed = rightY;

    if (frontSwitched) {
      differentialDrive.tankDrive(leftSpeed, rightSpeed);
    }
    else {
      differentialDrive.tankDrive(-rightSpeed, -leftSpeed);
    }
  }

  @Override
  public void disabledInit() {
    String frontSwitchedText = frontSwitched ? "1" : "0";
    FileUtils.writeLocalFile("frontSwitched.txt", frontSwitchedText);
  }

  @Override
  public void disabledPeriodic() {

  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
