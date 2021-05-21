package frc.robot.handlers;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.interfaces.ITeleopListener;

public class DriveHandler extends BaseHandler implements ITeleopListener {

    public DifferentialDrive differentialDrive;
    
    public DriveHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addTeleopListener(this);
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {

    }
}
