package frc.robot.general;

import edu.wpi.first.wpilibj.Timer;

/**
* The state of the robot controller
*/
public class StateHandler {

    private RobotHandler robotHandler;

    // Switching Front Stuff
    public boolean isIntakeSideFront = true;
    public double lastSwitchTime;

    // Ball System Stuff
    public boolean isIntakeExtened = false;
    public double lastIntakeToggleTime;
    public int ballCount = 0;
    public int encoderTarget = 0;
    public boolean isIndexingBall = false;
    public IntakeIndexState intakeState = IntakeIndexState.Deactivated;
    public enum IntakeIndexState
    {
        Deactivated,
        Starting,
        Indexing,
        Stopping
    }


    public boolean commitingToUnjam = false;
    public double lastCommitToUnjamTime;


    public StateHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void switchFront()
    {
        isIntakeSideFront = !isIntakeSideFront;
        lastSwitchTime = Timer.getFPGATimestamp();
    }

    public void toggleIntake()
    {
        isIntakeExtened = !isIntakeExtened;
        lastIntakeToggleTime = Timer.getFPGATimestamp();
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
        isIntakeExtened = false;

        ballCount = 0;

        isIndexingBall = false;

        commitingToUnjam = false;
    }
}
