package frc.robot.handlers;

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
import frc.robot.types.IntakeStateType;

public class BallSystemHandler extends BaseHandler implements IRobotListener, ITeleopListener, IButtonListener {

    public BallSystemHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);

        ToggleButtonUpdate toggleIntake = new ToggleButtonUpdate(this, PeriodicType.Teleop, "ToggleIntake", new ButtonLocation(Constants.Button.ToggleIntake, JoystickType.Manipulator), 0.5);
        buttonUpdateListenerHandler.addButtonUpdate(toggleIntake);
    }

    private IntakeStateType intakeState = IntakeStateType.Deactivated;
    private int ballCount = 0;
    private double encoderTarget = 0;

    public void robotInit()
    {
        deviceContainer.intakeBeltFront.setSafetyEnabled(false);
        deviceContainer.intakeBeltRear.setSafetyEnabled(false);
        deviceContainer.intakeRoller.setSafetyEnabled(false);
    }

    public void robotPeriodic()
    {

    }

    public void teleopInit()
    {
        ballCount = 0;
        intakeState = IntakeStateType.Deactivated;
        deviceContainer.intakeSolenoid.set(false);
    }

    public void teleopPeriodic()
    {
        shuffleboardHandler.displayString(shuffleboardHandler.tab, "Intake State", intakeState.toString());
        if (ballCount == 5) shuffleboardHandler.displayBool(shuffleboardHandler.tab, "Intake Full", true);
        else shuffleboardHandler.displayBool(shuffleboardHandler.tab, "Intake Full", false);
        shuffleboardHandler.displayString(shuffleboardHandler.tab, "Ball Count", String.valueOf(ballCount));

        if (joystickHandler.getManStickSpeed() != 0)
            intakeState = IntakeStateType.Manual;

        if (deviceContainer.intakeSolenoid.get())
            deviceContainer.intakeRoller.set(Constants.MotorSpeed.IntakeRoller);
        else deviceContainer.intakeRoller.stopMotor();

        switch(intakeState)
        {
            case Manual:
                intakeManual();
                break;
            case Deactivated:
                intakeDeactivated();
                break;
            case Waiting:
                intakeWaiting();
                break;
            case Indexing:
                intakeIndexing();
                break;
            case Full:
                intakeFull();
                break;
        }
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "ToggleIntake" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
            deviceContainer.intakeSolenoid.toggle();
    }

    private void intakeManual()
    {
        double manStickSpeed = joystickHandler.getManStickSpeed();
        deviceContainer.intakeBeltFront.set(manStickSpeed);
        deviceContainer.intakeBeltRear.set(manStickSpeed);

        ballCount = 0;

        if (manStickSpeed == 0)
        {
            if (deviceContainer.intakeSolenoid.get())
                intakeState = IntakeStateType.Waiting;
            else
                intakeState = IntakeStateType.Deactivated;
        }
    }
    private void intakeDeactivated()
    {
        if (deviceContainer.intakeSolenoid.get())
            intakeState = IntakeStateType.Waiting;
        deviceContainer.intakeBeltFront.stopMotor();
        deviceContainer.intakeBeltRear.stopMotor();
        deviceContainer.intakeRoller.stopMotor();
    }
    private void intakeWaiting()
    {
        if (!deviceContainer.intakeSolenoid.get())
        {
            intakeState = IntakeStateType.Deactivated;
            return;
        }

        boolean ballInSensor = !deviceContainer.ballInputSensor.get();
        if (ballCount == 4 && ballInSensor)
        {
            intakeState = IntakeStateType.Full;
            ballCount++;
        }
        else if (ballInSensor)
        {
            intakeState = IntakeStateType.Indexing;
            encoderTarget = Math.abs(deviceContainer.intakeBeltRear.getSelectedSensorPosition()) + Constants.BallSystemCPI;
        }
        else
        {
            deviceContainer.intakeBeltFront.set(Constants.MotorSpeed.IntakeBeltFront);
        }
        deviceContainer.intakeBeltRear.stopMotor();
    }
    private void intakeIndexing()
    {
        if (Math.abs(deviceContainer.intakeBeltRear.getSelectedSensorPosition()) >= encoderTarget)
        {
            ballCount++;
            intakeState = IntakeStateType.Waiting;
            deviceContainer.intakeBeltRear.stopMotor();
        }
        else
        {
            deviceContainer.intakeBeltFront.set(Constants.MotorSpeed.IntakeBeltFront);
            deviceContainer.intakeBeltRear.set(Constants.MotorSpeed.IntakeBeltRear);
        }
    }
    private void intakeFull()
    {
        deviceContainer.intakeBeltFront.stopMotor();
        deviceContainer.intakeBeltRear.stopMotor();
        deviceContainer.intakeRoller.stopMotor();
        deviceContainer.intakeSolenoid.set(false);
    }

    
}
