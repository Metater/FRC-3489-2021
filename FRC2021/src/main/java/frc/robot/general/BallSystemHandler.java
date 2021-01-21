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

    public void cycle()
    {
        
    }
    public void tryToggleIntake()
    {
        //if (robotHandler.inputHandler.)
    }
}
