package frc.robot.handlers;

import frc.robot.Robot;
import frc.robot.containers.DeviceContainer;
import frc.robot.shared.handlers.ButtonUpdateListenerHandler;
import frc.robot.shared.handlers.FunctionListenerHandler;

public class RobotHandler {
    
    public Robot robot;

    public DeviceContainer deviceContainer;

    public FunctionListenerHandler functionListenerHandler;
    public ButtonUpdateListenerHandler buttonUpdateListenerHandler;

    public SetupHandler setupHandler;
    public JoystickHandler joystickHandler;
    public ShuffleboardHandler shuffleboardHandler;
    //public FalconTestHandler falconTestHandler;
    public DriveHandler driveHandler;
    //public BallSystemHandler ballSystemHandler;
    public IntakeHandler intakeHandler;
    public ShooterHandler shooterHandler;

    public RobotHandler(Robot robot)
    {
        this.robot = robot;

        deviceContainer = new DeviceContainer();

        functionListenerHandler = new FunctionListenerHandler(this);
        buttonUpdateListenerHandler = new ButtonUpdateListenerHandler(this);

        setupHandler = new SetupHandler(this);
        joystickHandler = new JoystickHandler(this);
        shuffleboardHandler = new ShuffleboardHandler(this);
        //falconTestHandler = new FalconTestHandler(this);
        driveHandler = new DriveHandler(this);
        //ballSystemHandler = new BallSystemHandler(this);
        intakeHandler = new IntakeHandler(this);
        shooterHandler = new ShooterHandler(this);
    }

    public void robotInit()
    {
        functionListenerHandler.robotInit();
    }
    public void robotPeriodic()
    {
        functionListenerHandler.robotPeriodic();
    }
    public void disabledInit()
    {
        functionListenerHandler.disabledInit();
    }
    public void disabledPeriodic()
    {
        functionListenerHandler.disabledPeriodic();
    }
    public void autonomousInit()
    {
        functionListenerHandler.autonomousInit();
    }
    public void autonomousPeriodic()
    {
        functionListenerHandler.autonomousPeriodic();
    }
    public void teleopInit()
    {
        functionListenerHandler.teleopInit();
    }
    public void teleopPeriodic()
    {
        functionListenerHandler.teleopPeriodic();
    }
    public void testInit()
    {
        functionListenerHandler.testInit();
    }
    public void testPeriodic()
    {
        functionListenerHandler.testPeriodic();
    }
}
