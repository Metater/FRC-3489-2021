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
  private WPI_TalonSRX rearLeftDriveMotor = new WPI_TalonSRX(3);
  private WPI_TalonSRX rearRightDriveMotor = new WPI_TalonSRX(4);

  private DifferentialDrive differentialDrive;

  private Joystick leftDriveJoystick = new Joystick(0);
  private Joystick rightDriveJoystick = new Joystick(1);
  private Joystick manipulatorJoystick = new Joystick(2);

  private Solenoid intakeArmSolenoid = new Solenoid(19, 0);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    rearLeftDriveMotor.follow(frontLeftDriveMotor);
    rearRightDriveMotor.follow(frontRightDriveMotor);

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
  public void teleopPeriodic() {
    drive();

    boolean shouldToggleIntakeArmSolenoid = manipulatorJoystick.getRawButtonPressed(1);
    if (shouldToggleIntakeArmSolenoid) {
      toggleIntakeArmSolenoid();
      System.out.println("Toggled intake arm: " + intakeArmSolenoid.get());
    } else {
      //System.out.println("Waiting to toggle intake arm");
    }

    /*
    block
    comment

    hgtdrrd
    */
  }

  private void toggleIntakeArmSolenoid() {
    intakeArmSolenoid.toggle();
  }

  private void drive() {
    // Get drive joystick input
    double leftJoystickY = leftDriveJoystick.getY();
    double rightJoystickY = rightDriveJoystick.getY();

    // Invert joystick values
    leftJoystickY = -leftJoystickY;
    rightJoystickY = -rightJoystickY;

    // Make sure motor speed is below 50%
    if (Math.abs(leftJoystickY) > 0.5) {
      leftJoystickY = leftJoystickY > 0 ? 0.5 : -0.5;
    }
    if (Math.abs(rightJoystickY) > 0.5) {
      if (rightJoystickY > 0) {
        // is positive
        rightJoystickY = 0.5;
      }
      else {
        // is negative
        rightJoystickY = -0.5;
      }
      rightJoystickY = rightJoystickY > 0 ? 0.5 : -0.5;
    }

    //String debugMessage = "Left Speed: " + leftJoystickY + " Right Speed: " + rightJoystickY;
    //System.out.println(debugMessage);

    // Set tankdrive to values
    differentialDrive.tankDrive(leftJoystickY, rightJoystickY);
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
