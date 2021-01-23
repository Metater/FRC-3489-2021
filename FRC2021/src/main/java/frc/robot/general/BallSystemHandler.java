package frc.robot.general;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

public class BallSystemHandler {
    
    private RobotHandler robotHandler;

    //WPI_TalonSRX intakeBeltFront = new WPI_TalonSRX(Constants.Motors.INTAKE_BELT_FRONT);
    //WPI_TalonFX intakeBeltRear = new WPI_TalonFX(Constants.Motors.INTAKE_BELT_REAR);
    WPI_TalonSRX intakeBeltFront = new WPI_TalonSRX(1);
    WPI_TalonSRX intakeBeltRear = new WPI_TalonSRX(8);

    //WPI_TalonSRX intakeRoller = new WPI_TalonSRX(Constants.Motors.INTAKE_ROLLER);
    //WPI_TalonSRX intakeRoller = new WPI_TalonSRX(1);

    //Solenoid intakeSolenoid = new Solenoid(Constants.Solenoids.PCM_NUMBER, Constants.Solenoids.INTAKE_ROLLER);

    public BallSystemHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void teleopPeriodic()
    {
        //tryToggleIntake();
        tryMoveBallBelt();
    }
    /*
    private void tryToggleIntake()
    {
        if (robotHandler.inputHandler.shouldToggleIntake())
        {
            robotHandler.stateHandler.toggleIntake();
            if (robotHandler.stateHandler.isIntakeExtened) // Push intake out, and spin
            {
                //intakeSolenoid.set(true); // Push intake pneumatics out
            }
            else // Pull intake in, and stop spin
            {
                //intakeSolenoid.set(false); // Pull intake pneumatics in
            }
        }
        if (robotHandler.stateHandler.isIntakeExtened) // Push intake out, and spin
        {

            //intakeRoller.set(-0.4);

            double statorCurrent = intakeRoller.getStatorCurrent();
            if ((Math.abs(statorCurrent) < Constants.ZUCC_JAM_CURRENT) && !robotHandler.stateHandler.commitingToUnjam)
                intakeRoller.set(-0.25);
                //intakeRoller.set(Constants.ZUCC_SPEED);
            else if (!robotHandler.stateHandler.commitingToUnjam)
            {
                robotHandler.stateHandler.commitToUnjam();
                intakeRoller.set(Constants.ZUCC_JAM_SPEED);
            }
            else // If already commited to unjam
            {
                if (robotHandler.stateHandler.lastCommitToUnjamTime + 1 > Timer.getFPGATimestamp())
                {
                    intakeRoller.set(Constants.ZUCC_JAM_SPEED);
                    System.out.println("ITS JAMMING, YOU DONUT- gordon jamsey");
                }
                else
                {
                    robotHandler.stateHandler.uncommitToUnjam();
                }
            }
            

            System.out.println("Stator Current: " + statorCurrent);
        }
        else
        {
            System.out.println("JOEISYESfruigdsfigyewfygig");
            intakeRoller.stopMotor();
        }
        // May have to move pneumatics setting code out here, may start or stop in weird ways, if we do,
        // get current state pneumatics are in with robotHandler.stateHandler.isIntakeExtened, and if true,
        // extend pneumatics, else pull in, the code above only runs on toggle, that could be why
    }
    */

    private void tryMoveBallBelt()
    {
        double manStickSpeed = robotHandler.inputHandler.getManStickSpeed();
        intakeBeltFront.set(manStickSpeed);
        intakeBeltRear.set(manStickSpeed);
        System.out.println(manStickSpeed);
        robotHandler.stateHandler.ballCount = 0;
    }
    //-----------------------------------------------------------------------------------------------
    // MAKE ANOTHER BUTTON TO PRESS WHILE THE BLEH, FOR UNJAMMING STUFF, IT WONT RESET THE BALL COUNT
    //-----------------------------------------------------------------------------------------------
}
