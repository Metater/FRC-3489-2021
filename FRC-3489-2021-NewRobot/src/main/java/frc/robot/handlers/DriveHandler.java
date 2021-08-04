package frc.robot.handlers;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.IRobotListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.ToggleButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;

public class DriveHandler extends BaseHandler implements IButtonListener, IRobotListener, ITeleopListener {

    public DifferentialDrive differentialDrive;

    public boolean forwardSwitched = false;
    
    public DriveHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);

        ToggleButtonUpdate switchFrontLeft = new ToggleButtonUpdate(this, PeriodicType.Teleop, "SwitchFront", new ButtonLocation(Constants.Buttons.SwitchFront, JoystickType.DriveLeft), 0.5);
        ToggleButtonUpdate switchFrontRight = new ToggleButtonUpdate(this, PeriodicType.Teleop, "SwitchFront", new ButtonLocation(Constants.Buttons.SwitchFront, JoystickType.DriveRight), 0.5);
        buttonUpdateListenerHandler.addButtonUpdate(switchFrontLeft);
        buttonUpdateListenerHandler.addButtonUpdate(switchFrontRight);

        differentialDrive = new DifferentialDrive(deviceContainer.driveFrontLeft, deviceContainer.driveFrontRight);
        differentialDrive.setSafetyEnabled(Constants.SafetiesEnabled);
    }

    public void robotInit()
    {

    }

    public void robotPeriodic()
    {
        //double leftEncoderPos = deviceContainer.driveFrontLeft.getSelectedSensorPosition();
        //double rightEncoderPos = deviceContainer.driveFrontRight.getSelectedSensorPosition();
        //shuffleboardHandler.displayDouble("Left Drive Encoder Position", leftEncoderPos);
        //shuffleboardHandler.displayDouble("Right Drive Encoder Position", rightEncoderPos);
        //double leftEncoderVel = deviceContainer.driveFrontLeft.getSelectedSensorVelocity();
        //double rightEncoderVel = deviceContainer.driveFrontRight.getSelectedSensorVelocity();
        //shuffleboardHandler.displayDouble("Left Drive Train Velocity", leftEncoderVel);
        //shuffleboardHandler.displayDouble("Right Drivev Train Velocity", rightEncoderVel);
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        double leftDriveTrain = joystickHandler.getLeftDriveTrain();
        double rightDriveTrain = joystickHandler.getRightDriveTrain();

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
        {
            forwardSwitched = !forwardSwitched;
            shuffleboardHandler.displayBool("Forward Switched", forwardSwitched);
        }
    }
}
