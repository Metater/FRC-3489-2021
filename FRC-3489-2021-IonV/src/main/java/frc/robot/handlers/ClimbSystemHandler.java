package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.shared.interfaces.ITeleopListener;

public class ClimbSystemHandler extends BaseHandler implements ITeleopListener {
 
    public ClimbSystemHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
    }

    public void teleopInit()
    {
        setSolenoids(false);
    }

    public void teleopPeriodic() 
    {
        setSolenoids(shouldClimb());
        deviceContainer.winch.set(getWinchSpeed());
    }

    private boolean shouldClimb()
    {
        return deviceContainer.joystickManipulator.getRawButton(Constants.Button.Climb1) && deviceContainer.joystickManipulator.getRawButton(Constants.Button.Climb2);
    }

    private double getWinchSpeed()
    {
        if (deviceContainer.joystickDriveLeft.getPOV() == 180 && deviceContainer.joystickDriveRight.getPOV() == 180) return 0.5;
        else if (deviceContainer.joystickDriveLeft.getPOV() == 0 && deviceContainer.joystickDriveRight.getPOV() == 0) return -0.3;
        else return 0;
    }

    private void setSolenoids(boolean value)
    {
        deviceContainer.winchSolenoidLeft.set(value);
        deviceContainer.winchSolenoidRight.set(!value);
    }
}
