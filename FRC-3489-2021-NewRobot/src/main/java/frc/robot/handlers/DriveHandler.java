package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.shared.handlers.BaseHandler;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.RawButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;

public class DriveHandler extends BaseHandler implements ITeleopListener, IButtonListener {

    public DifferentialDrive differentialDrive;

    public boolean forwardSwitched = false;
    private double lastForwardSwitchTime = -1;
    
    public DriveHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addTeleopListener(this);
        RawButtonUpdate switchFrontLeft = new RawButtonUpdate(this, PeriodicType.Teleop, "SwitchFront", new ButtonLocation(7, JoystickType.DriveLeft));
        RawButtonUpdate switchFrontRight = new RawButtonUpdate(this, PeriodicType.Teleop, "SwitchFront", new ButtonLocation(7, JoystickType.DriveRight));
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(switchFrontLeft);
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(switchFrontRight);
        differentialDrive = new DifferentialDrive(robotHandler.deviceContainer.driveFrontLeft, robotHandler.deviceContainer.driveFrontRight);
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        /*
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
        */
    }

    public void update(BaseButtonUpdate update)
    {
        if (update.buttonUpdateName == "SwitchFront" && update.buttonUpdateEventType == ButtonUpdateEventType.On && Timer.getFPGATimestamp() - lastForwardSwitchTime > 0.5)
        {
            lastForwardSwitchTime = Timer.getFPGATimestamp();
            forwardSwitched = !forwardSwitched;
        }
    }
}
