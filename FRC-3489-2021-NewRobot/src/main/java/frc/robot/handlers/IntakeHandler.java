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

        ToggleButtonUpdate toggleIntake = new ToggleButtonUpdate(this, PeriodicType.Teleop, "ToggleIntake", new ButtonLocation(Constants.Buttons.ToggleIntake, JoystickType.Manipulator), 0.05);
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(toggleIntake);

        ToggleButtonUpdate toggleIntakeExtension = new ToggleButtonUpdate(this, PeriodicType.Teleop, "ToggleIntakeExtension", new ButtonLocation(Constants.Buttons.ToggleIntakeExtension, JoystickType.Manipulator), 0.05);
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(toggleIntakeExtension);
    }

    public void teleopInit()
    {
        
    }

    public void teleopPeriodic()
    {
        //robotHandler.deviceContainer.intake.set(robotHandler.deviceContainer.joystickManipulator.getY());
        //System.out.println(robotHandler.deviceContainer.joystickManipulator.getY());
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "ToggleIntake" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
        {
            if (!intakeExtended)
            {
                intakeExtended = true;
                setIntakePnuematics(true);
                robotHandler.shuffleboardHandler.displayBool("Intake Extended", true);
            }

            intakeActivated = !intakeActivated;
            if (intakeActivated) robotHandler.deviceContainer.intake.set(-1);
            else robotHandler.deviceContainer.intake.stopMotor();
            robotHandler.shuffleboardHandler.displayBool("Intake Activated", intakeActivated);
        }

        if (buttonUpdate.buttonUpdateName == "ToggleIntakeExtension" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
        {
            intakeExtended = !intakeExtended;
            if (intakeActivated) robotHandler.deviceContainer.intake.stopMotor();
            robotHandler.shuffleboardHandler.displayBool("Intake Activated", false);

            setIntakePnuematics(intakeExtended);
            robotHandler.shuffleboardHandler.displayBool("Intake Extended", intakeExtended);
        }
    }

    private void setIntakePnuematics(boolean state)
    {

    }



}
