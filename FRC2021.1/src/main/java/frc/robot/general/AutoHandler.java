package frc.robot.general;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.auto.*;

public class AutoHandler {

    private RobotHandler robotHandler;

    private double stopTime = 0;

    private boolean hasSetTime = false;

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

            //drive straight

                tankDriveTime(0.5, 0.5, 1);
                break;

            case 1:

            //turn left 45

                tankDriveTime(-0.5, 0.5, 0.9);
                break;

            case 2:

            //drive straight

                tankDriveTime(0.5, 0.5, 2);
                break;

            case 3:

            //turn right 45

                tankDriveTime(0.5, -0.5, 1.1);
                break;

            case 4:

            //drive straight

                tankDriveTime(0.6, 0.6, 3.5);
                break;

            case 5:

            //turn right 45

            tankDriveTime(0.5, -0.5, 1);
                break;

            case 6:

                //drive straight

                tankDriveTime(0.5, 0.5, 1);
                break;

            case 7:

                 //stop motor

                 stopTankDrive();
                 break; 

            case 8:
                break;
        }
    }

    public void stopTankDrive()
    {
        robotHandler.driveHandler.differentialDrive.tankDrive(0, 0);
        step++;
    }

    public void tankDriveTime(double leftSpeed, double rightSpeed, double runTime)
    {
        if (!hasSetTime)
        {
            setStopTime(runTime);
            System.out.println("Set stop time");
        }
        
        if (stopTime >= Timer.getFPGATimestamp())
        {
            robotHandler.driveHandler.differentialDrive.tankDrive(leftSpeed, rightSpeed);
            System.out.println("Driving, on step: " + step);
        }
        else
        {
            step++;
            hasSetTime = false;
            System.out.println("Finished, increment step");
        }
    }

    public void setStopTime(double runTime)
    {
        stopTime = Timer.getFPGATimestamp() + runTime;
        hasSetTime = true;
    }
}
