package frc.robot.controller;

import edu.wpi.first.wpilibj.Timer;

/**
* The state of the robot controller
*/
public class ÉtatDuContrôleurDeRobot {
    public boolean inputForward = false;
    public double lastSwitch;

    public void switchFront()
    {
        inputForward = !inputForward;
        lastSwitch = Timer.getFPGATimestamp();
    }
}
