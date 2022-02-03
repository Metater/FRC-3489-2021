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

  double zL = 0;
  double zR = 0;

  boolean left = true;

  @Override
  public void teleopPeriodic() {

    if (joystick.getRawButtonPressed(11)) {
      left = !left;
    }

    if (joystick.getRawButtonPressed(12)) {
      zR = zL;
    }

    if (joystick.getRawButtonPressed(3)) {
      setSelected(-0.025 + getSelected());
    }
    if (joystick.getRawButtonPressed(4)) {
      setSelected(0.025 + getSelected());
    }

    if (joystick.getRawButton(1))
    {
      zL = 0;
      zR = 0;
    }

    if (joystick.getRawButtonPressed(5))
    {
      setSelected(-0.05 + getSelected());
    }
    if (joystick.getRawButtonPressed(6))
    {
      setSelected(0.05 + getSelected());
    }

    double y = joystick.getY();
    setShooter();
    System.out.println("Controlling motor A: " + left);
    System.out.println(((int)(zL * 1000))/10d + " : " + (int)(zR * 1000)/10d);
    System.out.println(leftFalcon.getSelectedSensorVelocity() / -2048d);
    System.out.println(rightFalcon.getSelectedSensorVelocity() / -2048d);
    if (Math.abs(y) < 0.1) return;
    setSelected((y * -0.02) + getSelected());
  }

  private void setSelected(double speed) {
    if (left) zL = speed;
    else zR = speed;
  }

  private double getSelected() {
    return left ? zL : zR;
  }

  private void setShooter() {
    leftFalcon.set(-zL);
    rightFalcon.set(-zR);
  }
}
