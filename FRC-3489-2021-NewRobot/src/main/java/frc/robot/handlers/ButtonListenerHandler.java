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
import frc.robot.types.buttonTriggerCriteria.PressedDebounceButtonTriggerCriteria;
import frc.robot.types.buttonTriggerCriteria.RawButtonTriggerCriteria;
import frc.robot.types.buttonTriggerCriteria.ReleasedDebounceButtonTriggerCriteria;
import frc.robot.types.buttonTriggerCriteria.WhileHeldDebounceButtonTriggerCriteria;

public class ButtonListenerHandler extends BaseHandler implements IRobotListener, IDisabledListener, ITeleopListener, ITestListener {

    private List<BaseButtonTriggerCriteria> buttonTriggers = new ArrayList<BaseButtonTriggerCriteria>();

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
        buttonTriggerCriteria.buttonLastPressData = new ButtonLastPressData();
    }

    public void robotInit()
    {

    }

    public void robotPeriodic()
    {
        pollListeners(PeriodicType.Robot);
    }

    public void disabledInit()
    {

    }

    public void disabledPeriodic()
    {
        pollListeners(PeriodicType.Disabled);
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
                switch (buttonTriggerCriteria.buttonTriggerCriteriaType)
                {
                    case Raw:
                        RawButtonTriggerCriteria rawButtonTriggerCriteria = (RawButtonTriggerCriteria)buttonTriggerCriteria;
                        buttonTriggerCriteria.trigger.buttonPressed(new ButtonPress(buttonTriggerCriteria));
                        buttonTriggerCriteria.buttonLastPressData.lastPressTime = Timer.getFPGATimestamp();
                        break;
                    case PressedDebounce:
                        PressedDebounceButtonTriggerCriteria pressedDebounceButtonTriggerCriteria = (PressedDebounceButtonTriggerCriteria)buttonTriggerCriteria;
                        if (buttonTriggerCriteria.buttonLastPressData.getTimeSinceLastPressValid(0.2))
                        {
                            buttonTriggerCriteria.trigger.buttonPressed(new ButtonPress(buttonTriggerCriteria));
                            buttonTriggerCriteria.buttonLastPressData.lastPressTime = Timer.getFPGATimestamp();
                        }
                        break;
                    case WhileHeldDebounce:
                        WhileHeldDebounceButtonTriggerCriteria whileHeldDebounceButtonTriggerCriteria = (WhileHeldDebounceButtonTriggerCriteria)buttonTriggerCriteria;
                        if (!buttonTriggerCriteria.buttonLastPressData.getTimeSinceLastPressValid(0.2) && !whileHeldDebounceButtonTriggerCriteria.isHeld)
                        {
                            whileHeldDebounceButtonTriggerCriteria.isHeld = true;
                        }
                        buttonTriggerCriteria.buttonLastPressData.lastPressTime = Timer.getFPGATimestamp();
                        break;
                    case ReleasedDebounce:
                        ReleasedDebounceButtonTriggerCriteria releasedDebounceButtonTriggerCriteria = (ReleasedDebounceButtonTriggerCriteria)buttonTriggerCriteria;
                        buttonTriggerCriteria.buttonLastPressData.lastPressTime = Timer.getFPGATimestamp();
                        break;
                    default:
                        break;
                }
            }
            else
            {
                switch (buttonTriggerCriteria.buttonTriggerCriteriaType)
                {
                    case WhileHeldDebounce:
                        WhileHeldDebounceButtonTriggerCriteria whileHeldDebounceButtonTriggerCriteria = (WhileHeldDebounceButtonTriggerCriteria)buttonTriggerCriteria;
                        break;
                    case ReleasedDebounce:
                        ReleasedDebounceButtonTriggerCriteria releasedDebounceButtonTriggerCriteria = (ReleasedDebounceButtonTriggerCriteria)buttonTriggerCriteria;
                        if (buttonTriggerCriteria.buttonLastPressData.getTimeSinceLastPressValid(0.2))
                        {
                            if (releasedDebounceButtonTriggerCriteria.releaseScheduled)
                            {
                                releasedDebounceButtonTriggerCriteria.releaseScheduled = false;
                                buttonTriggerCriteria.trigger.buttonPressed(new ButtonPress(buttonTriggerCriteria));
                            }
                        }
                        else
                        {
                            releasedDebounceButtonTriggerCriteria.releaseScheduled = true;
                        }
                        releasedDebounceButtonTriggerCriteria.lastReleaseTime = Timer.getFPGATimestamp();
                        break;
                    default:
                        break;
                }
            }
        }
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
}
