package frc.robot.handlers;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants;

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
        return trainRaw * -1;
    }
    public double getRightDriveTrain()
    {
        double trainRaw = robotHandler.deviceContainer.joystickDriveRight.getY();
        if (Math.abs(trainRaw) < Constants.DriveJoystickThreshold)
            return 0;
        return trainRaw * -1;
    }
}
