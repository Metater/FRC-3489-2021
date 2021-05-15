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
import frc.robot.types.ButtonAction;
import frc.robot.types.ButtonActionType;
import frc.robot.types.ButtonLocation;
import frc.robot.types.JoystickType;
import frc.robot.types.PeriodicType;
import frc.robot.types.TriggeredButtonState;

public class ButtonHandler extends BaseHandler implements IRobotHandler, IDisabledHandler, ITeleopHandler, ITestHandler {

    public ButtonHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
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
    public void testInit()
    {

    }
    public void testPeriodic()
    {
        
    }
}
