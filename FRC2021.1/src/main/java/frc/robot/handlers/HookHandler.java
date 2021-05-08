package frc.robot.handlers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;

public class HookHandler {
    
    private RobotHandler robotHandler;

    public WPI_TalonFX winch = new WPI_TalonFX(Constants.Motors.WINCH);
    public Solenoid winchSolenoidLeft = new Solenoid(Constants.Solenoids.PCM_NUMBER, Constants.Solenoids.SCISSOR_LIFT_PART_1);
    public Solenoid winchSolenoidRight = new Solenoid(Constants.Solenoids.PCM_NUMBER, Constants.Solenoids.SCISSOR_LIFT_PART_2);

    public HookHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        setSolenoids(false);
    }

    public void teleopPeriodic()
    {
        setSolenoids(robotHandler.inputHandler.shouldScissorLift());
        winch.set(robotHandler.inputHandler.winchSpeed());
    }

    private void setSolenoids(boolean value)
    {
        winchSolenoidLeft.set(value);
        winchSolenoidRight.set(!value);
    }
}
