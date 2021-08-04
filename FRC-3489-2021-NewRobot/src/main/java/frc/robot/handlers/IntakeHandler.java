package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.ToggleButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;

public class IntakeHandler extends BaseHandler implements IButtonListener, ITeleopListener {

    public boolean intakeActivated = false;
    public boolean intakeExtended = false;
    

    public IntakeHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);

        ToggleButtonUpdate toggleIntake = new ToggleButtonUpdate(this, PeriodicType.Teleop, "ToggleIntake", new ButtonLocation(Constants.Button.ToggleIntake, JoystickType.Manipulator), 0.05);
        buttonUpdateListenerHandler.addButtonUpdate(toggleIntake);

        ToggleButtonUpdate toggleIntakeExtension = new ToggleButtonUpdate(this, PeriodicType.Teleop, "ToggleIntakeExtension", new ButtonLocation(Constants.Button.ToggleIntakeExtension, JoystickType.Manipulator), 0.05);
        buttonUpdateListenerHandler.addButtonUpdate(toggleIntakeExtension);
    }

    public void teleopInit()
    {
        
    }

    public void teleopPeriodic()
    {
        //deviceContainer.intake.set(deviceContainer.joystickManipulator.getY());
        //System.out.println(deviceContainer.joystickManipulator.getY());
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "ToggleIntake" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
        {
            if (!intakeExtended)
            {
                intakeExtended = true;
                setIntakePnuematics(true);
                robotHandler.shuffleboardHandler.displayBool(shuffleboardHandler.tab, "Intake Extended", true);
            }

            intakeActivated = !intakeActivated;
            if (intakeActivated) deviceContainer.intake.set(-1);
            else deviceContainer.intake.stopMotor();
            shuffleboardHandler.displayBool(shuffleboardHandler.tab, "Intake Activated", intakeActivated);
        }

        if (buttonUpdate.buttonUpdateName == "ToggleIntakeExtension" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
        {
            intakeExtended = !intakeExtended;
            if (intakeActivated) deviceContainer.intake.stopMotor();
            shuffleboardHandler.displayBool(shuffleboardHandler.tab, "Intake Activated", false);

            setIntakePnuematics(intakeExtended);
            shuffleboardHandler.displayBool(shuffleboardHandler.tab, "Intake Extended", intakeExtended);
        }
    }

    public void setIntakePnuematics(boolean state)
    {
        shuffleboardHandler.displayBool(shuffleboardHandler.tab, "Intake Arm State", state);
        deviceContainer.intakeArm.set(state);
    }

    

}
