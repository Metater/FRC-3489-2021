package frc.robot.types;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.types.buttonTriggerCriteria.BaseButtonTriggerCriteria;

public class ButtonPress {

    public BaseButtonTriggerCriteria buttonTriggerCriteria;
    public double lastPressTime;

    public ButtonPress(BaseButtonTriggerCriteria buttonTriggerCriteria)
    {
        this.buttonTriggerCriteria = buttonTriggerCriteria;
        this.lastPressTime = buttonTriggerCriteria.buttonLastPressData.lastPressTime;
    }

    public double getTimeSinceLastPress()
    {
        return Timer.getFPGATimestamp() - lastPressTime;
    }
}
