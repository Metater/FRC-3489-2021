package frc.robot.general;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

public class BallSystemHandler {
    
    private RobotHandler robotHandler;

    //digital inputs section!!
    DigitalInput ballInputSensor = new DigitalInput(Constants.DigitalInputs.INTAKE_BELT_BOTTOM_SENSOR);
    // Could use output sensor for seeing if all balls are out out

    WPI_TalonSRX intakeBeltFront = new WPI_TalonSRX(Constants.Motors.INTAKE_BELT_FRONT);
    WPI_TalonFX intakeBeltRear = new WPI_TalonFX(Constants.Motors.INTAKE_BELT_REAR);

    WPI_TalonSRX intakeRoller = new WPI_TalonSRX(Constants.Motors.INTAKE_ROLLER);

    Solenoid intakeSolenoid = new Solenoid(Constants.Solenoids.PCM_NUMBER, Constants.Solenoids.INTAKE_ROLLER);

    public IntakeState intakeState = IntakeState.IntakeDeactivated;
    public enum IntakeState
    {
        IntakeManual,
        IntakeDeactivated,
        IntakeWaiting,
        IntakeIndexing,
        IntakeFull
    }
    public int ballCount = 0;
    public boolean isIntakeExtended = false;
    public double lastIntakeToggleTime;
    public double encoderTarget = 0;
    public double waitImDumbThoseAreEncoderClicks = 20000; // clicksPerIndex

    public BallSystemHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void teleopPeriodic()
    {
        tryToggleIntake();
        tryMoveBallBeltManually();

        updateShuffleboard();

        handleIntakeState();
    }
    private void updateShuffleboard()
    {
        System.out.println(ballCount);

        robotHandler.shuffleboardHandler.PrintStringToWidget("Intake State", intakeState.toString());
        //if (ballCount == 5)
            //robotHandler.shuffleboardHandler.PrintBooleanToWidget("Intake Full", true);
        //else
            //robotHandler.shuffleboardHandler.PrintBooleanToWidget("Intake Full", false);

        //robotHandler.shuffleboardHandler.PrintDoubleToWidget("Front Clicks: ", intakeBeltFront.getSelectedSensorPosition());
        robotHandler.shuffleboardHandler.PrintDoubleToWidget("Rear Clicks: ", intakeBeltRear.getSelectedSensorPosition());
    }
    private void handleIntakeState()
    {
        switch(intakeState)
        {
            case IntakeManual:
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
    private void intakeDeactivated()
    {
        intakeBeltFront.stopMotor();
        intakeBeltRear.stopMotor();
        if (isIntakeExtended) intakeState = IntakeState.IntakeWaiting;

    }
    private void intakeWaiting()
    {
        boolean ballInSensor = !ballInputSensor.get();
        if (ballCount == 4 && ballInSensor)
        {
            ballCount++;
            intakeState = IntakeState.IntakeFull;
        }
        else if(ballInSensor)
        {

            intakeState = IntakeState.IntakeIndexing;
            encoderTarget = Math.abs(intakeBeltRear.getSelectedSensorPosition()) + waitImDumbThoseAreEncoderClicks;
        }
        else
        {
            intakeBeltFront.set(Constants.INTAKE_BELT_FRONT_SPEED);
        }
        intakeBeltRear.stopMotor();
    }
    private void intakeIndexing()
    {
        System.out.println(intakeBeltRear.getSelectedSensorPosition() + ":::::" + encoderTarget);
        if (Math.abs(intakeBeltRear.getSelectedSensorPosition()) >= encoderTarget)
        {
            ballCount++;
            intakeState = IntakeState.IntakeWaiting;
        }
        else
        {
            intakeBeltFront.set(Constants.INTAKE_BELT_FRONT_SPEED);
            intakeBeltRear.set(Constants.INTAKE_BELT_REAR_SPEED);
        }


    }
    private void intakeFull()
    {
        intakeSolenoid.set(false);
        isIntakeExtended = false;
        intakeBeltFront.stopMotor();
        intakeBeltRear.stopMotor();
        intakeRoller.stopMotor();
    }

    /*
            if (!isIntakeExtened)
            return;


        //if (robotHandler.stateHandler.ballCount <= 4) //if count is less than or = to 4


        if (!ballInputSensor.get()) //if theres a ball in the sensor
        {
            intakeBeltFront.set(Constants.INTAKE_BELT_FRONT_SPEED); //set small belt to 0.4
            intakeBeltRear.set(Constants.INTAKE_BELT_REAR_SPEED);
            isIndexingBall = true;  
        }
        if (ballInputSensor.get() && isIndexingBall) //if ball sensor is empty and is done indexing ball
        {
            isIndexingBall = false; //then index big belt
        }
    */
    private void tryToggleIntake()
    {
        if (robotHandler.inputHandler.shouldToggleIntake())
        {
            robotHandler.stateHandler.toggleIntake();
            if (isIntakeExtended) // Push intake out
                intakeSolenoid.set(true); // Push intake pneumatics out
            else // Pull intake in
                intakeSolenoid.set(false); // Pull intake pneumatics in

            intakeState = IntakeState.IntakeDeactivated;
        }
        if (isIntakeExtended) // Push intake out, and spin
        {
            double statorCurrent = intakeRoller.getStatorCurrent();
            if ((Math.abs(statorCurrent) < Constants.ZUCC_JAM_CURRENT) && !robotHandler.stateHandler.commitingToUnjam)
            {
                // Tune current late for antijam
                intakeRoller.set(Constants.ZUCC_SPEED);


                
                // ---------------------------------------------------------------
                // Worry about this line, and account for it when 4 balls are held
                // ---------------------------------------------------------------
                intakeBeltFront.set(Constants.INTAKE_BELT_FRONT_SPEED);
            }
            else if (!robotHandler.stateHandler.commitingToUnjam)
            {
                robotHandler.stateHandler.commitToUnjam();
                intakeRoller.set(Constants.ZUCC_JAM_SPEED);
            }
            else // If already commited to unjam
            {
                if (robotHandler.stateHandler.lastCommitToUnjamTime + 1 > Timer.getFPGATimestamp())
                    intakeRoller.set(Constants.ZUCC_JAM_SPEED);
                else
                    robotHandler.stateHandler.uncommitToUnjam();
            }
        }
        // May have to move pneumatics setting code out here, may start or stop in weird ways, if we do,
        // get current state pneumatics are in with robotHandler.stateHandler.isIntakeExtened, and if true,
        // extend pneumatics, else pull in, the code above only runs on toggle, that could be why
    }

    private void tryMoveBallBeltManually()
    {
        double manStickSpeed = robotHandler.inputHandler.getManStickSpeed();
        if (Math.abs(manStickSpeed) > Constants.MAN_STICK_SPEED_CUTOFF - 0.1)
        {
            intakeBeltFront.set(manStickSpeed * -1);
            intakeBeltRear.set(manStickSpeed);
            ballCount = 0;
        }
    }
    //-----------------------------------------------------------------------------------------------
    // MAKE ANOTHER BUTTON TO PRESS WHILE THE BLEH, FOR UNJAMMING STUFF, IT WONT RESET THE BALL COUNT
    //-----------------------------------------------------------------------------------------------
}
