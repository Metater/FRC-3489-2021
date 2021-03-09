package frc.robot.handlers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

public class BallSystemHandler {
    
    private RobotHandler robotHandler;

    // Devices
    public DigitalInput ballInputSensor = new DigitalInput(Constants.DigitalInputs.INTAKE_BELT_BOTTOM_SENSOR); // Could use output sensor for seeing if all balls are out out
    public WPI_TalonSRX intakeBeltFront = new WPI_TalonSRX(Constants.Motors.INTAKE_BELT_FRONT);
    public WPI_TalonFX intakeBeltRear = new WPI_TalonFX(Constants.Motors.INTAKE_BELT_REAR);
    public WPI_TalonSRX intakeRoller = new WPI_TalonSRX(Constants.Motors.INTAKE_ROLLER);
    public Solenoid intakeSolenoid = new Solenoid(Constants.Solenoids.PCM_NUMBER, Constants.Solenoids.INTAKE_ROLLER);

    //-----------------------------------------------------------------------------------------------
    // MAKE ANOTHER BUTTON TO PRESS WHILE THE BLEH, FOR UNJAMMING STUFF, IT WONT RESET THE BALL COUNT
    //-----------------------------------------------------------------------------------------------

    // STATE VARIABLES
    public IntakeState intakeState = IntakeState.IntakeDeactivated;
    public int ballCount = 0;
    public double lastIntakeToggleTime;
    public double encoderTarget = 0;

    public enum IntakeState
    {
        IntakeManual,
        IntakeDeactivated,
        IntakeWaiting,
        IntakeIndexing,
        IntakeFull
    }



    public BallSystemHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        intakeRoller.setSafetyEnabled(false);
    }

    public void teleopPeriodic()
    {
        tryToggleIntake();

        updateShuffleboard();

        // Have method here for detecting intake roller jam, and account for in handleIntakeState, possibly by not calling it if jamming

        trySwitchToIntakeManualState();

        handleIntakeState();
    }
    private void updateShuffleboard()
    {
        System.out.println(ballCount);

        robotHandler.shuffleboardHandler.PrintStringToWidget("Intake State", intakeState.toString());
        if (ballCount == 5)
            robotHandler.shuffleboardHandler.PrintBooleanToWidget("Intake Full", true);
        else
            robotHandler.shuffleboardHandler.PrintBooleanToWidget("Intake Full", false);

        //robotHandler.shuffleboardHandler.PrintDoubleToWidget("Front Clicks: ", intakeBeltFront.getSelectedSensorPosition());
        robotHandler.shuffleboardHandler.PrintDoubleToWidget("Rear Clicks: ", intakeBeltRear.getSelectedSensorPosition());
    }
    private void handleIntakeState()
    {
        switch(intakeState)
        {
            case IntakeManual:
                intakeManual();
                break;
            case IntakeDeactivated:
                intakeDeactivated();
                break;
            case IntakeWaiting:
                intakeWaiting();
                break;
            case IntakeIndexing:
                intakeIndexing();
                break;
            case IntakeFull:
                intakeFull();
                break;
        }
    }
    private void intakeManual()
    {
        double manStickSpeed = robotHandler.inputHandler.getManStickSpeed();
        intakeBeltFront.set(manStickSpeed * -1);
        intakeBeltRear.set(manStickSpeed);

        ballCount = 0;

        if (manStickSpeed == 0) // If in manual mode, and stick isn't being touched, switch state
        {
            if (isIntakeExtended()) // If intake is extended, switch to IntakeWaiting mode
                intakeState = IntakeState.IntakeWaiting;
            else // If intake ins't extended, switch to IntakeDeactivated mode
                intakeState = IntakeState.IntakeDeactivated;
        }
    }
    private void intakeDeactivated()
    {
        if (isIntakeExtended()) // Intake was deactivated, see if it has been extended, if so, switch to IntakeWaiting
            intakeState = IntakeState.IntakeWaiting;
        tryStopIntakeBeltFront();
        tryStopIntakeBeltRear();
        tryStopIntakeRoller();
    }
    private void intakeWaiting()
    {
        if (!isIntakeExtended())
        {
            intakeState = IntakeState.IntakeDeactivated;
            return;
        }

        boolean ballInSensor = !ballInputSensor.get();
        if (ballCount == 4 && ballInSensor) // Index was waiting, but final ball hit sensor, switch to IntakeFull, and increment ball count, dont index
        {
            intakeState = IntakeState.IntakeFull;
            ballCount++;
        }
        else if(ballInSensor) // Intake was waiting, but ball hit sensor, switch to IntakeIndexing, and calculate the encoderTarget for that index
        {
            intakeState = IntakeState.IntakeIndexing;
            encoderTarget = Math.abs(intakeBeltRear.getSelectedSensorPosition()) + Constants.Clicks.BALL_SYSTEM_CLICKS_PER_INDEX;
        }
        else // Intake is still waiting
        {
            intakeBeltFront.set(Constants.INTAKE_BELT_FRONT_SPEED);
        }
        tryStopIntakeBeltRear();
    }
    private void intakeIndexing()
    {
        if (Math.abs(intakeBeltRear.getSelectedSensorPosition()) >= encoderTarget) // If the indexing intake hit it's encoder target, index ball count, and switch to IntakeWaiting
        {
            ballCount++;
            intakeState = IntakeState.IntakeWaiting;
            tryStopIntakeBeltRear();
        }
        else // Intake hasn't hit encoder targer, keep indexing
        {
            intakeBeltFront.set(Constants.INTAKE_BELT_FRONT_SPEED);
            intakeBeltRear.set(Constants.INTAKE_BELT_REAR_SPEED);
        }
    }
    private void intakeFull()
    {
        tryStopIntakeBeltFront();
        tryStopIntakeBeltRear();
        tryStopIntakeRoller();
        tryCloseIntakeSolenoid();
    }
    private void tryToggleIntake()
    {
        if (robotHandler.inputHandler.shouldToggleIntake())
        {
            intakeSolenoid.toggle();
            lastIntakeToggleTime = Timer.getFPGATimestamp();
        }
        if (isIntakeExtended()) // Spin intake roller
            intakeRoller.set(Constants.MotorSpeeds.INTAKE_ROLLER_SPEED);
        else // Stop intake roller if on
            tryStopIntakeRoller();
    }
    private void trySwitchToIntakeManualState()
    {
        if ((robotHandler.inputHandler.getManStickSpeed() != 0) && intakeState != IntakeState.IntakeManual)
            intakeState = IntakeState.IntakeManual;
    }

    public void tryStopIntakeBeltFront()
    {
        if (intakeBeltFront.get() != 0)
            intakeBeltFront.stopMotor();
    }
    public void tryStopIntakeBeltRear()
    {
        if (intakeBeltRear.get() != 0)
            intakeBeltRear.stopMotor();
    }
    public void tryStopIntakeRoller()
    {
        if (intakeRoller.get() != 0)
            intakeRoller.stopMotor();
    }
    public void tryCloseIntakeSolenoid()
    {
        if (intakeSolenoid.get())
            intakeSolenoid.set(false);
    }

    public boolean isIntakeExtended()
    {
        return intakeSolenoid.get();
    }
    public void reset()
    {
        ballCount = 0;
        intakeState = IntakeState.IntakeDeactivated;
        // Test if solonoids get turned off when disabled, without what is below
        tryCloseIntakeSolenoid();
    }

}
