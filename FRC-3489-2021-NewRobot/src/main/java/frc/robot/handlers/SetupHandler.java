package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.shared.interfaces.*;

public class SetupHandler extends BaseHandler implements IRobotListener {


    public SetupHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
    }

    public void robotInit()
    {
        // Follow Motors
        deviceContainer.driveBackLeft.follow(deviceContainer.driveFrontLeft);
        deviceContainer.driveBackRight.follow(deviceContainer.driveFrontRight);

        // Disable Safeties
        deviceContainer.driveFrontLeft.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.driveFrontRight.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.driveBackLeft.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.driveBackRight.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.turretRotate.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.shooterLeft.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.shooterRight.setSafetyEnabled(Constants.SafetiesEnabled);
        deviceContainer.cellevator.setSafetyEnabled(Constants.SafetiesEnabled);

        //Default Pneumatic Positions
        deviceContainer.intakeArm.set(false);
    }

    public void robotPeriodic()
    {

    }
}
