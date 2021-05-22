package frc.robot.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.containers.DeviceContainer;
import frc.robot.interfaces.*;
import frc.robot.types.ButtonLastPressData;
import frc.robot.types.ButtonLocation;
import frc.robot.types.ButtonPress;
import frc.robot.types.JoystickType;
import frc.robot.types.PeriodicType;
import frc.robot.types.buttonTriggerCriteria.BaseButtonTriggerCriteria;

public class ButtonListenerHandler extends BaseHandler implements IRobotListener, ITeleopListener, ITestListener {

    private List<IButtonListener> buttonListeners = new ArrayList<IButtonListener>();
    // Later could add button press criteria along with adding a button listener

    private List<BaseButtonTriggerCriteria> buttonTriggers = new ArrayList<BaseButtonTriggerCriteria>();

    private Map<ButtonLocation, Map<PeriodicType, ButtonLastPressData>> buttonLocationLastPressMap = new HashMap<ButtonLocation, Map<PeriodicType, ButtonLastPressData>>();

    public ButtonListenerHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addRobotListener(this);
        robotHandler.functionListenerHandler.addTeleopListener(this);
        robotHandler.functionListenerHandler.addTestListener(this);
    }

    public void addButtonTriggerCriteria(BaseButtonTriggerCriteria buttonTriggerCriteria)
    {
        buttonTriggers.add(buttonTriggerCriteria);
        boolean hadButtonLocation = false;
        for (Entry<ButtonLocation, Map<PeriodicType, ButtonLastPressData>> buttonLocationLastPress : buttonLocationLastPressMap.entrySet())
        {
            if (buttonLocationLastPress.getKey().compare(buttonTriggerCriteria.buttonLocation))
            {
                if (!buttonLocationLastPress.getValue().containsKey(buttonTriggerCriteria.periodicType))
                {
                    buttonLocationLastPressMap.get(buttonLocationLastPress.getKey()).put(buttonTriggerCriteria.periodicType, new ButtonLastPressData());
                    hadButtonLocation = true;
                    break;
                }
            }
        }
        if (!hadButtonLocation)
        {
            Map<PeriodicType, ButtonLastPressData> lastPressTimesForPeriodicType = new HashMap<PeriodicType, ButtonLastPressData>();
            lastPressTimesForPeriodicType.put(buttonTriggerCriteria.periodicType, new ButtonLastPressData());
            buttonLocationLastPressMap.put(buttonTriggerCriteria.buttonLocation, lastPressTimesForPeriodicType);
        }
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
        for (BaseButtonTriggerCriteria buttonTriggerCriteria : buttonTriggers)
        {
            if (buttonTriggerCriteria.periodicType != periodicType) continue;
            if (getButton(buttonTriggerCriteria.buttonLocation))
            {

            }
        }

        checkMapAndPoll(periodicType, JoystickType.DriveLeft);
        checkMapAndPoll(periodicType, JoystickType.DriveRight);
        checkMapAndPoll(periodicType, JoystickType.Manipulator);
    }

    private boolean getButton(ButtonLocation buttonLocation)
    {
        if (buttonLocation.joystickType == JoystickType.DriveLeft) {
            return robotHandler.deviceContainer.joystickDriveLeft.getRawButton(buttonLocation.buttonIndex);
        } else if (buttonLocation.joystickType == JoystickType.DriveRight) {
            return robotHandler.deviceContainer.joystickDriveRight.getRawButton(buttonLocation.buttonIndex);
        } else {
            return robotHandler.deviceContainer.joystickManipulator.getRawButton(buttonLocation.buttonIndex);
        }
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
