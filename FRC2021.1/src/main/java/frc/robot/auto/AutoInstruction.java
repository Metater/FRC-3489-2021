package frc.robot.auto;

import java.util.function.Function;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.general.AutoHandler;
import frc.robot.general.RobotHandler;

public class AutoInstruction {

    public enum InstructionType
    {
        Wait,
        Drive,
        TurnLeft,
        TurnRight,
        Tank,
        TrainLeft,
        TrainRight
    }

    // ------------------------------------------------------------------
    // Left off on reordering enums and methods, in both autohandler and here
    // ------------------------------------------------------------------

    private RobotHandler robotHandler;

    public InstructionType instructionType;
    public double[] values;
    public boolean isFinished = false;

    private WPI_TalonSRX clicksReference;
    private double finishTime;
    private boolean isFirstCycle = true;

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
            case TrainLeft:
                trainLeftInstruction(values[0], values[1]);
                break;
            case TrainRight:
                trainRightInstruction(values[0], values[1]);
                break;
            default:
                break;
        }
    }
    private void waitInstruction(double waitTime)
    {
        if (isFirstCycle())
        {
            finishTime = Timer.getFPGATimestamp() + waitTime;
            robotHandler.driveHandler.differentialDrive.stopMotor();
        }

        if (finishTime <= Timer.getFPGATimestamp())
            isFinished = true;
    }

    private void driveInstruction(double speed, double clicks) {
        tankInstruction(speed, speed, clicks);
    }
    private void turnLeftInstruction(double speed, double clicks) {
        tankInstruction(speed * -1, speed, clicks);
    }
    private void turnRightInstruction(double speed, double clicks) {
        tankInstruction(speed, speed * -1, clicks);
    }
    private void trainLeftInstruction(double speed, double clicks) {
        tankInstruction(speed, 0, clicks);
    }
    private void trainRightInstruction(double speed, double clicks) {
        tankInstruction(0, speed, clicks);
    }

    private double startClicks = 0;
    private void tankInstruction(double speedLeft, double speedRight, double clicks)
    {
        if (isFirstCycle()) startClicks = clicksReference.getSelectedSensorPosition();

        if (Math.abs(startClicks - clicksReference.getSelectedSensorPosition()) < clicks)
            tankDrive(speedLeft, speedRight);
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

    private void tankDrive(double speedLeft, double speedRight)
    {
        robotHandler.driveHandler.differentialDrive.tankDrive(speedLeft, speedRight);
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