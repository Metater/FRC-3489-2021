package frc.robot.types.buttonTriggerCriteria;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.interfaces.IButtonListener;
import frc.robot.types.ButtonLocation;
import frc.robot.types.PeriodicType;

public class ReleasedDebounceButtonTriggerCriteria extends BaseButtonTriggerCriteria {

    public double lastReleaseTime = -1;
    public boolean releaseScheduled = false;
    
    public ReleasedDebounceButtonTriggerCriteria(IButtonListener trigger, PeriodicType periodicType, String buttonTriggerName, ButtonLocation buttonLocation)
    {
        buttonTriggerCriteriaType = ButtonTriggerCriteriaType.ReleasedDebounce;
        this.trigger = trigger;
        this.periodicType = periodicType;
        this.buttonTriggerName = buttonTriggerName;
        this.buttonLocation = buttonLocation;
    }

    public double getTimeSinceLastRelease()
    {
        return Timer.getFPGATimestamp() - lastReleaseTime;
    }
}
