package frc.robot.general;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.*;


/**
* This is used to get the robot input, and different methods for checking weather an action should be started, or is happening
*/
public class RobotInputHandler {


    public Joystick joystickDriveLeft = new Joystick(Constants.USB.JOYSTICK_DRIVE_LEFT);
    public Joystick joystickDriveRight = new Joystick(Constants.USB.JOYSTICK_DRIVE_RIGHT);
    public Joystick joystickManipulator = new Joystick(Constants.USB.JOYSTICK_MANIPULATOR);

    public enum JoystickType
    {
        DriveLeft,
        DriveRight,
        Manipulator
    }

    public RobotInputHandler()
    {
        
    }

    // SPECIFIC JOYSTICK BUTTON METHODS
    public boolean shouldScissorLift()
    {
        return joystickManipulator.getRawButton(Constants.Buttons.SCISSOR_LIFT_RIGHT) &&
               joystickManipulator.getRawButton(Constants.Buttons.SCISSOR_LIFT_LEFT);
    }
    public boolean shouldBLEH()
    {
        return joystickManipulator.getRawButton(Constants.Buttons.BLEH);
    }


    // SPECIFIC JOYSTICK STICK METHODS
    public double getLeftDriveSpeed()
    {
        double driveSpeed = joystickDriveLeft.getY();
        if (Math.abs(driveSpeed) < Constants.DRIVE_SPEED_CUTOFF)
            driveSpeed = 0;
        return driveSpeed;
    }
    public double getRightDriveSpeed()
    {
        double driveSpeed = joystickDriveRight.getY();
        if (Math.abs(driveSpeed) < Constants.DRIVE_SPEED_CUTOFF)
            driveSpeed = 0;
        return driveSpeed;
    }


    // GENERAL JOYSTICK BUTTON METHODS
    public boolean isEitherOrDriveJoystickButton(int button)
    {
        return joystickDriveLeft.getRawButton(button) ||
               joystickDriveRight.getRawButton(button);
    }
    public boolean isEitherOrDriveJoystickButtonPressed(int button)
    {
        return joystickDriveLeft.getRawButtonPressed(button) ||
               joystickDriveRight.getRawButtonPressed(button);
    }
    public boolean isEitherOrDriveJoystickButtonReleased(int button)
    {
        return joystickDriveLeft.getRawButtonReleased(button) ||
               joystickDriveRight.getRawButtonReleased(button);
    }

}