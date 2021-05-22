package frc.robot.types;

import edu.wpi.first.wpilibj.Timer;

public class ButtonLastPressData {
    
    public double lastPressTime = -1;
    
    public ButtonLastPressData()
    {
        
    }

    public double getTimeSinceLastPress()
    {
        return Timer.getFPGATimestamp() - lastPressTime;
    }
    public boolean getTimeSinceLastPressValid(double time)
    {
        return getTimeSinceLastPress() >= time;
    }
}
