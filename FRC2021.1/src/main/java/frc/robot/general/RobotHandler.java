package frc.robot.general;

import frc.robot.auto.AutosHandler.AutoType;

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
        //shuffleboardHandler moved to teleopInit
        cameraHandler = new CameraHandler(this);

        driveHandler.differentialDrive.setSafetyEnabled(false);
        autoHandler = new AutoHandler(this, AutoType.TestAuto); // Set the auto type with shuffleboard or something

        //testHandler = new TestHandler(this);
    }

    public void disabledInit() // Disabled isn't it?
    {
        //ballSystemHandler.reset();
    }
    // UNCOMMENTSDOIJASOIHOIHSODHAOHIHO SOMETIME

    public void teleopInit() // Teleop isn't it?
    {
        shuffleboardHandler = new ShuffleboardHandler(this);
        stateHandler.reset();
    }

    public void teleopPeriodic()
    {
        //testHandler.teleopPeriodic();
        driveHandler.teleopPeriodic();
        ballSystemHandler.teleopPeriodic();
        hookHandler.teleopPeriodic();
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
