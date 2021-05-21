package frc.robot.handlers;

import frc.robot.Robot;
import frc.robot.containers.DeviceContainer;

public class RobotHandler {
    
    public Robot robot;

    public DeviceContainer deviceContainer;

    public FunctionListenerHandler functionListenerHandler;

    public SetupHandler setupHandler;
    public DriveHandler driveHandler;

    public RobotHandler(Robot robot)
    {
        this.robot = robot;

        deviceContainer = new DeviceContainer();

        functionListenerHandler = new FunctionListenerHandler(this);

        setupHandler = new SetupHandler(this);
        driveHandler = new DriveHandler(this);
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
