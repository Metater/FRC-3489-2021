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
    public double getManStickSpeed()
    {
        double manStickSpeed = deviceContainer.joystickManipulator.getY();
        if (Math.abs(manStickSpeed) < Constants.ManipulatorJoystickThreshold)
            manStickSpeed = 0;
        return manStickSpeed;
    }

}
