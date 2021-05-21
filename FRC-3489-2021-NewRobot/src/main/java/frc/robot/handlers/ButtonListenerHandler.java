package frc.robot.handlers;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Constants;
import frc.robot.containers.DeviceContainer;
import frc.robot.interfaces.*;
import frc.robot.types.ButtonPress;
import frc.robot.types.PeriodicType;

public class ButtonListenerHandler extends BaseHandler implements IRobotListener, ITeleopListener, ITestListener {

    private List<IButtonListener> buttonListeners = new ArrayList<IButtonListener>();
    // Later could add button press criteria along with adding a button listener
    
    public ButtonListenerHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addRobotListener(this);
        robotHandler.functionListenerHandler.addTeleopListener(this);
        robotHandler.functionListenerHandler.addTestListener(this);
    }

    public void addButtonListener(IButtonListener buttonListener)
    {
        buttonListeners.add(buttonListener);
    }

    public void robotInit()
    {

    }

    public void robotPeriodic()
    {
        pollListeners(PeriodicType.Robot);
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        pollListeners(PeriodicType.Teleop);
    }

    public void testInit()
    {

    }

    public void testPeriodic()
    {
        pollListeners(PeriodicType.Test);
    }

    private void pollListeners(PeriodicType periodicType)
    {
        DeviceContainer deviceContainer = robotHandler.deviceContainer;

        for (int i = 0; i < deviceContainer.joystickDriveLeft.getButtonCount(); i++)
        {
            if (deviceContainer.joystickDriveLeft.getRawButton(i))
            {

            }
        }

        for (int i = 0; i < Constants.JoystickManipulatorButtonCount; i++)
        {
            
        }
    }
    private void pollListenersWithButtonPress(ButtonPress buttonPress)
    {
        for (IButtonListener buttonListener : buttonListeners)
        {
            buttonListener.buttonPressed(new ButtonPress(buttonPress));
        }
    }
}
