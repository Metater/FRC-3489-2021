package frc.robot.general;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.auto.*;
import frc.robot.auto.AutoInstruction.InstructionType;;

public class AutoHandler {

    public enum AutoType
    {
        Slalom
    }

    private RobotHandler robotHandler;

    public AutoType autoType;
    public int currentStep = 0;

    private AutoInstruction[] selectedAuto;
    private Map<AutoType, AutoInstruction[]> autos = new HashMap<AutoType, AutoInstruction[]>();

    private AutoInstruction[] auto1 = {
        waitFor(2),
        drive(0.6, 500),
        stop()
    };

    private void populateAutoList()
    {
        autos.put(AutoType.Slalom, auto1);
    }
    
    public AutoHandler(RobotHandler robotHandler, AutoType autoType)
    {
        this.robotHandler = robotHandler;
        this.autoType = autoType;
        populateAutoList();
        selectedAuto = autos.get(autoType); // May not work the way i think, research hashmaps
    }

    public void autonomousInit()
    {

    }

    public void autonomousPeriodic()
    {
        if (selectedAuto[currentStep].isFinished) currentStep++;
        if (selectedAuto.length > currentStep)
            selectedAuto[currentStep].cycle();
    }


    private AutoInstruction waitFor(double waitTime) {
        return new AutoInstruction(InstructionType.Wait, group1(waitTime), robotHandler);
    }
    private AutoInstruction stop() {
        return waitFor(0);
    }
    private AutoInstruction tank(double speedLeft, double speedRight, double clicks) {
        return new AutoInstruction(InstructionType.Tank, group3(speedLeft, speedRight, clicks), robotHandler);
    }
    private AutoInstruction drive(double speed, double clicks) {
        return new AutoInstruction(InstructionType.Drive, group2(speed, clicks), robotHandler);
    }
    private AutoInstruction turnLeft(double speed, double clicks) {
        return new AutoInstruction(InstructionType.TurnLeft, group2(speed, clicks), robotHandler);
    }
    private AutoInstruction turnRight(double speed, double clicks) {
        return new AutoInstruction(InstructionType.TurnRight, group2(speed, clicks), robotHandler);
    }
    private AutoInstruction trainLeft(double speed, double clicks) {
        return new AutoInstruction(InstructionType.TrainLeft, group2(speed, clicks), robotHandler);
    }
    private AutoInstruction trainRight(double speed, double clicks) {
        return new AutoInstruction(InstructionType.TrainRight, group2(speed, clicks), robotHandler);
    }



    private double[] group1(double a)
    {
        double[] values = {a};
        return values;
    }
    private double[] group2(double a, double b)
    {
        double[] values = {a, b};
        return values;
    }
    private double[] group3(double a, double b, double c)
    {
        double[] values = {a, b, c};
        return values;
    }
}
