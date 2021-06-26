package frc.robot.handlers;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.shared.handlers.BaseHandler;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.ToggleButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;

public class DriveHandler extends BaseHandler implements ITeleopListener, IButtonListener {

    public DifferentialDrive differentialDrive;

    public boolean forwardSwitched = false;
    
    public DriveHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addTeleopListener(this);

        ToggleButtonUpdate switchFrontLeft = new ToggleButtonUpdate(this, PeriodicType.Teleop, "SwitchFront", new ButtonLocation(7, JoystickType.DriveLeft), 0.5);
        ToggleButtonUpdate switchFrontRight = new ToggleButtonUpdate(this, PeriodicType.Teleop, "SwitchFront", new ButtonLocation(7, JoystickType.DriveRight), 0.5);
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(switchFrontLeft);
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(switchFrontRight);

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

    public void update(BaseButtonUpdate update)
    {
        if (update.buttonUpdateName == "SwitchFront" && update.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
            forwardSwitched = !forwardSwitched;
    }
}
