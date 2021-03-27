package frc.robot.types;

import java.util.function.Function;

public class ButtonAction {
    public Function<TriggeredButtonState, Void> trigger;

    public ButtonAction(Function<TriggeredButtonState, Void> trigger)
    {
        this.trigger = trigger;
    }
}
