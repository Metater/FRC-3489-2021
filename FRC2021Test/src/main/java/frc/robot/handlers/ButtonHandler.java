package frc.robot.handlers;

import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import frc.robot.Constants;
import frc.robot.configs.ButtonConfig;
import frc.robot.interfaces.IDisabledHandler;
import frc.robot.interfaces.IRobotHandler;
import frc.robot.interfaces.ITeleopHandler;
import frc.robot.interfaces.ITestHandler;
import frc.robot.registries.ButtonRegistry;
import frc.robot.types.ButtonAction;
import frc.robot.types.ButtonActionType;
import frc.robot.types.ButtonLocation;
import frc.robot.types.JoystickType;
import frc.robot.types.PeriodicType;
import frc.robot.types.TriggeredButtonState;

public class ButtonHandler extends BaseHandler implements IRobotHandler, IDisabledHandler, ITeleopHandler, ITestHandler {

    public ButtonRegistry buttonRegistry;

    public ButtonHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        registerButtons();
    }

    private void registerButtons()
    {
        registerSwitchFront();
    }

    private void registerSwitchFront()
    {
        ButtonAction switchFrontAction = new ButtonAction((triggeredButtonState) -> robotHandler.buttonActionHandler.switchFront(triggeredButtonState));
        ArrayList<ButtonLocation> buttonLocations = new ArrayList<ButtonLocation>();
        buttonLocations.add(new ButtonLocation(JoystickType.DriveLeft, Constants.JoystickDriveLeftButtons.SwitchFront));
        buttonLocations.add(new ButtonLocation(JoystickType.DriveLeft, Constants.JoystickDriveRightButtons.SwitchFront));
        ButtonConfig switchFront = new ButtonConfig(switchFrontAction, PeriodicType.Teleop, 0.25, buttonLocations);
        buttonRegistry.registerButton(ButtonActionType.SwitchFront, switchFront);
    }

    public void robotInit()
    {

    }
    public void robotPeriodic()
    {
        for(Map.Entry<ButtonActionType, ButtonConfig> button : buttonRegistry..entrySet())
        {

        }
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
    public void testInit()
    {

    }
    public void testPeriodic()
    {
        
    }
}
