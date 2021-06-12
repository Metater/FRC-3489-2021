package frc.robot.types.buttonTriggerCriteria;

import frc.robot.interfaces.IButtonListener;
import frc.robot.types.ButtonLocation;
import frc.robot.types.PeriodicType;

public class WhileNotHeldDebounceButtonTriggerCriteria extends BaseButtonTriggerCriteria {

    public boolean isHeld = false;
    
    public WhileNotHeldDebounceButtonTriggerCriteria(IButtonListener trigger, PeriodicType periodicType, String buttonTriggerName, ButtonLocation buttonLocation)
    {
        buttonTriggerCriteriaType = ButtonTriggerCriteriaType.WhileNotHeldDebounce;
        this.trigger = trigger;
        this.periodicType = periodicType;
        this.buttonTriggerName = buttonTriggerName;
        this.buttonLocation = buttonLocation;
    }
}
