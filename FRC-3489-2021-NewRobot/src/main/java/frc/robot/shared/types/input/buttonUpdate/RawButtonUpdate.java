package frc.robot.shared.types.input.buttonUpdate;

import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateType;
import frc.robot.shared.types.robot.PeriodicType;

public class RawButtonUpdate extends BaseButtonUpdate {
    

    public RawButtonUpdate(IButtonListener trigger, PeriodicType periodicType, String buttonUpdateName, ButtonLocation buttonLocation)
    {
        buttonUpdateType = ButtonUpdateType.Raw;
        this.trigger = trigger;
        this.periodicType = periodicType;
        this.buttonUpdateName = buttonUpdateName;
        this.buttonLocation = buttonLocation;
    }
}
