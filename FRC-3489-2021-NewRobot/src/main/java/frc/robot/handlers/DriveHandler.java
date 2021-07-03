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
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(switchFrontLeft);
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(switchFrontRight);

        differentialDrive = new DifferentialDrive(robotHandler.deviceContainer.driveFrontLeft, robotHandler.deviceContainer.driveFrontRight);
    }

    public void robotInit()
    {

    }

    public void robotPeriodic()
    {
        double leftEncoderPos = robotHandler.deviceContainer.driveFrontLeft.getSelectedSensorPosition();
        double rightEncoderPos = robotHandler.deviceContainer.driveFrontRight.getSelectedSensorPosition();
        robotHandler.shuffleboardHandler.displayDouble("Left Drive Encoder Position", leftEncoderPos);
        robotHandler.shuffleboardHandler.displayDouble("Right Drive Encoder Position", rightEncoderPos);
        double leftEncoderVel = robotHandler.deviceContainer.driveFrontLeft.getSelectedSensorVelocity();
        double rightEncoderVel = robotHandler.deviceContainer.driveFrontRight.getSelectedSensorVelocity();
        robotHandler.shuffleboardHandler.displayDouble("Left Drive Train Velocity", leftEncoderVel);
        robotHandler.shuffleboardHandler.displayDouble("Right Drivev Train Velocity", rightEncoderVel);
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
        {
            forwardSwitched = !forwardSwitched;
            robotHandler.shuffleboardHandler.displayBool("Forward Switched", forwardSwitched);
        }
    }
}
