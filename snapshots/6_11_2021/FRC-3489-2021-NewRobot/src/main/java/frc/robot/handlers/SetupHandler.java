package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.containers.DeviceContainer;
import frc.robot.interfaces.IRobotListener;

public class SetupHandler extends BaseHandler implements IRobotListener {


    public SetupHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addRobotListener(this);
    }

    public void robotInit()
    {
        DeviceContainer deviceContainer =  robotHandler.deviceContainer;

        // Follow Motors
        deviceContainer.driveBackLeft.follow(deviceContainer.driveFrontLeft);
        deviceContainer.driveBackRight.follow(deviceContainer.driveFrontRight);

        // Disable Safeties
        deviceContainer.driveFrontLeft.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.driveFrontRight.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.driveBackLeft.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.driveBackRight.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.turretRotate.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.turretShooterLeft.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.turretShooterRight.setSafetyEnabled(Constants.SafetiesEnabled);
    }

    public void robotPeriodic()
    {

    }
}
