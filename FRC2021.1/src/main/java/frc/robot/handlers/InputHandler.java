package frc.robot.handlers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;

import frc.robot.*;


/**
* This is used to get the robot input, and different methods for checking weather an action should be started, or is happening
*/
public class InputHandler {

    private RobotHandler robotHandler;

    public Joystick joystickDriveLeft = new Joystick(Constants.USB.JOYSTICK_DRIVE_LEFT);
    public Joystick joystickDriveRight = new Joystick(Constants.USB.JOYSTICK_DRIVE_RIGHT);
    public Joystick joystickManipulator = new Joystick(Constants.USB.JOYSTICK_MANIPULATOR);

    /*
    public DigitalInput _ballInputSensor = new DigitalInput(0);
    public DigitalInput _ballOutputSensor = new DigitalInput(1);
    public DigitalInput _stopWinchSensor = new DigitalInput(2);
    */

    public InputHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    // SPECIFIC JOYSTICK BUTTON METHODS
    public boolean shouldScissorLift()
    {
        boolean bothButtonsPressed = joystickManipulator.getRawButton(Constants.Buttons.SCISSOR_LIFT_RIGHT) && joystickManipulator.getRawButton(Constants.Buttons.SCISSOR_LIFT_LEFT);
        return bothButtonsPressed;
    }
    public boolean shouldToggleIntake()
    {
        if (robotHandler.ballSystemHandler.lastIntakeToggleTime + 1 < Timer.getFPGATimestamp())
            return joystickManipulator.getRawButtonPressed(Constants.Buttons.BALL_INTAKE_ROLLER_TOGGLE);
        return false;
    }

    public boolean shouldSwitchFront()
    {
        if (robotHandler.stateHandler.lastSwitchFrontTime + 1 < Timer.getFPGATimestamp())
            return isEitherOrDriveJoystickButtonPressed(Constants.Buttons.SWITCH_FRONT);
        return false;
    }
    public boolean shouldToggleSpeedScale()
    {
        if (robotHandler.stateHandler.lastSpeedScaleToggleTime + 1 < Timer.getFPGATimestamp())
            return isEitherOrDriveJoystickButton(Constants.Buttons.TOGGLE_SPEED_SCALE);
        return false;
    }


    // SPECIFIC JOYSTICK STICK METHODS
    public double getLeftDriveSpeed()
    {
        double driveSpeed = joystickDriveLeft.getY() * getDriveScaledSpeed();
        if (Math.abs(driveSpeed) < Constants.DRIVE_SPEED_CUTOFF)
            driveSpeed = 0;
        return driveSpeed;
    }
    public double getRightDriveSpeed()
    {
        double driveSpeed = joystickDriveRight.getY() * getDriveScaledSpeed();
        if (Math.abs(driveSpeed) < Constants.DRIVE_SPEED_CUTOFF)
            driveSpeed = 0;
        return driveSpeed;
    }
    public double getZAxisSpeed()
    {
        return joystickDriveLeft.getZ();
    }
    public double getDriveScaledSpeed()
    {
        if (shouldToggleSpeedScale())
        {
            robotHandler.stateHandler.toggleSpeedScale();
        }

        robotHandler.shuffleboardHandler.printBooleanToWidget("Scaling Speed", robotHandler.stateHandler.isSpeedScaleEnabled);

        if (robotHandler.stateHandler.isSpeedScaleEnabled)
        {
            double scaledSpeed = RobotUtils.normalizeJoystick(getZAxisSpeed());

            scaledSpeed += 0.6d;

            scaledSpeed = Math.min(1, scaledSpeed);

            robotHandler.shuffleboardHandler.printDoubleToWidget("Scaled Speed", scaledSpeed);
            
            return scaledSpeed;
        }
        else
        {
            return 1d;
        }
    }
    public double getManStickSpeed()
    {
        double manStickSpeed = joystickManipulator.getY();
        if (Math.abs(manStickSpeed) < Constants.MAN_STICK_SPEED_CUTOFF)
            manStickSpeed = 0;
        //else
            //manStickSpeed = Math.signum(manStickSpeed);
        return manStickSpeed;
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