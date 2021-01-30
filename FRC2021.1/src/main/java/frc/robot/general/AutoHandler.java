package frc.robot.general;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.auto.*;

public class AutoHandler {

    private RobotHandler robotHandler;

    private double startClicks = 0;
    private double stopClicks = 0;

    private boolean hasSetClicks = false;

    private int step = 0;

    /*
    private ArrayList<AutoInstruction> slalom =v
    {
        new AutoInstruction(),
        new AutoInstruction(),
        new AutoInstruction()
    };
    */

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
                tankDriveClicks(0.5, 0.5, 20);
                break;

            case 1:
                stopTankDrive();
                break;
        }
    }

    public void stopTankDrive()
    {
        robotHandler.driveHandler.differentialDrive.tankDrive(0, 0);
        step++;
    }

    public void tankDriveClicks(double leftSpeed, double rightSpeed, double clicks)
    {
        if (!hasSetClicks)
        {
            startClicks = robotHandler.driveHandler._leftFront.getSelectedSensorPosition();
            stopClicks = robotHandler.driveHandler._leftFront.getSelectedSensorPosition() + clicks; // Collides low to high number
            hasSetClicks = true;
            System.out.println("Set stop time");
        }
        if (robotHandler.driveHandler._leftFront.getSelectedSensorPosition() <= stopClicks)
        {
            robotHandler.driveHandler.differentialDrive.tankDrive(leftSpeed, rightSpeed);
            System.out.println("Driving, on step: " + step);
        }
        else
        {
            step++;
            hasSetClicks = false;
            System.out.println("Finished, increment step");
        }
    }
}
