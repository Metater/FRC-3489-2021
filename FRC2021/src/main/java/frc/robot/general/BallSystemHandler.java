package frc.robot.general;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;

public class BallSystemHandler {
    
    private RobotHandler robotHandler;

    Solenoid intakeSolenoid = new Solenoid(Constants.Solenoids.PCM_NUMBER, Constants.Solenoids.INTAKE_ROLLER);

    public BallSystemHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }
}
