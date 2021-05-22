package frc.robot.types;

import edu.wpi.first.wpilibj.Timer;

public class ButtonPress {

    public ButtonLocation buttonLocation;
    public PeriodicType periodicType;
    public double lastPressTime;

    public ButtonPress(ButtonLocation buttonLocation, PeriodicType periodicType, double lastPressTime)
    {
        this.buttonLocation = buttonLocation;
        this.periodicType = periodicType;
        this.lastPressTime = lastPressTime;
    }

    public double getTimeBetweenLastPress()
    {
        return Timer.getFPGATimestamp() - lastPressTime;
    }
}
