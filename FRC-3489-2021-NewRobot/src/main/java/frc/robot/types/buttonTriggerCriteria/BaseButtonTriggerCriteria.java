package frc.robot.types.buttonTriggerCriteria;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.interfaces.IButtonListener;
import frc.robot.types.ButtonTriggerState;
import frc.robot.types.ButtonLocation;
import frc.robot.types.ButtonPress;
import frc.robot.types.PeriodicType;

public abstract class BaseButtonTriggerCriteria {

    public ButtonTriggerCriteriaType buttonTriggerCriteriaType;
    public IButtonListener trigger;
    public PeriodicType periodicType;
    public String buttonTriggerName;
    public ButtonLocation buttonLocation;

    public ButtonTriggerState buttonTriggerState;

    public void trigger(ButtonPress buttonPress)
    {
        trigger.buttonTriggered(buttonPress);
        buttonTriggerState.trigger();
    }
    public void trigger()
    {
        trigger(new ButtonPress(this));
    }
}
