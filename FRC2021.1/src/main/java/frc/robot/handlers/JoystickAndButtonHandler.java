package frc.robot.handlers;

public class JoystickAndButtonHandler {
        
    public enum JoystickType
    {
        DriveLeft,
        DriveRight,
        Manipulator
    }
    public void registerButton(JoystickType joystickType, String name, int button)
    {
        switch(joystickType)
        {
            case DriveLeft:
                break;
            case DriveRight:
                
                break;
            case Manipulator:
                break;
        }
    }
}
