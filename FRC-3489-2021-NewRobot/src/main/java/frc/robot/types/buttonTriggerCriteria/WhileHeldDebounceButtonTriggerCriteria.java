package frc.robot.types.buttonTriggerCriteria;

import frc.robot.interfaces.IButtonListener;
import frc.robot.types.ButtonLocation;
import frc.robot.types.PeriodicType;

public class WhileHeldDebounceButtonTriggerCriteria extends BaseButtonTriggerCriteria {

    public boolean isHeld = false;
    
    public WhileHeldDebounceButtonTriggerCriteria(IButtonListener trigger, PeriodicType periodicType, String buttonTriggerName, ButtonLocation buttonLocation)
    {
        buttonTriggerCriteriaType = ButtonTriggerCriteriaType.PressedDebounce;
        this.trigger = trigger;
        this.periodicType = periodicType;
        this.buttonTriggerName = buttonTriggerName;
        this.buttonLocation = buttonLocation;
    }
}
