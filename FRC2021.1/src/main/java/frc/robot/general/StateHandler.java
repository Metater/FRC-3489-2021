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
    public boolean isOutakeSideFront = false;
    public double lastSwitchFrontTime;

    public void switchFront()
    {
        isOutakeSideFront = !isOutakeSideFront;
        lastSwitchFrontTime = Timer.getFPGATimestamp();
    }
    
    public boolean scaleSpeedEnabled = false;
    public double lastSpeedScaleToggleTime;

    public void toggleSpeedScale()
    {
        scaleSpeedEnabled = !scaleSpeedEnabled;
        lastSpeedScaleToggleTime = Timer.getFPGATimestamp();
    }

    public void reset()
    {
        isOutakeSideFront = true;
    }
}
