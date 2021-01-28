package frc.robot.auto;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.general.*;

public class DriveStraightAI extends AutoInstruction {

    private RobotHandler robotHandler;

    public double speed = 0;
    
    public DriveStraightAI(RobotHandler robotHandler, double runTime, double speed)
    {  
        System.out.println("AutoThingYese");

        this.robotHandler = robotHandler;

        this.speed = speed;

        this.runTime = runTime;
        targetTime = Timer.getFPGATimestamp() + runTime;
    }

    @Override
    public void run()
    {
        System.out.println("idsuisduedhusudhsduhsdhsudhusduhe");
        if (!isFinished())
        {
            robotHandler.driveHandler.differentialDrive.tankDrive(speed, speed);
        }
        else
        {
            robotHandler.driveHandler.differentialDrive.tankDrive(0, 0);
        }
    }
}
