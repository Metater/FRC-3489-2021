package frc.robot.handlers;

import frc.robot.Robot;
import frc.robot.interfaces.IAutoHandler;
import frc.robot.interfaces.IDisabledHandler;
import frc.robot.interfaces.IRobotHandler;
import frc.robot.interfaces.ITeleopHandler;
import frc.robot.interfaces.ITestHandler;

public class RobotHandler implements IRobotHandler, IDisabledHandler, ITeleopHandler, IAutoHandler, ITestHandler {

    public Robot robot;

    public RobotHandler(Robot robot)
    {
        this.robot = robot;
    }

    public void robotInit()
    {

    }
    public void robotPeriodic()
    {

    }
    public void disabledInit()
    {

    }
    public void disabledPeriodic()
    {

    }
    public void teleopInit()
    {

    }
    public void teleopPeriodic()
    {

    }
    public void autonomousInit()
    {

    }
    public void autonomousPeriodic()
    {

    }
    public void testInit()
    {

    }
    public void testPeriodic()
    {
        
    }
}
