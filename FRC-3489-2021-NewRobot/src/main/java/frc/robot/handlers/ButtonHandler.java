package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.interfaces.IButtonListener;
import frc.robot.types.ButtonLocation;
import frc.robot.types.ButtonPress;
import frc.robot.types.JoystickType;
import frc.robot.types.PeriodicType;
import frc.robot.types.buttonTriggerCriteria.RawButtonTriggerCriteria;

public class ButtonHandler extends BaseHandler implements IButtonListener {
    
    public ButtonHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        RawButtonTriggerCriteria testButton0 = new RawButtonTriggerCriteria(this, PeriodicType.Robot, "TestButton0", new ButtonLocation(12, JoystickType.Manipulator));
        robotHandler.buttonListenerHandler.addButtonTriggerCriteria(testButton0);
        RawButtonTriggerCriteria testButton1 = new RawButtonTriggerCriteria(this, PeriodicType.Robot, "TestButton1", new ButtonLocation(11, JoystickType.Manipulator));
        robotHandler.buttonListenerHandler.addButtonTriggerCriteria(testButton1);
        RawButtonTriggerCriteria testButton2 = new RawButtonTriggerCriteria(this, PeriodicType.Robot, "TestButton2", new ButtonLocation(7, JoystickType.DriveLeft));
        robotHandler.buttonListenerHandler.addButtonTriggerCriteria(testButton2);
    }

    public void buttonPressed(ButtonPress buttonPress)
    {
        System.out.println(buttonPress.buttonTriggerCriteria.buttonTriggerName + ":" + buttonPress.buttonTriggerCriteria.buttonLocation.buttonIndex + ": " + Timer.getFPGATimestamp());
    }

}
