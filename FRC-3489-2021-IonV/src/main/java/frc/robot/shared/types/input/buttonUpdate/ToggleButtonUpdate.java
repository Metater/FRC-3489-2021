package frc.robot.shared.types.input.buttonUpdate;

import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateType;
import frc.robot.shared.types.robot.PeriodicType;

public class ToggleButtonUpdate extends BaseButtonUpdate {

    public double triggerCooldown;

    public boolean lastState = false;
    

    public ToggleButtonUpdate(IButtonListener trigger, PeriodicType periodicType, String buttonUpdateName, ButtonLocation buttonLocation, double triggerCooldown)
    {
        buttonUpdateType = ButtonUpdateType.Toggle;
        this.trigger = trigger;
        this.periodicType = periodicType;
        this.buttonUpdateName = buttonUpdateName;
        this.buttonLocation = buttonLocation;
        this.triggerCooldown = triggerCooldown;
    }
}
