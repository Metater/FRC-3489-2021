package frc.robot.types;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.types.buttonTriggerCriteria.BaseButtonTriggerCriteria;

public class ButtonPress {

    public BaseButtonTriggerCriteria buttonTriggerCriteria;
    public double lastPressTime;

    public ButtonPress(BaseButtonTriggerCriteria buttonTriggerCriteria, double lastPressTime)
    {
        this.buttonTriggerCriteria = buttonTriggerCriteria;
        this.lastPressTime = lastPressTime;
    }

    public double getTimeBetweenLastPress()
    {
        return Timer.getFPGATimestamp() - lastPressTime;
    }
}
