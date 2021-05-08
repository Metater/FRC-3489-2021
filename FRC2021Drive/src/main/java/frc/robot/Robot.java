// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private Joystick joystickDriveLeft = new Joystick(0);
  private Joystick joystickDriveRight = new Joystick(1);
  private Joystick joystickManipulator = new Joystick(2);

  public static final double DriveJoystickThreshold = 0.05;

  private WPI_TalonSRX driveFrontLeft = new WPI_TalonSRX(7);
  private WPI_TalonSRX driveFrontRight = new WPI_TalonSRX(4);
  private WPI_TalonSRX driveBackLeft = new WPI_TalonSRX(5);
  private WPI_TalonSRX driveBackRight = new WPI_TalonSRX(2);

  private WPI_TalonSRX ninjagoNinjago = new WPI_TalonSRX(6);
  public WPI_TalonFX falconShooterLeft = new WPI_TalonFX(11);
  public WPI_TalonFX falconShooterRight = new WPI_TalonFX(10);

  public static final boolean shouldBeSafe = false;

  private DifferentialDrive driveTrain;

  private double shooterSpeed = 0;

  private ShuffleboardTab tab = Shuffleboard.getTab("3489 New Robot");

  private NetworkTableEntry shooterSpeedEntry;




  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    driveTrain = new DifferentialDrive(driveFrontLeft, driveFrontRight);

    driveBackLeft.follow(driveFrontLeft);
    driveBackRight.follow(driveFrontRight);

    shooterSpeedEntry = tab.add("Shooter Speed: ", shooterSpeed).getEntry();

    driveFrontLeft.setSafetyEnabled(shouldBeSafe);
    driveFrontRight.setSafetyEnabled(shouldBeSafe);
    driveBackLeft.setSafetyEnabled(shouldBeSafe);
    driveBackRight.setSafetyEnabled(shouldBeSafe);

    ninjagoNinjago.setSafetyEnabled(shouldBeSafe);
    falconShooterLeft.setSafetyEnabled(shouldBeSafe);
    falconShooterRight.setSafetyEnabled(shouldBeSafe);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic()
  {
    drive();
    twist();
    shooter();

    if (joystickManipulator.getRawButton(12))
    {
      falconShooterLeft.set(-1);
      falconShooterRight.set(1);
      shooterSpeed = -1;
    }
    else if (joystickManipulator.getRawButton(10))
    {
      falconShooterLeft.set(-0.8);
      falconShooterRight.set(0.8);
      shooterSpeed = -0.8;
    }
    else if (joystickManipulator.getRawButton(8))
    {
      falconShooterLeft.set(-0.6);
      falconShooterRight.set(0.6);
      shooterSpeed = -0.6;
    }
    else if (joystickManipulator.getRawButton(7))
    {
      falconShooterLeft.set(-0.4);
      falconShooterRight.set(0.4);
      shooterSpeed = -0.4;
    }
    else if (joystickManipulator.getRawButton(9))
    {
      falconShooterLeft.set(-0.2);
      falconShooterRight.set(0.2);
      shooterSpeed = -0.2;
    }
    else if (joystickManipulator.getRawButton(11))
    {
      falconShooterLeft.stopMotor();
      falconShooterRight.stopMotor();
      shooterSpeed = 0;
    }
  }

  private void shooter()
  {
    double speedChange = joystickManipulator.getY();
    if (Math.abs(speedChange) > 0.175)
    {
      speedChange *= 0.0025;
      shooterSpeed += speedChange;
      if (Math.abs(shooterSpeed) > 1)
      {
        shooterSpeed = Math.signum(shooterSpeed);
      }

      falconShooterLeft.set(shooterSpeed);
      falconShooterRight.set(-1*shooterSpeed);
      shooterSpeedEntry.setDouble(shooterSpeed);
    }
  }

  private void twist()
  {
    double twistSpeed = joystickManipulator.getZ() * -1;
    if (Math.abs(twistSpeed) > 0.15)
    {
      twistSpeed *= 0.65;
      ninjagoNinjago.set(twistSpeed);
    }
    else
    {
      ninjagoNinjago.stopMotor();
    }
  }

  /*
  private void tryToggleFullSend()
  {
    if (Timer.getFPGATimestamp() > 1 + lastFullSendToggleTime)
    {
      lastFullSendToggleTime = Timer.getFPGATimestamp();
      fullSending = !fullSending;
      if (fullSending)
      {
        falconShooterLeft.set(-1);
        falconShooterRight.set(1);
      }
      else
      {
        falconShooterLeft.set(0);
        falconShooterRight.set(0);
      }
    }
  }
  */

  private void drive()
  {
    double leftTrain = joystickDriveLeft.getY() *-1;
    double rightTrain = joystickDriveRight.getY() *-1;

    if (Math.abs(leftTrain) > DriveJoystickThreshold || Math.abs(rightTrain) > DriveJoystickThreshold)
    {
      driveTrain.tankDrive(leftTrain, rightTrain);
    }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
