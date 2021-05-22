package frc.robot.types.buttonTriggerCriteria;

import frc.robot.types.ButtonLocation;

public class RawButtonTriggerCriteria extends BaseButtonTriggerCriteria {
    
    public RawButtonTriggerCriteria(String buttonTriggerName, ButtonLocation buttonLocation)
    {
        buttonTriggerCriteriaType = ButtonTriggerCriteriaType.Raw;
        this.buttonTriggerName = buttonTriggerName;
        this.buttonLocation = buttonLocation;
    }
}
