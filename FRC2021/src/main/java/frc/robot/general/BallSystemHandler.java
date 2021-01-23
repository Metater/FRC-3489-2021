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
   

    WPI_TalonSRX intakeBeltFront = new WPI_TalonSRX(Constants.Motors.INTAKE_BELT_FRONT);
    WPI_TalonFX intakeBeltRear = new WPI_TalonFX(Constants.Motors.INTAKE_BELT_REAR);

    WPI_TalonSRX intakeRoller = new WPI_TalonSRX(Constants.Motors.INTAKE_ROLLER);

    Solenoid intakeSolenoid = new Solenoid(Constants.Solenoids.PCM_NUMBER, Constants.Solenoids.INTAKE_ROLLER);

    public BallSystemHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void teleopPeriodic()
    {
        tryToggleIntake();
        tryMoveBallBeltManually();
        printEncoderClicks();
        tryIndexBall();
    }
    private void printEncoderClicks()
    {
        robotHandler.shuffleboardHandler.PrintDoubleToWidget("Front Clicks: ", intakeBeltFront.getSelectedSensorPosition());
        robotHandler.shuffleboardHandler.PrintDoubleToWidget("Rear Clicks: ", intakeBeltRear.getSelectedSensorPosition());
    }
    private void tryIndexBall()
    {
        if (!robotHandler.stateHandler.isIntakeExtened)
            return;


        //if (robotHandler.stateHandler.ballCount <= 4) //if count is less than or = to 4


        if (!ballInputSensor.get()) //if theres a ball in the sensor
        {
            intakeBeltFront.set(Constants.INTAKE_BELT_FRONT_SPEED); //set small belt to 0.4
            intakeBeltRear.set(Constants.INTAKE_BELT_REAR_SPEED);
            robotHandler.stateHandler.isIndexingBall = true;  
        }
        if (ballInputSensor.get() && robotHandler.stateHandler.isIndexingBall) //if ball sensor is empty and is done indexing ball
        {
            robotHandler.stateHandler.isIndexingBall = false; //then index big belt
        }
    }
    private void tryToggleIntake()
    {
        if (robotHandler.inputHandler.shouldToggleIntake())
        {
            robotHandler.stateHandler.toggleIntake();
            if (robotHandler.stateHandler.isIntakeExtened) // Push intake out
                intakeSolenoid.set(true); // Push intake pneumatics out
            else // Pull intake in
                intakeSolenoid.set(false); // Pull intake pneumatics in
        }
        if (robotHandler.stateHandler.isIntakeExtened) // Push intake out, and spin
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
        else
        {
            intakeRoller.stopMotor();
        }
        // May have to move pneumatics setting code out here, may start or stop in weird ways, if we do,
        // get current state pneumatics are in with robotHandler.stateHandler.isIntakeExtened, and if true,
        // extend pneumatics, else pull in, the code above only runs on toggle, that could be why
    }

    private void tryMoveBallBeltManually()
    {
        double manStickSpeed = robotHandler.inputHandler.getManStickSpeed();
        intakeBeltFront.set(manStickSpeed * -1);
        intakeBeltRear.set(manStickSpeed);
        System.out.println(manStickSpeed);
        robotHandler.stateHandler.ballCount = 0;
    }
    //-----------------------------------------------------------------------------------------------
    // MAKE ANOTHER BUTTON TO PRESS WHILE THE BLEH, FOR UNJAMMING STUFF, IT WONT RESET THE BALL COUNT
    //-----------------------------------------------------------------------------------------------
}
