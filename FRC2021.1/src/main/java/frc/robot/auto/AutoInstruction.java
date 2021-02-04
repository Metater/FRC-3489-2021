package frc.robot.auto;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;

public class AutoInstruction {

    public InstructionType instructionType;
    public double[] values;
    public boolean isFinished = false;

    public double finishTime;

    public enum InstructionType
    {
        Wait,
        Drive,
        TurnLeft,
        TurnRight,
        Tank
    }

    public AutoInstruction(InstructionType instructionType, double[] values)
    {
        this.instructionType = instructionType;
        this.values = values;
    }

    public void cycle()
    {
        switch(instructionType)
        {
            case Wait:
                waitInstruction(values[0]);
                break;
            case Drive:
                driveInstruction(values[0], values[1], new WPI_TalonSRX(0));
                break;
            case TurnLeft:
                turnLeftInstruction(values[0]);
                break;
            case TurnRight:
                turnRightInstruction(values[0]);
                break;
            case Tank:
                tankInstruction(values[0]);
                break;
            default:
                break;
        }
    }
    private boolean isWaitInit = false;
    public void waitInstruction(double waitTime)
    {
        if (!isWaitInit)
        {
            isWaitInit = true;
            finishTime = Timer.getFPGATimestamp() + waitTime;
        }
        if (finishTime <= Timer.getFPGATimestamp())
        {
            isFinished = true;
        }
    }
    public void driveInstruction(double driveSpeedLeft, double driveSpeedRight, WPI_TalonSRX clicksReference)
    {
        //if (clicksReference.getSelectedSensorPosition())
    }
    public void turnLeftInstruction(double turnSpeed)
    {

    }
    public void turnRightInstruction(double turnSpeed)
    {

    }
    public void tankInstruction(double tankSpeed)
    {

    }


    public void driveWhileCorrectingInstruction(WPI_TalonSRX encoderProvider)
    {
        double velocityThreshold = 10;

        double velocity = Math.abs(encoderProvider.getSelectedSensorVelocity());

        if (velocity < velocityThreshold) velocity = 0;

        // Use a circular fifo queue, or write something similar to get acceleration, or jerk
        // Have thresholds for each of those, and use those values changing as expected, or not expected
        //to adjust the speed over time
        // Use acceleration to smooth out occilations
        // Remember to wipe, the queues for calculating accel, or jerk whenever the speed is set differently
        // Instead of wiping the queues, it could also be predicted
    }
}