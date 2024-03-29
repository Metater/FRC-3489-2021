// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

  private WPI_TalonSRX frontLeftDriveMotor = new WPI_TalonSRX(1);
  private WPI_TalonSRX frontRightDriveMotor = new WPI_TalonSRX(2);
  private WPI_TalonSRX backLeftDriveMotor = new WPI_TalonSRX(3);
  private WPI_TalonSRX backRightDriveMotor = new WPI_TalonSRX(4);

  private DifferentialDrive differentialDrive;

  private Joystick leftDriveJoystick = new Joystick(Constants.LeftDriveJoystickPort);
  private Joystick rightDriveJoystick = new Joystick(Constants.RightDriveJoystickPort);
  private Joystick manipulatorJoystick = new Joystick(Constants.ManipulatorJoystickPort);

  private Solenoid solenoid = new Solenoid(19, 1);

  private int currentAutoStep = 0;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    backLeftDriveMotor.follow(frontLeftDriveMotor);
    backRightDriveMotor.follow(frontRightDriveMotor);

    differentialDrive = new DifferentialDrive(frontLeftDriveMotor, frontRightDriveMotor);
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

    solenoid.set(true);

    frontLeftDriveMotor.setSelectedSensorPosition(0);

    currentAutoStep = 1;
  }

  // Drive 10 feet with solenoid extended, then retract
  private void AutoStep1(double currentEncoderClicks) {
    // encoder clicks are less than target
    if (Math.abs(currentEncoderClicks) < Constants.EncoderClicksForTenFeet) {
      differentialDrive.tankDrive(Constants.AutoDriveSpeed, Constants.AutoDriveSpeed);
    }
    // encoder clicks are greater than target
    else {
      differentialDrive.stopMotor();
      solenoid.set(false);

      frontLeftDriveMotor.setSelectedSensorPosition(0);
      currentAutoStep++;
    }
  }

    // Do a 180
    private void AutoStep2(double currentEncoderClicks) {
      // encoder clicks are less than target
      if (Math.abs(currentEncoderClicks) < Constants.EncoderClicksFor180Turn) {
        differentialDrive.tankDrive(-Constants.Auto180TurnSpeed, Constants.Auto180TurnSpeed);
      }
      // encoder clicks are greater than target
      else {
        differentialDrive.stopMotor();

        frontLeftDriveMotor.setSelectedSensorPosition(0);
        currentAutoStep++;
      }
    }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

    // Drive 10 feet with solenoid extended, then retract
    // Do a 180
    // Drive 5 feet

    double currentEncoderClicks = frontLeftDriveMotor.getSelectedSensorPosition();
    System.out.println("Current encoder clicks: " + currentEncoderClicks);

    System.out.println("Running auto step: " + currentAutoStep);

    switch (currentAutoStep) {
      case 0: // Initialization stuff
        
        break;
      case 1: // First auto step
        AutoStep1(currentEncoderClicks);
        break;
      case 2: // Second auto step
        AutoStep2(currentEncoderClicks);
        break;
      default:
        System.out.println("Unknown auto step: " + currentAutoStep);
        break;
    }

    // encoder clicks are less than target
    if (Math.abs(currentEncoderClicks) < Constants.EncoderClicksForTenFeet) {
      differentialDrive.tankDrive(Constants.AutoDriveSpeed, Constants.AutoDriveSpeed);
    }
    // encoder clicks are greater than target
    else {
      differentialDrive.stopMotor();
      solenoid.set(false);

      frontLeftDriveMotor.setSelectedSensorPosition(0);

      // encoder clicks are less than target
      if (Math.abs(currentEncoderClicks) < Constants.EncoderClicksFor180Turn) {
        differentialDrive.tankDrive(-Constants.Auto180TurnSpeed, Constants.Auto180TurnSpeed);
      }
      // encoder clicks are greater than target
      else {
        differentialDrive.stopMotor();

        frontLeftDriveMotor.setSelectedSensorPosition(0);

        // encoder clicks are less than target
        if (Math.abs(currentEncoderClicks) < Constants.EncoderClicksForFiveFeet) {
          differentialDrive.tankDrive(Constants.AutoDriveSpeed, Constants.AutoDriveSpeed);
        }
        // encoder clicks are greater than target
        else {
          differentialDrive.stopMotor();
        }
      }
    }
  }

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
  public void teleopPeriodic() {

    double leftSpeed = leftDriveJoystick.getY();
    double rightSpeed = rightDriveJoystick.getY();
    
    differentialDrive.tankDrive(leftSpeed, rightSpeed);

    boolean activateSolenoid = manipulatorJoystick.getRawButtonPressed(5);
    if (activateSolenoid) {
      solenoid.set(true);
    }
    boolean deactivateSolenoid = manipulatorJoystick.getRawButtonReleased(5);
    if (deactivateSolenoid) {
      solenoid.set(false);
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
