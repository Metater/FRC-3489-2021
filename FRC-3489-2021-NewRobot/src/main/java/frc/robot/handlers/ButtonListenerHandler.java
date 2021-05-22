package frc.robot.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.containers.DeviceContainer;
import frc.robot.interfaces.*;
import frc.robot.types.ButtonLocation;
import frc.robot.types.ButtonPress;
import frc.robot.types.JoystickType;
import frc.robot.types.PeriodicType;

public class ButtonListenerHandler extends BaseHandler implements IRobotListener, ITeleopListener, ITestListener {

    private List<IButtonListener> buttonListeners = new ArrayList<IButtonListener>();
    // Later could add button press criteria along with adding a button listener

    private Map<ButtonLocation, Map<PeriodicType, Double>> buttonLocationLastPressMap = new HashMap<ButtonLocation, Map<PeriodicType, Double>>();

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
        checkMapAndPoll(periodicType, JoystickType.DriveLeft);
        checkMapAndPoll(periodicType, JoystickType.DriveRight);
        checkMapAndPoll(periodicType, JoystickType.Manipulator);
    }
    private void checkMapAndPoll(PeriodicType periodicType, JoystickType joystickType)
    {
        Joystick joystick;

        if (joystickType == JoystickType.DriveLeft) {
            joystick = robotHandler.deviceContainer.joystickDriveLeft;
        } else if (joystickType == JoystickType.DriveRight) {
            joystick = robotHandler.deviceContainer.joystickDriveRight;
        } else {
            joystick = robotHandler.deviceContainer.joystickManipulator;
        }

        for (int buttonIndex = 1; buttonIndex <= joystick.getButtonCount(); buttonIndex++)
        {
            if (joystick.getRawButton(buttonIndex))
            {
                ButtonLocation buttonLocation = new ButtonLocation(buttonIndex, JoystickType.DriveLeft);
                if (buttonLocationLastPressMap.containsKey(buttonLocation))
                {
                    Map<PeriodicType, Double> lastPressMap = buttonLocationLastPressMap.get(buttonLocation);
                    if (!lastPressMap.containsKey(periodicType))
                    {
                        lastPressMap.put(periodicType, -1d);
                    }
                }
                else
                {
                    Map<PeriodicType, Double> lastPressMap = new HashMap<PeriodicType, Double>();
                    lastPressMap.put(periodicType, -1d);
                    buttonLocationLastPressMap.put(buttonLocation, lastPressMap);
                }
                pollListenersWithButtonPress(buttonLocation, periodicType, buttonLocationLastPressMap.get(buttonLocation).get(periodicType));
                buttonLocationLastPressMap.get(buttonLocation).replace(periodicType, Timer.getFPGATimestamp());
            }
        }
    }
    private void pollListenersWithButtonPress(ButtonLocation buttonLocation, PeriodicType periodicType, double lastPressTime)
    {
        for (IButtonListener buttonListener : buttonListeners)
        {
            buttonListener.buttonPressed(new ButtonPress(buttonLocation, periodicType, lastPressTime));
        }
    }
}
