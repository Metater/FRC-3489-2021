package frc.robot.types.buttonTriggerCriteria;

import frc.robot.interfaces.IButtonListener;
import frc.robot.types.ButtonLocation;

public abstract class BaseButtonTriggerCriteria {

    public ButtonTriggerCriteriaType buttonTriggerCriteriaType;
    public IButtonListener trigger;
    public String buttonTriggerName;
    public ButtonLocation buttonLocation;
}
