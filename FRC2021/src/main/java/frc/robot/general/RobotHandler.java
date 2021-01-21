package frc.robot.general;

public class RobotHandler {

    public DriveHandler driveHandler;
    public StateHandler stateHandler;
    public InputHandler inputHandler;
    public BallSystemHandler ballSystemHandler;
    public HookHandler hookHandler;

    public TestHandler testHandler;

    public RobotHandler()
    {
        driveHandler = new DriveHandler(this);
        stateHandler = new StateHandler(this);
        inputHandler = new InputHandler(this);
        ballSystemHandler = new BallSystemHandler(this);
        hookHandler = new HookHandler(this);

        testHandler = new TestHandler(this);
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        testHandler.teleopPeriodic();
        //driveHandler.cycle();
        //ballSystemHandler.cycle();
    }
}
