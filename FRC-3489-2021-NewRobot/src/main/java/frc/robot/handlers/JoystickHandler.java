package frc.robot.handlers;

import frc.robot.Constants;

public class JoystickHandler extends BaseHandler {
    
    public JoystickHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
    }

    public double getLeftDriveTrain()
    {
        double trainRaw = deviceContainer.joystickDriveLeft.getY();
        if (Math.abs(trainRaw) < Constants.DriveJoystickThreshold)
            return 0;
        return -trainRaw;
    }
    public double getRightDriveTrain()
    {
        double trainRaw = deviceContainer.joystickDriveRight.getY();
        if (Math.abs(trainRaw) < Constants.DriveJoystickThreshold)
            return 0;
        return -trainRaw;
    }

    public double getShooterAdjust()
    {
        if (Math.abs(deviceContainer.joystickManipulator.getY()) < Constants.ManipulatorJoystickThreshold)
            return 0;
        return -0.005 * deviceContainer.joystickManipulator.getY();
    }

    public double getTurretRotateSpeed()
    {
        if (Math.abs(deviceContainer.joystickManipulator.getZ()) < Constants.ManipulatorJoystickThreshold)
            return 0;
        return -0.2 * deviceContainer.joystickManipulator.getZ();
    }
}
