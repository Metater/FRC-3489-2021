// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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

  private boolean autoAimEnabled = false;
  private double lastAutoAimToggleTime = 0;

  private boolean limelightEnabled = false;
  private double lastLimelightEnableTime = 0;

  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tx = table.getEntry("tx");
  private NetworkTableEntry pl = table.getEntry("pipeline");

  public static final int DecrementSpeed = 11;
  public static final int IncrementSpeed = 12;
  private double lastDecrementTime = 0;
  private double lastIncrementTime = 0;
  private static final double incrementAmount = -0.05;

  private String turretData = "";

  private double lastShootTime = 0;

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

    driveTrain.setSafetyEnabled(shouldBeSafe);

    pl.setNumber(5);
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
  public void disabledInit()
  {
    if (FileUtils.fileExists("turretData.txt"))
    {
      System.out.println(turretData);
    }
    else
    {
      FileUtils.createLocalFile("turretData.txt");
    }
    FileUtils.writeLocalFile("turretData.txt", turretData);
  }

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

    turretData = "";
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic()
  {
    tryToggleAutoAim();
    tryToggleLimelight();

    drive();
    if (!autoAimEnabled) twist();
    shooter();
    tryDecrementShooterSpeed();
    tryIncrementShooterSpeed();

    double x = tx.getDouble(0.0);

    //

    double statorCurrent = (falconShooterLeft.getStatorCurrent() + falconShooterRight.getStatorCurrent())/2;

    if (statorCurrent > 5)
    {
      if (Timer.getFPGATimestamp() - lastShootTime > 1)
      {
        turretData += "----------------------------------------------------------------------------------\n";
      }

      lastShootTime = Timer.getFPGATimestamp();
      turretData += "Time: " + "{" + Timer.getFPGATimestamp() + "} Stator Current: {" + statorCurrent + "}\n";
    }


    //turretData += "Motor Speed: {" + ninjagoNinjago.get() + "} " + "Target Offset : {" + x + "}\n";

    double speed = Math.abs(ninjagoNinjago.get());

    System.out.println("Stator Current: " + statorCurrent);



    if (autoAimEnabled)
    {
      tapeTrackSimple(x, 1, 0.1675);
      if (Math.signum(speed) > 0.1)
      {

      }
      else if (Math.signum(speed) < 0.1)
      {

      }
      else
      {
        if (autoAimEnabled && Math.abs(x) < 1) autoAimEnabled = false;
        ninjagoNinjago.stopMotor();
      }
    }
    /*
    if (autoAimEnabled)
    {
      
      if (speed <= 0.1f) {

      } else if (speed <= 0.2) {
  
      } else {
        if (autoAimEnabled) tapeTrackEquationOld(x, 2.5, 0.3, 8880.2);
      }
    }
    */

    if (joystickManipulator.getRawButton(9))
      shooterSpeed = 0;


    setShooterStuff();
  }

  private void setShooterStuff()
  {
    if (Math.abs(shooterSpeed) > 1)
    {
      shooterSpeed = Math.signum(shooterSpeed);
    }
    falconShooterLeft.set(shooterSpeed);
    falconShooterRight.set(-1*shooterSpeed);
    shooterSpeedEntry.setDouble(shooterSpeed * -1);
  }

  private void tapeTrackSimple(double x, double xDegreesThreshold, double speed)
  {
    if (Math.abs(x) >= xDegreesThreshold)
    {
      if (Math.signum(x) > 0.5)
      {
        ninjagoNinjago.set(speed * -1);
      }
      else
      {
        ninjagoNinjago.set(speed);
      }
    }
  }

  private void tapeTrackEquationOld(double x, double xDegreesThreshold, double speed, double scale)
  {
    
    //double heightSpeed = 1;
    //double heightScale = 0.0009;
    //double heightScaledPower = -(heightScale * y * y) + heightSpeed;

    double scaledSpeed = (((x * x)/scale) + speed);

    if (x > xDegreesThreshold)
    {
      ninjagoNinjago.set(scaledSpeed * -1);
    } 
    else if (x < (-1 * xDegreesThreshold)) {
      ninjagoNinjago.set(scaledSpeed);
    }
    else {
      ninjagoNinjago.stopMotor();
    }
  }

  private void tryToggleAutoAim()
  {
    if (joystickManipulator.getRawButton(4) && 1 + lastAutoAimToggleTime < Timer.getFPGATimestamp())
    {
      lastAutoAimToggleTime = Timer.getFPGATimestamp();
      autoAimEnabled = !autoAimEnabled;
    }
  }
  private void tryToggleLimelight()
  {
    if (joystickManipulator.getRawButton(3) && 1 + lastLimelightEnableTime < Timer.getFPGATimestamp())
    {
      lastLimelightEnableTime = Timer.getFPGATimestamp();
      limelightEnabled = !limelightEnabled;
      if (limelightEnabled) pl.setNumber(0);
      else pl.setNumber(5);
    }
  }

  private void tryDecrementShooterSpeed()
  {
    if (joystickManipulator.getRawButton(DecrementSpeed) && 0.25 + lastDecrementTime < Timer.getFPGATimestamp())
    {
      lastDecrementTime = Timer.getFPGATimestamp();
      shooterSpeed -= incrementAmount;
    }
  }
  private void tryIncrementShooterSpeed()
  {
    if (joystickManipulator.getRawButton(IncrementSpeed) && 0.25 + lastIncrementTime < Timer.getFPGATimestamp())
    {
      lastIncrementTime = Timer.getFPGATimestamp();
      shooterSpeed += incrementAmount;
    }
  }

  private void shooter()
  {
    double speedChange = joystickManipulator.getY();
    if (Math.abs(speedChange) > 0.175)
    {
      speedChange *= 0.0025;
      shooterSpeed += speedChange;
    }
  }

  private void twist()
  {
    double twistSpeed = joystickManipulator.getZ() * -1;
    if (Math.abs(twistSpeed) > 0.1)
    {
      twistSpeed *= 0.30;
      ninjagoNinjago.set(twistSpeed);
      System.out.println(twistSpeed);
    }
    else
    {
      ninjagoNinjago.stopMotor();
    }
  }

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
