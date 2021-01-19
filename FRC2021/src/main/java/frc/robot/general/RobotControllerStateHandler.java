package frc.robot.general;

import edu.wpi.first.wpilibj.Timer;

/**
* The state of the robot controller
*/
public class RobotControllerStateHandler {
    public boolean isInputSideFront = true;
    public double lastSwitchTime;

    public void switchFront()
    {
        isInputSideFront = !isInputSideFront;
        lastSwitchTime = Timer.getFPGATimestamp();
    }
}
