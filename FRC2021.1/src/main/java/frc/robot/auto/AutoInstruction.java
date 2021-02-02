package frc.robot.auto;

import edu.wpi.first.wpilibj.Timer;

public class AutoInstruction {

    public InstructionType instructionType;
    public double[] values;
    public enum InstructionType
    {
        Wait,
        Drive,
        TurnLeft,
        TurnRight
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
                driveInstruction();
                break;
            case TurnLeft:
                turnLeftInstruction();
                break;
            case TurnRight:
                turnRightInstruction();
                break;
            default:
                break;
        }
    }
    private boolean isWaitInit = false;
    private double waitFinishTime;
    public void waitInstruction(double waitTime)
    {
        if (!isWaitInit)
        {
            isWaitInit = true;
            waitFinishTime = Timer.getFPGATimestamp() + waitTime;
        }
        if ()
    }
    public void driveInstruction()
    {

    }
    public void turnLeftInstruction()
    {

    }
    public void turnRightInstruction()
    {

    }
}