package frc.robot.general;

import edu.wpi.first.wpilibj.Timer;

/**
* The state of the robot controller
*/
public class StateHandler {

    private RobotHandler robotHandler;

    public StateHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    // Switching Front Stuff
    public boolean isIntakeSideFront = true;
    public double lastSwitchTime;


    public boolean commitingToUnjam = false;
    public double lastCommitToUnjamTime;

    public void switchFront()
    {
        isIntakeSideFront = !isIntakeSideFront;
        lastSwitchTime = Timer.getFPGATimestamp();
    }

    public void toggleIntake()
    {
        robotHandler.ballSystemHandler.isIntakeExtended = !robotHandler.ballSystemHandler.isIntakeExtended;
        robotHandler.ballSystemHandler.lastIntakeToggleTime = Timer.getFPGATimestamp();
    }

    public void commitToUnjam()
    {
        commitingToUnjam = true;
        lastCommitToUnjamTime = Timer.getFPGATimestamp();
    }
    public void uncommitToUnjam()
    {
        commitingToUnjam = false;
    }

    public void reset()
    {
        isIntakeSideFront = true;
        robotHandler.ballSystemHandler.isIntakeExtended = false;

        commitingToUnjam = false;
    }
}
