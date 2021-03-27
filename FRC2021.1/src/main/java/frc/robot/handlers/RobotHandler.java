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

    private double enableTime;
    private boolean hasBeenStopped = false;

    public RobotHandler(Robot robot)
    {
        this.robot = robot;

        stateHandler = new StateHandler(this);
        inputHandler = new InputHandler(this);

        driveHandler = new DriveHandler(this);
        ballSystemHandler = new BallSystemHandler(this);
        hookHandler = new HookHandler(this);

        autoHandler = new AutoHandler(this, AutoType.HyperPathA); // Set the auto type with suffleboard or something

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
        recordingAndPlaybackHandler.disabledInit();
    }

    public void teleopInit() // Teleop isn't it?
    {
        robotMode = RobotMode.Teleop;
        enableTime = Timer.getFPGATimestamp();
        hasBeenStopped = false;
        recordingAndPlaybackHandler.teleopInit();
    }

    public void teleopPeriodic()
    {
        if (!recordingAndPlaybackHandler.player.isPlaying && Timer.getFPGATimestamp() - enableTime <= 60)
        {
            driveHandler.teleopPeriodic();
            shuffleboardHandler.printBooleanToWidget("Driving Enabled", true);
        }
        else if (!recordingAndPlaybackHandler.player.isPlaying)
        {
            driveHandler.differentialDrive.tankDrive(0, 0);
            shuffleboardHandler.printBooleanToWidget("Driving Enabled", false);
        }
        ballSystemHandler.teleopPeriodic();
        hookHandler.teleopPeriodic();
        recordingAndPlaybackHandler.teleopPeriodic();

        if (inputHandler.joystickManipulator.getRawButton(Constants.Buttons.STOP_TIMER) && !hasBeenStopped)
        {
            hasBeenStopped = true;
            double time = Timer.getFPGATimestamp() - enableTime;
            System.out.println(time);
            shuffleboardHandler.printDoubleToWidget("Last Time: ", time);
        }

        if (Timer.getFPGATimestamp() - enableTime <= 60)
            shuffleboardHandler.printDoubleToWidget("Time Left", (Timer.getFPGATimestamp() - enableTime) - 60);
        else
            shuffleboardHandler.printDoubleToWidget("Time Left", 0);
    }

    public void autonomousInit() 
    {
        robotMode = RobotMode.Autonomous;

        autoHandler.autonomousInit();
        recordingAndPlaybackHandler.autonomousInit();
    }

    public void autonomousPeriodic()
    {
        autoHandler.autonomousPeriodic();
        recordingAndPlaybackHandler.autonomousPeriodic();
    }
}
