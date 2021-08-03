package frc.robot.handlers;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Robot;
import frc.robot.containers.DeviceContainer;
import frc.robot.shared.handlers.ButtonUpdateListenerHandler;
import frc.robot.shared.handlers.FunctionListenerHandler;

public class RobotHandler {
    
    public Robot robot;

    public DeviceContainer deviceContainer;

    public FunctionListenerHandler functionListenerHandler;
    public ButtonUpdateListenerHandler buttonUpdateListenerHandler;

    // Add new handlers here
    public ShuffleboardHandler shuffleboardHandler;
    public BallSystemHandler ballSystemHandler;
    public JoystickHandler joystickHandler;
    public CameraHandler cameraHandler;
    public DriveHandler driveHandler;
    public ClimbSystemHandler climbSystemHandler;
    public ControlPanelHandler controlPanelHandler;
    public AutoHandler autoHandler;

    public List<BaseHandler> handlers = new ArrayList<BaseHandler>();

    public RobotHandler(Robot robot)
    {
        this.robot = robot;

        deviceContainer = new DeviceContainer();

        functionListenerHandler = new FunctionListenerHandler(this);
        buttonUpdateListenerHandler = new ButtonUpdateListenerHandler(this);

        // Add new handlers here
        shuffleboardHandler = new ShuffleboardHandler(this);
        ballSystemHandler = new BallSystemHandler(this);
        joystickHandler = new JoystickHandler(this);
        cameraHandler = new CameraHandler(this);
        driveHandler = new DriveHandler(this);
        climbSystemHandler = new ClimbSystemHandler(this);
        controlPanelHandler = new ControlPanelHandler(this);
        autoHandler = new AutoHandler(this);


        for (BaseHandler handler : handlers)
        {
            handler.ensureReferences(this);
        }
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
