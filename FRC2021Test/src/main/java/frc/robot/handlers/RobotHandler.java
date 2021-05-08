package frc.robot.handlers;

import frc.robot.Robot;
import frc.robot.interfaces.IAutoHandler;
import frc.robot.interfaces.IDisabledHandler;
import frc.robot.interfaces.IRobotHandler;
import frc.robot.interfaces.ITeleopHandler;
import frc.robot.interfaces.ITestHandler;

public class RobotHandler implements IRobotHandler, IDisabledHandler, ITeleopHandler, IAutoHandler, ITestHandler {

    public Robot robot;

    public ButtonActionHandler buttonActionHandler;
    public ButtonHandler buttonHandler;

    public RobotHandler(Robot robot)
    {
        this.robot = robot;

        buttonActionHandler = new ButtonActionHandler(this);
        buttonHandler = new ButtonHandler(this);
    }

    public void robotInit()
    {
        buttonHandler.robotInit();
    }
    public void robotPeriodic()
    {
        buttonHandler.robotPeriodic();
    }
    public void disabledInit()
    {
        buttonHandler.disabledInit();
    }
    public void disabledPeriodic()
    {
        buttonHandler.disabledPeriodic();
    }
    public void teleopInit()
    {
        buttonHandler.teleopInit();
    }
    public void teleopPeriodic()
    {
        buttonHandler.teleopPeriodic();
    }
    public void autonomousInit()
    {

    }
    public void autonomousPeriodic()
    {

    }
    public void testInit()
    {
        buttonHandler.testInit();
    }
    public void testPeriodic()
    {
        buttonHandler.testPeriodic();
    }
}
