package frc.robot.handlers;

import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.shared.handlers.BaseHandler;
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
    

    public IntakeHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addTeleopListener(this);
        ToggleButtonUpdate toggleIntake = new ToggleButtonUpdate(this, PeriodicType.Teleop, "ToggleIntake", new ButtonLocation(2, JoystickType.Manipulator), 0.05);
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(toggleIntake);
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
            intakeActivated = !intakeActivated;
            if (intakeActivated) robotHandler.deviceContainer.intake.set(1);
            else robotHandler.deviceContainer.intake.stopMotor();
        }
    }



}
