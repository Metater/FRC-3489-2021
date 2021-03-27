package frc.robot.handlers;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Timer;

/**
* The state of the robot controller
*/
public class StateHandler {

    private RobotHandler robotHandler;

    private Map<String, Boolean> states = new HashMap<String, Boolean>();

    public StateHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public boolean isIntakeSideFront = true;
    public double lastSwitchFrontTime;
    public void switchFront()
    {
        isIntakeSideFront = !isIntakeSideFront;
        lastSwitchFrontTime = Timer.getFPGATimestamp();
    }

    public boolean isSpeedScaleEnabled = false;
    public double lastSpeedScaleToggleTime;
    public void toggleSpeedScale()
    {
        isSpeedScaleEnabled = !isSpeedScaleEnabled;
        lastSpeedScaleToggleTime = Timer.getFPGATimestamp();
    }

    public boolean isRecording = false;
    public double lastRecordingToggleTime;
    public void toggleRecording()
    {
        isRecording = !isRecording;
        lastRecordingToggleTime = Timer.getFPGATimestamp();
    }
    public boolean isPlaying = false;
    public double lastPlayerToggleTime;
    public void togglePlayer()
    {
        isPlaying = !isPlaying;
        lastPlayerToggleTime = Timer.getFPGATimestamp();
    }

    public void reset()
    {
        isIntakeSideFront = true;
        isSpeedScaleEnabled = false;
        isRecording = false;
        isPlaying = false;

        lastSwitchFrontTime = 0;
        lastSpeedScaleToggleTime = 0;
        lastRecordingToggleTime = 0;
        lastPlayerToggleTime = 0;
    }
}
