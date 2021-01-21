package frc.robot.general;

import edu.wpi.first.wpilibj.Timer;

/**
* The state of the robot controller
*/
public class StateHandler {

    private RobotHandler robotHandler;

    public boolean isInputSideFront = true;
    public double lastSwitchTime;

    public boolean isIntakeExtened = false;
    public double lastIntakeToggleTime;

    public StateHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void switchFront()
    {
        isInputSideFront = !isInputSideFront;
        lastSwitchTime = Timer.getFPGATimestamp();
    }

    public void toggleIntake()
    {
        isIntakeExtened = !isIntakeExtened;
        lastIntakeToggleTime = Timer.getFPGATimestamp();
    }
}
