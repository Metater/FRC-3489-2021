package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.interfaces.IButtonListener;
import frc.robot.interfaces.ITeleopListener;
import frc.robot.types.ButtonLocation;
import frc.robot.types.ButtonPress;
import frc.robot.types.JoystickType;

public class DriveHandler extends BaseHandler implements ITeleopListener, IButtonListener {

    public DifferentialDrive differentialDrive;

    public boolean forwardSwitched = false;
    private double lastForwardSwitchTime = -1;
    
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
        boolean leftSwitchForward = buttonPress.buttonLocation.compare(7, JoystickType.DriveLeft);
        boolean rightSwitchForward = buttonPress.buttonLocation.compare(7, JoystickType.DriveRight);
        if ((leftSwitchForward || rightSwitchForward) && Timer.getFPGATimestamp() - lastForwardSwitchTime > 0.5)
        {
            lastForwardSwitchTime = Timer.getFPGATimestamp();
            forwardSwitched = !forwardSwitched;
        }
    }
}
