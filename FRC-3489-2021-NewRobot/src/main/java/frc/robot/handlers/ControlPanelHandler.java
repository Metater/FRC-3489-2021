package frc.robot.handlers;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.IRobotListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.RawButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;

public class ControlPanelHandler extends BaseHandler implements IRobotListener, ITeleopListener, IButtonListener {


    public ControlPanelHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);

        RawButtonUpdate spinCPSpinner = new RawButtonUpdate(this, PeriodicType.Teleop, "SpinCPSpinner", new ButtonLocation(Constants.Button.SpinCPSpinner, JoystickType.Manipulator));
        buttonUpdateListenerHandler.addButtonUpdate(spinCPSpinner);
    }

    public void robotInit()
    {

    }

    public void robotPeriodic()
    {

    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        shuffleboardHandler.displayString(shuffleboardHandler.tab, "Game Data", DriverStation.getInstance().getGameSpecificMessage());
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "SpinCPSpinner")
        {
            if (buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.On)
            {
                //deviceContainer.controlPanelPnuematics.set(true);
                //deviceContainer.controlPanelSpinner.set(0.6);
            }
            else if (buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.Off)
            {
                //deviceContainer.controlPanelPnuematics.set(false);
                //deviceContainer.controlPanelSpinner.stopMotor();
            }
        }
    }


}
