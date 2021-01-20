package frc.robot.general;

import edu.wpi.first.wpilibj.Timer;

/**
* The state of the robot controller
*/
public class StateHandler {

    private RobotHandler robotHandler;

    public boolean isInputSideFront = true;
    public double lastSwitchTime;

    public StateHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void switchFront()
    {
        isInputSideFront = !isInputSideFront;
        lastSwitchTime = Timer.getFPGATimestamp();
    }
}
