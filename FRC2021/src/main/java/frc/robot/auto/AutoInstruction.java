package frc.robot.auto;

import edu.wpi.first.wpilibj.Timer;

public class AutoInstruction {

    public double targetTime;
    public double runTime;

    public void run()
    {
        // Override this
    }

    public boolean isFinished()
    {
        if (targetTime <= Timer.getFPGATimestamp())
            return true;
        return false;
    }
}