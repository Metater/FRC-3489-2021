package frc.robot.types.buttonTriggerCriteria;

import frc.robot.types.ButtonLocation;

public class ReleasedDebounceButtonTriggerCriteria extends BaseButtonTriggerCriteria {
    
    public ReleasedDebounceButtonTriggerCriteria(String buttonTriggerName, ButtonLocation buttonLocation)
    {
        buttonTriggerCriteriaType = ButtonTriggerCriteriaType.ReleasedDebounce;
        this.buttonTriggerName = buttonTriggerName;
        this.buttonLocation = buttonLocation;
    }
}
