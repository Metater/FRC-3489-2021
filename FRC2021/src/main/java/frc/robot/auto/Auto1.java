package frc.robot.auto;

import java.util.ArrayList;

import frc.robot.general.RobotHandler;

public class Auto1 extends AutoRunner {

    public RobotHandler robotHandler;

    public ArrayList<AutoInstruction> instructions = new ArrayList<AutoInstruction>();

    public int currentStep = 0;

    public Auto1(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;

        DriveStraightAI test = new DriveStraightAI(robotHandler, 1, 0.4);

        instructions.add(test);
    }

    public void cycle()
    {
        if (currentStep == instructions.size()) return;

        AutoInstruction instruction = instructions.get(currentStep);
        if (!instruction.isFinished())
            instruction.run();
        else
            currentStep++;
    }
}
