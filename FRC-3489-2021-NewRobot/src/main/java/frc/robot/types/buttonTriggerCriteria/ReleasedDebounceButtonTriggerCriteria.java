package frc.robot.types.buttonTriggerCriteria;

import frc.robot.interfaces.IButtonListener;
import frc.robot.types.ButtonLocation;
import frc.robot.types.PeriodicType;

public class ReleasedDebounceButtonTriggerCriteria extends BaseButtonTriggerCriteria {
    
    public ReleasedDebounceButtonTriggerCriteria(IButtonListener trigger, PeriodicType periodicType, String buttonTriggerName, ButtonLocation buttonLocation)
    {
        buttonTriggerCriteriaType = ButtonTriggerCriteriaType.ReleasedDebounce;
        this.trigger = trigger;
        this.periodicType = periodicType;
        this.buttonTriggerName = buttonTriggerName;
        this.buttonLocation = buttonLocation;
    }
}
