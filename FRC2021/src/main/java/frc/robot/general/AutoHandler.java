package frc.robot.general;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.auto.*;

public class AutoHandler {

    private RobotHandler robotHandler;

    private double stopTime = 0;

    private boolean hasSetTime = false;

    private int step = 0;

    public AutoHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void autonomousInit()
    {
        robotHandler.driveHandler.differentialDrive.setSafetyEnabled(false);
    }

    public void autonomousPeriodic()
    {
        robotHandler.driveHandler.differentialDrive.setSafetyEnabled(false);

        switch (step) {
            case 0:
                tankDriveTime(0.5, 0.5, 3);
                break;
            case 1:
                tankDriveTime(-0.5, 0.5, 0.5);
                break;
            case 2:

                break;
        }
    }

    public void tankDriveTime(double leftSpeed, double rightSpeed, double runTime)
    {
        if (!hasSetTime)
            setStopTime(runTime);
        
        if (stopTime >= Timer.getFPGATimestamp())
        {
            robotHandler.driveHandler.differentialDrive.tankDrive(leftSpeed, rightSpeed);
        }
        else
        {
            step++;
            hasSetTime = false;
        }
    }

    public void setStopTime(double runTime)
    {
        stopTime = Timer.getFPGATimestamp() + runTime;
        hasSetTime = true;
    }
}
