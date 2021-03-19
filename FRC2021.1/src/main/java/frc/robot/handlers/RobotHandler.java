package frc.robot.handlers;

import frc.robot.handlers.AutoHandler.AutoType;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.*;

public class RobotHandler {

    public Robot robot;

    public enum RobotMode
    {
        Disabled,
        Autonomous,
        Teleop
    }

    public RobotMode robotMode = RobotMode.Disabled;

    public StateHandler stateHandler;
    public InputHandler inputHandler;

    public DriveHandler driveHandler;
    public BallSystemHandler ballSystemHandler;
    public HookHandler hookHandler;

    public AutoHandler autoHandler;

    public ShuffleboardHandler shuffleboardHandler;
    public CameraHandler cameraHandler;

    public RecordingAndPlaybackHandler recordingAndPlaybackHandler;

    public RobotHandler(Robot robot)
    {
        this.robot = robot;

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
        shuffleboardHandler.printBooleanToWidget("Recording", false);
        shuffleboardHandler.printBooleanToWidget("Playing", false);
    }
    public void robotPeriodic()
    {

    }

    public void disabledInit() // Disabled isn't it?
    {
        robotMode = RobotMode.Disabled;
        stateHandler.reset();
        ballSystemHandler.reset();
    }

    public void teleopInit() // Teleop isn't it?
    {
        robotMode = RobotMode.Teleop;
        recordingAndPlaybackHandler.teleopInit();
    }

    public void teleopPeriodic()
    {
        double time = Timer.getFPGATimestamp();

        if (!recordingAndPlaybackHandler.player.isPlaying)
            driveHandler.teleopPeriodic();
        ballSystemHandler.teleopPeriodic();
        hookHandler.teleopPeriodic();
        recordingAndPlaybackHandler.teleopPeriodic();
    }

    public void autonomousInit() /* ROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOTROBOT */
    {
        /*
        robotMode = RobotMode.Autonomous;

        if (recordingAndPlaybackHandler.selectedRecording == -1)
        {
            autoHandler.autonomousInit();
        }
        else
        {
            recordingAndPlaybackHandler.autonomousInit();
        }
        */
        recordingAndPlaybackHandler.autonomousInit();
    }

    public void autonomousPeriodic()
    {
        /*
        if (recordingAndPlaybackHandler.selectedRecording == -1)
        {
            autoHandler.autonomousPeriodic();
        }
        else
        {
            recordingAndPlaybackHandler.autonomousPeriodic();
        }
        */
        recordingAndPlaybackHandler.autonomousPeriodic();
    }
}
