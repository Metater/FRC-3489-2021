package frc.robot.controller;

import edu.wpi.first.wpilibj.Timer;

/**
* The state of the robot controller
*/
public class ÉtatDuContrôleurDeRobot {
    public boolean isInputForward = false;
    public double lastSwitch;

    public void switchFront()
    {
        isInputForward = !isInputForward;
        lastSwitch = Timer.getFPGATimestamp();
    }
}
