// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.handlers.RobotHandler;

public class Robot extends TimedRobot {

  private RobotHandler robotHandler;

  @Override
  public void robotInit() {
    robotHandler = new RobotHandler(this);
    robotHandler.robotInit();
  }

  @Override
  public void robotPeriodic() {
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
    robotHandler.autonomousInit();
  }

  @Override
  public void autonomousPeriodic() {
    robotHandler.autonomousPeriodic();
  }

  @Override
  public void teleopInit() {
    robotHandler.teleopInit();
  }

  @Override
  public void teleopPeriodic() {
    robotHandler.teleopPeriodic();
  }

  @Override
  public void testInit() {
    robotHandler.testInit();
  }

  @Override
  public void testPeriodic() {
    robotHandler.testPeriodic();
  }
}
