package frc.robot.handlers;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.interfaces.IButtonListener;
import frc.robot.interfaces.ITeleopListener;
import frc.robot.types.ButtonLocation;
import frc.robot.types.ButtonPress;
import frc.robot.types.JoystickType;

public class DriveHandler extends BaseHandler implements ITeleopListener, IButtonListener {

    public DifferentialDrive differentialDrive;

    public boolean forwardSwitched = false;
    
    public DriveHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addTeleopListener(this);
        robotHandler.buttonListenerHandler.addButtonListener(this);
        differentialDrive = new DifferentialDrive(robotHandler.deviceContainer.driveFrontLeft, robotHandler.deviceContainer.driveFrontRight);
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        double leftDriveTrain = robotHandler.joystickHandler.getLeftDriveTrain();
        double rightDriveTrain = robotHandler.joystickHandler.getRightDriveTrain();

        if (!forwardSwitched)
        {
            differentialDrive.tankDrive(leftDriveTrain, rightDriveTrain);
        }
        else
        {
            differentialDrive.tankDrive(rightDriveTrain * -1, leftDriveTrain * -1);
        }
    }

    public void buttonPressed(ButtonPress buttonPress)
    {
        System.out.println(buttonPress.buttonLocation.buttonIndex + ":" + buttonPress.getTimeBetweenLastPress());
        boolean leftSwitchForward = buttonPress.buttonLocation.compare(7, JoystickType.DriveLeft) && buttonPress.getTimeBetweenLastPress() > 0.5;
        boolean rightSwitchForward = buttonPress.buttonLocation.compare(7, JoystickType.DriveRight) && buttonPress.getTimeBetweenLastPress() > 0.5;
        if (leftSwitchForward || rightSwitchForward)
        {
            forwardSwitched = !forwardSwitched;
        }
    }
}
