package frc.robot.types.buttonTriggerCriteria;

import frc.robot.interfaces.IButtonListener;
import frc.robot.types.ButtonLastPressData;
import frc.robot.types.ButtonLocation;
import frc.robot.types.PeriodicType;

public abstract class BaseButtonTriggerCriteria {

    public ButtonTriggerCriteriaType buttonTriggerCriteriaType;
    public IButtonListener trigger;
    public PeriodicType periodicType;
    public String buttonTriggerName;
    public ButtonLocation buttonLocation;

    public ButtonLastPressData buttonLastPressData;
}
