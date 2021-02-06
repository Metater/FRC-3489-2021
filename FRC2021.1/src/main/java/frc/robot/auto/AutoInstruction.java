package frc.robot.auto;

import java.util.function.Function;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.general.AutoHandler;
import frc.robot.general.RobotHandler;

public class AutoInstruction {

    private RobotHandler robotHandler;

    public InstructionType instructionType;
    public double[] values;
    public boolean isFinished = false;

    private WPI_TalonSRX clicksReference;

    public double finishTime;

    private boolean isFirstCycle = true;

    public enum InstructionType
    {
        Wait,
        Drive,
        TurnLeft,
        TurnRight,
        Tank
    }

    public AutoInstruction(InstructionType instructionType, double[] values, RobotHandler robotHandler)
    {
        this.instructionType = instructionType;
        this.values = values;
        this.robotHandler = robotHandler;

        clicksReference = robotHandler.driveHandler._leftFront;
    }

    public void cycle()
    {
        switch(instructionType)
        {
            case Wait:
                waitInstruction(values[0]);
                break;
            case Drive:
                driveInstruction(values[0], values[1]);
                break;
            case TurnLeft:
                turnLeftInstruction(values[0], values[1]);
                break;
            case TurnRight:
                turnRightInstruction(values[0], values[1]);
                break;
            case Tank:
                tankInstruction(values[0], values[1], values[2]);
                break;
            default:
                break;
        }
    }
    public void waitInstruction(double waitTime)
    {
        if (isFirstCycle())
        {
            finishTime = Timer.getFPGATimestamp() + waitTime;
            robotHandler.driveHandler.differentialDrive.stopMotor();
        }

        if (finishTime <= Timer.getFPGATimestamp())
            isFinished = true;
    }

    public void driveInstruction(double driveSpeed, double driveClicks) {
        tankInstruction(driveSpeed, driveSpeed, driveClicks);
    }
    public void turnLeftInstruction(double turnSpeed, double turnClicks) {
        tankInstruction(turnSpeed * -1, turnSpeed, turnClicks);
    }
    public void turnRightInstruction(double turnSpeed, double turnClicks) {
        tankInstruction(turnSpeed, turnSpeed * -1, turnClicks);
    }

    private double tankStartClicks = 0;
    public void tankInstruction(double tankSpeedLeft, double tankSpeedRight, double tankClicks)
    {
        if (isFirstCycle()) tankStartClicks = clicksReference.getSelectedSensorPosition();

        if (Math.abs(tankStartClicks - clicksReference.getSelectedSensorPosition()) < tankClicks)
            tankDrive(tankSpeedLeft, tankSpeedRight);
        else
            isFinished = true;
    }

    private boolean isFirstCycle()
    {
        if (isFirstCycle)
            return true;
        isFirstCycle = false;
        return false;
    }

    private void tankDrive(double tankSpeedLeft, double tankSpeedRight)
    {
        robotHandler.driveHandler.differentialDrive.tankDrive(tankSpeedLeft, tankSpeedRight);
    }

    /*
    double previousVelocity = 0;
    public void driveWhileCorrectingVelocityInstruction(WPI_TalonSRX encoder)
    {
        double velocityThreshold = 10;
        double velocity = Math.abs(encoder.getSelectedSensorVelocity());


        if (Math.abs(velocity) < velocityThreshold) velocity = 0;
        previousVelocity = velocity;
    }
    */

    // Use a circular fifo queue, or write something similar to get acceleration, or jerk
    // Have thresholds for each of those, and use those values changing as expected, or not expected
    //to adjust the speed over time
    // Use acceleration to smooth out occilations
    // Remember to wipe, the queues for calculating accel, or jerk whenever the speed is set differently
    // Instead of wiping the queues, it could also be predicted
}