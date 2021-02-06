package frc.robot.general;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.auto.*;
import frc.robot.auto.AutoInstruction.InstructionType;;

public class AutoHandler {

    private RobotHandler robotHandler;

    private int currentStep = 0;
    
    private AutoInstruction[] auto1 = {
        waitFor(2),
        drive(0.6, 500),
        stop()
    };
    
    public AutoHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void autonomousInit()
    {

    }

    public void autonomousPeriodic()
    {
        if (auto1[currentStep].isFinished) currentStep++;
        if (auto1.length > currentStep)
            auto1[currentStep].cycle();
    }


    private AutoInstruction waitFor(double waitTime)
    {
        double[] values = {waitTime};
        return new AutoInstruction(InstructionType.Wait, values, robotHandler);
    }
    private AutoInstruction tank(double tankSpeedLeft, double tankSpeedRight, double clicks)
    {
        double[] values = {tankSpeedLeft, tankSpeedRight, clicks};
        return new AutoInstruction(InstructionType.Tank, values, robotHandler);
    }
    private AutoInstruction drive(double driveSpeed, double clicks)
    {
        double[] values = {driveSpeed, clicks};
        return new AutoInstruction(InstructionType.Drive, values, robotHandler);
    }
    private AutoInstruction turnLeft(double turnSpeed, double clicks)
    {
        double[] values = {turnSpeed};
        return new AutoInstruction(InstructionType.TurnLeft, values, robotHandler);
    }
    private AutoInstruction turnRight(double turnSpeed, double clicks)
    {
        double[] values = {turnSpeed};
        return new AutoInstruction(InstructionType.TurnRight, values, robotHandler);
    }
    private AutoInstruction stop()
    {
        return waitFor(0);
    }
}
