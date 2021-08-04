package frc.robot.handlers;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
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

    public boolean frontSwitched = false;
    
    public DriveHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
        deviceContainer.driveLeftFollower.follow(deviceContainer.driveLeftFront);
        deviceContainer.driveRightFollower.follow(deviceContainer.driveRightFront);
        differentialDrive = new DifferentialDrive(deviceContainer.driveLeftFront, deviceContainer.driveRightFront);

        ToggleButtonUpdate switchFrontLeft = new ToggleButtonUpdate(this, PeriodicType.Teleop, "SwitchFront", new ButtonLocation(Constants.Button.SwitchFront, JoystickType.DriveLeft), 0.5);
        ToggleButtonUpdate switchFrontRight = new ToggleButtonUpdate(this, PeriodicType.Teleop, "SwitchFront", new ButtonLocation(Constants.Button.SwitchFront, JoystickType.DriveRight), 0.5);
        buttonUpdateListenerHandler.addButtonUpdate(switchFrontLeft);
        buttonUpdateListenerHandler.addButtonUpdate(switchFrontRight);

        differentialDrive.setSafetyEnabled(false);
    }

    public void teleopInit() 
    {

    }

    public void teleopPeriodic() 
    {
        double leftDriveTrain = joystickHandler.getLeftDriveTrain();
        double rightDriveTrain = joystickHandler.getRightDriveTrain();

        if (!frontSwitched)
            differentialDrive.tankDrive(leftDriveTrain, rightDriveTrain);
        else
            differentialDrive.tankDrive(rightDriveTrain * -1, leftDriveTrain * -1);
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "SwitchFront" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
            switchFront();
    }

    private void switchFront()
    {
        frontSwitched = !frontSwitched;
        cameraHandler.updateCameraDirection(frontSwitched);
        shuffleboardHandler.displayBool(shuffleboardHandler.tab, "Front Switched", frontSwitched);
    }

}
