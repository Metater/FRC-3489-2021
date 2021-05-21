// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.handlers.RobotHandler;

public class Robot extends TimedRobot {

  private RobotHandler robotHandler;

  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    robotHandler = new RobotHandler(this);
    robotHandler.robotInit();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    robotHandler.robotPeriodic();
  }

  @Override
  public void disabledInit() {
    robotHandler.disabledInit();
  }

  @Override
  public void disabledPeriodic() {
    robotHandler.disabledPeriodic();
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    robotHandler.autonomousInit();
  }

  @Override
  public void autonomousPeriodic() {
    robotHandler.autonomousPeriodic();
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    robotHandler.teleopInit();
  }

  @Override
  public void teleopPeriodic() {
    robotHandler.teleopPeriodic();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
    robotHandler.testInit();
  }

  @Override
  public void testPeriodic() {
    robotHandler.testPeriodic();
  }
}
