package frc.robot.types;

import edu.wpi.first.wpilibj.Timer;

public class ButtonTriggerState {
    
    public double lastPressTime = -1;
    public double lastTriggerTime = -1;
    
    public ButtonTriggerState()
    {
        
    }

    public void press()
    {
        lastPressTime = Timer.getFPGATimestamp();
    }
    public void trigger()
    {
        lastTriggerTime = Timer.getFPGATimestamp();
    }

    public double getTimeSinceLastPress()
    {
        return Timer.getFPGATimestamp() - lastPressTime;
    }
    public boolean getTimeSinceLastPressValid(double time)
    {
        return getTimeSinceLastPress() >= time;
    }
    
    public double getTimeSinceLastTrigger()
    {
        return Timer.getFPGATimestamp() - lastTriggerTime;
    }
    public boolean getTimeSinceLastTriggerValid(double time)
    {
        return getTimeSinceLastTrigger() >= time;
    }
}
