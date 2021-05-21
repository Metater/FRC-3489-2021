package frc.robot.handlers;

import java.util.ArrayList;
import java.util.List;

import frc.robot.interfaces.*;

public class FunctionListenerHandler extends BaseHandler {

    private List<IRobotListener> robotListeners = new ArrayList<IRobotListener>();
    private List<IDisabledListener> disabledListeners = new ArrayList<IDisabledListener>();
    private List<IAutoListener> autoListeners = new ArrayList<IAutoListener>();
    private List<ITeleopListener> teleopListeners = new ArrayList<ITeleopListener>();
    private List<ITestListener> testListeners = new ArrayList<ITestListener>();

    public FunctionListenerHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void addRobotListener(IRobotListener robotListener)
    {
        robotListeners.add(robotListener);
    }
    public void addDisabledListener(IDisabledListener disabledListener)
    {
        disabledListeners.add(disabledListener);
    }
    public void addAutoListener(IAutoListener autoListener)
    {
        autoListeners.add(autoListener);
    }
    public void addTeleopListener(ITeleopListener teleopListener)
    {
        teleopListeners.add(teleopListener);
    }
    public void addTestListener(ITestListener testListener)
    {
        testListeners.add(testListener);
    }

    public void robotInit()
    {
        robotListeners.forEach(IRobotListener::robotInit);
    }
    public void robotPeriodic()
    {
        robotListeners.forEach(IRobotListener::robotPeriodic);
    }
    public void disabledInit()
    {
        disabledListeners.forEach(IDisabledListener::disabledInit);
    }
    public void disabledPeriodic()
    {
        disabledListeners.forEach(IDisabledListener::disabledPeriodic);
    }
    public void autonomousInit()
    {
        autoListeners.forEach(IAutoListener::autonomousInit);
    }
    public void autonomousPeriodic()
    {
        autoListeners.forEach(IAutoListener::autonomousPeriodic);
    }
    public void teleopInit()
    {
        teleopListeners.forEach(ITeleopListener::teleopInit);
    }
    public void teleopPeriodic()
    {
        teleopListeners.forEach(ITeleopListener::teleopPeriodic);
    }
    public void testInit()
    {
        testListeners.forEach(ITestListener::testInit);
    }
    public void testPeriodic()
    {
        testListeners.forEach(ITestListener::testPeriodic);
    }
    
}
