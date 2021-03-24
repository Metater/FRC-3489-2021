package frc.robot.configs;

import java.util.function.Function;

public class ButtonConfig {
    public enum JoystickType
    {
        DriveLeft,
        DriveRight,
        Manipulator
    }
    public enum ButtonState
    {
        Pressed,
        Held,
        Released
    }

    public JoystickType assignedJoystick;
    public int buttonIndex;
    public Function<ButtonState, Void> trigger;
}