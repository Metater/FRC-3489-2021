package frc.robot.shared.types.input.buttonUpdate;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.ButtonUpdateType;
import frc.robot.shared.types.robot.PeriodicType;

public abstract class BaseButtonUpdate {

    public ButtonUpdateType buttonUpdateType;
    public IButtonListener trigger;
    public PeriodicType periodicType;
    public String buttonUpdateName;
    public ButtonLocation buttonLocation;

    public ButtonUpdateEventType buttonUpdateEventType;
    public double lastUpdateTime = -1;
    
    public void update(ButtonUpdateEventType buttonUpdateEventType)
    {
        this.buttonUpdateEventType = buttonUpdateEventType;
        trigger.update(this);
        lastUpdateTime = Timer.getFPGATimestamp();
    }
}
