package frc.robot.handlers;

import frc.robot.Robot;
import frc.robot.containers.DeviceContainer;

public class RobotHandler {
    
    public Robot robot;

    public DeviceContainer deviceContainer;

    public FunctionListenerHandler functionListenerHandler;
    public SetupHandler setupHandler;
    public ButtonListenerHandler buttonListenerHandler;
    public ButtonHandler buttonHandler;
    public JoystickHandler joystickHandler;
    public ShuffleboardHandler shuffleboardHandler;
    //public DriveHandler driveHandler;
    //public BallSystemHandler ballSystemHandler;

    public RobotHandler(Robot robot)
    {
        this.robot = robot;

        deviceContainer = new DeviceContainer();

        functionListenerHandler = new FunctionListenerHandler(this);
        setupHandler = new SetupHandler(this);
        buttonListenerHandler = new ButtonListenerHandler(this);
        buttonHandler = new ButtonHandler(this);
        joystickHandler = new JoystickHandler(this);
        shuffleboardHandler = new ShuffleboardHandler(this);
        //driveHandler = new DriveHandler(this);
        //ballSystemHandler = new BallSystemHandler(this);
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
