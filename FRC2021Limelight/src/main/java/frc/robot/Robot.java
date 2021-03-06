// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.HashMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry pl = table.getEntry("pipeline");

  //ShuffleboardTab tab = Shuffleboard.getTab("3489 2021");

  //Map<String, SimpleWidget> simpleWidgets = new HashMap<String, SimpleWidget>();

  WPI_TalonSRX falcon = new WPI_TalonSRX(1);

  WPI_TalonFX actualFalcon = new WPI_TalonFX(6);

  int cycles = 0;

  static final int LIMELIGHT_TOGGLE = 2;
  static final int SHOOT_BUTTON = 1;
  boolean limelightEnabled = false;
  double limelightLastToggleTime = 0;

  Joystick driveLeft = new Joystick(0);



  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
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

    /*

    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    X = x;
    Y = y;
    Area = area;
    */

    //PrintDoubleToWidget("X", x);
    //PrintDoubleToWidget("Y", y);
    //PrintDoubleToWidget("Area", area);
  }

  /*
  public void PrintDoubleToWidget(String name, double value)
  {
      if (simpleWidgets.containsKey(name))
      {
          simpleWidgets.get(name).getEntry().setDouble(value);
      }
      else
      {
          SimpleWidget sw = tab.add(name, value);
          sw.getEntry().setDouble(value);
          simpleWidgets.put(name, sw);
      }
  }
  */

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

    falcon.setSafetyEnabled(false);
    actualFalcon.setSafetyEnabled(false);


    addPeriodic(() -> 
    {
      if (limelightEnabled)
      {
        pl.setNumber(0);
        tapeTrackEquationOld();
        //tapeTrackEquation();
        //tapeTrackBigBain();

        if (cycles % 48 == 0)
          System.out.println("Limelight enabled");
      }
      else
      {
        pl.setNumber(5);
        if (cycles % 48 == 0)
          System.out.println("Limelight disabled");
      }

      cycles++;

    }, 0.0105, 0);
  }

  private double speed;
  //double Kp = -0.0001f;
  double Kp = -0.000001f;

  private void tapeTrackBigBain()
  {
    double x = tx.getDouble(0.0);

    double steering_adjust = (Kp * x * x);

    speed += steering_adjust;

    double xDegreesThreshold = 4;
    if (Math.abs(x) < xDegreesThreshold)
    {
      speed = 0;
    }

    System.out.println(speed);

    speed = Math.max(-0.5, Math.min(0.5, speed));

    System.out.println(speed);

    falcon.set(speed);
  }

  private void tapeTrackEquation()
  {
    double x = tx.getDouble(0.0);
    //double y = ty.getDouble(0.0);
    //double area = ta.getDouble(0.0);

    double xDegreesThreshold = 2;
    double speed = 0.4;

    double scale = 4000000;
    double scaledSpeed = (((x * x * x * x)/scale) + speed);

    if (cycles % 95 == 0)
    {
      System.out.println("Xoff" + x);
      System.out.println("Scaled Speed" + scaledSpeed);
    }

    if (x > xDegreesThreshold)
    {
      falcon.set(scaledSpeed * -1);
    } 
    else if (x < (-1 * xDegreesThreshold)) {
      falcon.set(scaledSpeed);
    }
    else {
      falcon.stopMotor();
    }
  }

  private void tapeTrackEquationOld()
  {
    double x = tx.getDouble(0.0);
    //double y = ty.getDouble(0.0);
    //double area = ta.getDouble(0.0);

    double xDegreesThreshold = 7;
    double speed = 0.325;

    double scale = 8880.2;
    double scaledSpeed = (((x * x)/scale) + speed);

    if (x > xDegreesThreshold)
    {
      falcon.set(scaledSpeed * -1);
    } 
    else if (x < (-1 * xDegreesThreshold)) {
      falcon.set(scaledSpeed);
    }
    else {
      falcon.stopMotor();
      
      if (driveLeft.getRawButton(SHOOT_BUTTON))
        actualFalcon.set(0.3);
      else
        actualFalcon.stopMotor();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    /*
    if (X > 0)
    {
      falcon.set(0.065);
    }
    else
    {
      falcon.set(-0.065);
    }
    */

    double time  = Timer.getFPGATimestamp();

    if (driveLeft.getRawButton(LIMELIGHT_TOGGLE))
    {
      if (limelightLastToggleTime + 1 < time)
      {
        limelightLastToggleTime = time;
        limelightEnabled = !limelightEnabled;
      }
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
