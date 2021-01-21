package frc.robot.general;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;

public class BallSystemHandler {
    
    private RobotHandler robotHandler;

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
        
    }
    public void tryToggleIntake()
    {
        if (robotHandler.inputHandler.shouldToggleIntake())
        {
            robotHandler.stateHandler.toggleIntake();
            if (robotHandler.stateHandler.isIntakeExtened) // Push intake out, and spin
            {
                intakeSolenoid.set(true); // Push intake pneumatics out
                intakeRoller.set();
            }
            else // Pull intake in, and stop spin
            {
                intakeSolenoid.set(false); // Pull intake pneumatics in
                intakeRoller.stopMotor(); // Stop intake roller
            }
        }
        // May have to move pneumatics setting code out here, may start or stop in weird ways, if we do,
        // get current state pneumatics are in with robotHandler.stateHandler.isIntakeExtened, and if true,
        // extend pneumatics, else pull in, the code above only runs on toggle, that could be why
    }
}
