package frc.robot.controller;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.*;
/**
* This is used to get the RSS data 
*/
public class EntréeDuRobot {
    public Joystick joystickDriveLeft = new Joystick(1);
    public Joystick joystickDriveRight = new Joystick(2);
    public Joystick joystickManipulator = new Joystick(3);

    public EntréeDuRobot()
    {

    }
    public boolean doScissorLift()
    {
        return joystickManipulator.getRawButton(Constants.Buttons.SCISSOR_LIFT_RIGHT) &&
               joystickManipulator.getRawButton(Constants.Buttons.SCISSOR_LIFT_LEFT);
    }
    public double getRightDriveSpeed()
    {
        double driveSpeed = joystickDriveRight.getY();
        if (Math.abs(driveSpeed) < Constants.DRIVE_SPEED_CUTOFF)
            driveSpeed = 0;
        return driveSpeed;
    }
    public double getLeftDriveSpeed()
    {
        double driveSpeed = joystickDriveLeft.getY();
        if (Math.abs(driveSpeed) < Constants.DRIVE_SPEED_CUTOFF)
            driveSpeed = 0;
        return driveSpeed;
    }
    public boolean getDriveButtonPressed(int button)
    {
        return getRightDriveButtonPressed(button) ||
            getLeftDriveButtonPressed(button);
    }
    public boolean getRightDriveButtonPressed(int button)
    {
        return joystickDriveRight.getRawButtonPressed(button);
    }
    public boolean getLeftDriveButtonPressed(int button)
    {
        return joystickDriveLeft.getRawButtonPressed(button);
    }

}
