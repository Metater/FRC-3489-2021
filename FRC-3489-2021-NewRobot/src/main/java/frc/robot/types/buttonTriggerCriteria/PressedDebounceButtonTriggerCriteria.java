package frc.robot.types.buttonTriggerCriteria;

import frc.robot.interfaces.IButtonListener;
import frc.robot.types.ButtonLocation;

public class PressedDebounceButtonTriggerCriteria extends BaseButtonTriggerCriteria {
    
    public PressedDebounceButtonTriggerCriteria(IButtonListener trigger, String buttonTriggerName, ButtonLocation buttonLocation)
    {
        buttonTriggerCriteriaType = ButtonTriggerCriteriaType.PressedDebounce;
        this.trigger = trigger;
        this.buttonTriggerName = buttonTriggerName;
        this.buttonLocation = buttonLocation;
    }
}
