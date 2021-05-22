package frc.robot.types;

public class ButtonLocation {
    public int buttonIndex;
    public JoystickType joystickType;

    public ButtonLocation(int buttonIndex, JoystickType joystickType)
    {
        this.buttonIndex = buttonIndex;
        this.joystickType = joystickType;
    }

    public boolean compare(ButtonLocation buttonLocation)
    {
        return buttonIndex == buttonLocation.buttonIndex && joystickType == buttonLocation.joystickType;
    }
    public boolean compare(int buttonIndex, JoystickType joystickType)
    {
        return this.buttonIndex == buttonIndex && this.joystickType == joystickType;
    }
}
