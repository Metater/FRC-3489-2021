package frc.robot.shared.handlers;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.handlers.BaseHandler;
import frc.robot.handlers.RobotHandler;
import frc.robot.shared.interfaces.IRobotListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.interfaces.ITestListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.ToggleButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;

public class ButtonUpdateListenerHandler extends BaseHandler implements IRobotListener, ITeleopListener, ITestListener {

    private List<BaseButtonUpdate> updatedButtons = new ArrayList<BaseButtonUpdate>();
    
    public ButtonUpdateListenerHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addRobotListener(this);
        robotHandler.functionListenerHandler.addTeleopListener(this);
        robotHandler.functionListenerHandler.addTestListener(this);
    }

    public void addButtonUpdate(BaseButtonUpdate buttonUpdate)
    {
        updatedButtons.add(buttonUpdate);
    }

    public void robotInit() {}
    public void robotPeriodic() { pollButtons(PeriodicType.Robot); }
    public void teleopInit() {}
    public void teleopPeriodic() { pollButtons(PeriodicType.Teleop); }
    public void testInit() {}
    public void testPeriodic() { pollButtons(PeriodicType.Test); }

    private void pollButtons(PeriodicType periodicType)
    {
        for (BaseButtonUpdate buttonUpdate : updatedButtons)
        {
            if (periodicType != buttonUpdate.periodicType) continue;
            if (getButton(buttonUpdate.buttonLocation))
            {
                switch (buttonUpdate.buttonUpdateType)
                {
                    case Raw:
                        buttonUpdate.update(ButtonUpdateEventType.On);
                        break;
                    case Toggle:
                        ToggleButtonUpdate toggleButtonUpdate = (ToggleButtonUpdate)buttonUpdate;
                        if (Timer.getFPGATimestamp() > toggleButtonUpdate.lastUpdateTime + toggleButtonUpdate.triggerCooldown && !toggleButtonUpdate.lastState)
                        {
                            buttonUpdate.update(ButtonUpdateEventType.RisingEdge);
                        }
                        toggleButtonUpdate.lastState = true;
                        break;
                }
            }
            else
            {
                switch (buttonUpdate.buttonUpdateType)
                {
                    case Raw:
                        buttonUpdate.update(ButtonUpdateEventType.Off);
                        break;
                    case Toggle:
                        ToggleButtonUpdate toggleButtonUpdate = (ToggleButtonUpdate)buttonUpdate;
                        toggleButtonUpdate.lastState = false;
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
