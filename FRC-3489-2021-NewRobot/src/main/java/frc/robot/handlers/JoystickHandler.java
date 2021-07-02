package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.shared.handlers.BaseHandler;

public class JoystickHandler extends BaseHandler {
    
    public JoystickHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public double getLeftDriveTrain()
    {
        double trainRaw = robotHandler.deviceContainer.joystickDriveLeft.getY();
        if (Math.abs(trainRaw) < Constants.DriveJoystickThreshold)
            return 0;
        return -trainRaw;
    }
    public double getRightDriveTrain()
    {
        double trainRaw = robotHandler.deviceContainer.joystickDriveRight.getY();
        if (Math.abs(trainRaw) < Constants.DriveJoystickThreshold)
            return 0;
        return -trainRaw;
    }

    public double getShooterAdjust()
    {
        if (Math.abs(robotHandler.deviceContainer.joystickManipulator.getY()) < Constants.ManipulatorJoystickThreshold)
            return 0;
        return -0.005 * robotHandler.deviceContainer.joystickManipulator.getY();
    }

    public double getTurretRotateSpeed()
    {
        if (Math.abs(robotHandler.deviceContainer.joystickManipulator.getZ()) < Constants.ManipulatorJoystickThreshold)
            return 0;
        return -0.2 * robotHandler.deviceContainer.joystickManipulator.getZ();
    }
}
