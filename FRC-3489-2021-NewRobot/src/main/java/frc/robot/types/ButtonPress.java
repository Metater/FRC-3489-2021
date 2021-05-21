package frc.robot.types;

import java.util.Map;

import edu.wpi.first.wpilibj.Timer;

public class ButtonPress {

    public int buttonIndex;
    public JoystickType joystickType;
    public PeriodicType periodicType;
    public Map<PeriodicType, Double> lastPressTimes;

    public ButtonPress(int buttonIndex, JoystickType joystickType, Map<PeriodicType, Double> lastPressTimes)
    {
        this.buttonIndex = buttonIndex;
        this.joystickType = joystickType;
        this.lastPressTimes = lastPressTimes;
    }

    public double getTimeBetweenLastPress()
    {
        return Timer.getFPGATimestamp() - lastPressTimes.get(periodicType);
    }
}
