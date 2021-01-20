package frc.robot.general;

public class RobotHandler {
    public DriveHandler driveHandler;
    public StateHandler stateHandler;
    public InputHandler inputHandler;

    public RobotHandler()
    {
        driveHandler = new DriveHandler(this);
        stateHandler = new StateHandler();
        inputHandler = new InputHandler();
    }

    public void initTeleop()
    {
        
    }

    public void cycleTeleop()
    {
        driveHandler.cycle();
    }
}
