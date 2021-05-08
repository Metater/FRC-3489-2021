package frc.robot.types;

public class ButtonLocation {
    public JoystickType assignedJoystick;
    public int buttonIndex;

    public ButtonLocation(JoystickType assignedJoystick, int buttonIndex)
    {
        this.assignedJoystick = assignedJoystick;
        this.buttonIndex = buttonIndex;
    }
}
