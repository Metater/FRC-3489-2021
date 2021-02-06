package frc.robot.general;

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
    }

    public void teleopPeriodic()
    {
        if (robotHandler.inputHandler.shouldScissorLift())
            solenoidsOn();
        else
            solenoidsOff();
    }

    private void solenoidsOn()
    {
        winchSolenoidLeft.set(true);
        winchSolenoidRight.set(true);
    }
    private void solenoidsOff()
    {
        winchSolenoidLeft.set(false);
        winchSolenoidRight.set(false);
    }
}
