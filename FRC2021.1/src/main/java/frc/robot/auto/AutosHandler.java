package frc.robot.auto;

import java.util.HashMap;
import java.util.Map;

import frc.robot.general.AutoHandler;
import frc.robot.auto.*;
import frc.robot.auto.AutoInstruction.InstructionType;

public class AutosHandler {

    public enum AutoType
    {
        Joe,
        Slalom,
        TheChugLife,
        Bounce,
        Barrel,
        HyperPathA,
        HyperPathB,
        TestAuto
    }

    private AutoHandler autoHandler;
    public Map<AutoType, AutoInstruction[]> autos = new HashMap<AutoType, AutoInstruction[]>();
    
    public AutosHandler(AutoHandler autoHandler)
    {
        this.autoHandler = autoHandler;
        populateAutoList();
    }

    private void populateAutoList()
    {
        autos.put(AutoType.Slalom, autoSlalom);
        autos.put(AutoType.Joe, autoJoe);
        autos.put(AutoType.TheChugLife, autoTheChugLife);
        autos.put(AutoType.Bounce, autoBounce);
        autos.put(AutoType.Barrel, autoBarrel);
        autos.put(AutoType.HyperPathA, autoHyperPathA);
        autos.put(AutoType.HyperPathB, autoHyperPathB);
        autos.put(AutoType.TestAuto, autoTestAuto);
    }





    
    public AutoInstruction waitFor(double waitTime) {
        return new AutoInstruction(InstructionType.Wait, group1(waitTime), autoHandler);
    }
    public AutoInstruction stop() {
        return waitFor(0);
    }
    public AutoInstruction tank(double speedLeft, double speedRight, double clicks) {
        return new AutoInstruction(InstructionType.Tank, group3(speedLeft, speedRight, clicks), autoHandler);
    }
    public AutoInstruction drive(double speed, double clicks) {
        return new AutoInstruction(InstructionType.Drive, group2(speed, clicks), autoHandler);
    }
    public AutoInstruction turnLeft(double speed, double clicks) {
        return new AutoInstruction(InstructionType.TurnLeft, group2(speed, clicks), autoHandler);
    }
    public AutoInstruction turnRight(double speed, double clicks) {
        return new AutoInstruction(InstructionType.TurnRight, group2(speed, clicks), autoHandler);
    }
    public AutoInstruction trainLeft(double speed, double clicks) {
        return new AutoInstruction(InstructionType.TrainLeft, group2(speed, clicks), autoHandler);
    }
    public AutoInstruction trainRight(double speed, double clicks) {
        return new AutoInstruction(InstructionType.TrainRight, group2(speed, clicks), autoHandler);
    }
    public AutoInstruction zuccWithTank(double zuccRollerSpeed, double zuccFrontBeltSpeed, double tankSpeedLeft, double tankSpeedRight, double tankClicks) {
        return new AutoInstruction(InstructionType.ZuccAndTank, group5(zuccRollerSpeed, zuccFrontBeltSpeed, tankSpeedLeft, tankSpeedRight, tankClicks), autoHandler);
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
    private double[] group5(double a, double b, double c, double d, double e)
    {
        double[] values = {a, b, c, d, e};
        return values;
    }
}
