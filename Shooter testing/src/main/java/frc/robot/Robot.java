// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private WPI_TalonFX leftFalcon = new WPI_TalonFX(15);
  private WPI_TalonFX rightFalcon = new WPI_TalonFX(16);

  private Joystick joystick = new Joystick(2);

  @Override
  public void robotInit() {

  }

  @Override
  public void robotPeriodic() {

  }

  @Override
  public void teleopInit() {

  }

  double zoomieness = 0;

  @Override
  public void teleopPeriodic() {

    if (joystick.getRawButtonPressed(3)) {
      zoomieness -= 0.025;
    }
    if (joystick.getRawButtonPressed(4)) {
      zoomieness += 0.025;
    }

    if (joystick.getRawButton(1))
    {
      zoomieness = 0;
    }
    if (joystick.getRawButton(7))
    {
      zoomieness = 0.4;
    }
    if (joystick.getRawButton(9))
    {
      zoomieness = 0.5;
    }
    if (joystick.getRawButton(11))
    {
      zoomieness = 0.6;
    }
    if (joystick.getRawButton(8))
    {
      zoomieness = 0.7;
    }
    if (joystick.getRawButton(10))
    {
      zoomieness = 0.8;
    }
    if (joystick.getRawButton(12))
    {
      zoomieness = 0.9;
    }
    if (joystick.getRawButton(2))
    {
      zoomieness = 0.2;
    }
    if (joystick.getRawButton(5))
    {
      zoomieness -= 0.05;
    }
    if (joystick.getRawButton(6))
    {
      zoomieness += 0.05;
    }

    double y = joystick.getY();
    setShooter(zoomieness);
    System.out.println("Speed: " + ((int)(zoomieness * 1000))/10d);
    System.out.println(leftFalcon.getSelectedSensorVelocity() / -2048d);
    if (Math.abs(y) < 0.1) return;
    zoomieness += y * -0.02;
  }

  private void setShooter(double speed)
  {
    leftFalcon.set(-speed);
    rightFalcon.set(speed);
  }
}
