package frc.robot.shared.types.input.buttonUpdate;

import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonState;
import frc.robot.shared.types.input.ButtonUpdateType;
import frc.robot.shared.types.robot.PeriodicType;

public abstract class BaseButtonUpdate {

    public ButtonUpdateType buttonUpdateType;
    public IButtonListener trigger;
    public PeriodicType periodicType;
    public String buttonUpdateName;
    public ButtonLocation buttonLocation;

    public ButtonState buttonState;
    
    public void update()
    {
        trigger.update(this);
    }
}
