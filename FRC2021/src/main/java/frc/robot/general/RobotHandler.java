package frc.robot.general;

public class RobotHandler {

    public DriveHandler driveHandler;
    public StateHandler stateHandler;
    public InputHandler inputHandler;
    public BallSystemHandler ballSystemHandler;
    public HookHandler hookHandler;
    public ShuffleboardHandler shuffleboardHandler;
    public CameraHandler cameraHandler;

    public AutoHandler autoHandler;

    public TestHandler testHandler;

    public RobotHandler()
    {
        driveHandler = new DriveHandler(this);
        stateHandler = new StateHandler(this);
        inputHandler = new InputHandler(this);
        ballSystemHandler = new BallSystemHandler(this);
        hookHandler = new HookHandler(this);
        //shuffleboardHandler = new ShuffleboardHandler(this);
        cameraHandler = new CameraHandler(this);

        autoHandler = new AutoHandler(this);

        //testHandler = new TestHandler(this);
    }

    public void disabledInit() // Disabled isn't it?
    {
        //ballSystemHandler.reset();
    }

    public void teleopInit() // Teleop isn't it?
    {
        stateHandler.reset();
    }

    public void teleopPeriodic()
    {
        //testHandler.teleopPeriodic();
        driveHandler.teleopPeriodic();
        ballSystemHandler.teleopPeriodic();
    }

    public void autonomousInit()
    {
        autoHandler.autonomousInit();
    }

    public void autonomousPeriodic()
    {
        autoHandler.autonomousPeriodic();
    }
}
