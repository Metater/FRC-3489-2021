package frc.robot.handlers;

import frc.robot.handlers.AutoHandler.AutoType;

public class RobotHandler {

    public StateHandler stateHandler;
    public InputHandler inputHandler;

    public DriveHandler driveHandler;
    public BallSystemHandler ballSystemHandler;
    public HookHandler hookHandler;

    public AutoHandler autoHandler;

    public ShuffleboardHandler shuffleboardHandler;
    public CameraHandler cameraHandler;

    public RecordingAndPlaybackHandler recordingAndPlaybackHandler;

    public RobotHandler()
    {
        stateHandler = new StateHandler(this);
        inputHandler = new InputHandler(this);

        driveHandler = new DriveHandler(this);
        ballSystemHandler = new BallSystemHandler(this);
        hookHandler = new HookHandler(this);

        autoHandler = new AutoHandler(this, AutoType.Barrel); // Set the auto type with suffleboard or something

        shuffleboardHandler = new ShuffleboardHandler(this);
        cameraHandler = new CameraHandler(this);

        recordingAndPlaybackHandler = new RecordingAndPlaybackHandler(this);

        driveHandler.differentialDrive.setSafetyEnabled(false);
    }

    public void robotInit()
    {
        
    }
    public void robotPeriodic()
    {

    }

    public void disabledInit() // Disabled isn't it?
    {
        stateHandler.reset();
        ballSystemHandler.reset();
    }

    public void teleopInit() // Teleop isn't it?
    {
        
    }

    public void teleopPeriodic()
    {
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
