package frc.robot.configs;

import java.util.List;

import frc.robot.types.ButtonAction;
import frc.robot.types.ButtonLocation;
import frc.robot.types.PeriodicType;

public class ButtonConfig {
    public ButtonAction buttonAction;
    public PeriodicType pollOn;
    public double resetTime = 0;

    public List<ButtonLocation> buttonLocations;

    public ButtonConfig(ButtonAction buttonAction, PeriodicType pollOn, double resetTime, List<ButtonLocation> buttonLocations)
    {
        this.buttonAction = buttonAction;
        this.pollOn = pollOn;
        this.resetTime = resetTime;
        this.buttonLocations = buttonLocations;
    }
}