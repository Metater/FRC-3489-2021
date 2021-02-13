package frc.robot.general;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.auto.*;
import frc.robot.auto.AutoInstruction.InstructionType;
import frc.robot.auto.AutosHandler.AutoType;

public class AutoHandler {

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

    private RobotHandler robotHandler;

    public Map<AutoType, AutoInstruction[]> autos = new HashMap<AutoType, AutoInstruction[]>();

    public int currentStep = 0;

    private AutoType autoType;
    private AutoInstruction[] selectedAuto;
    
    public AutoHandler(RobotHandler robotHandler, AutoType autoType)
    {
        this.robotHandler = robotHandler;
        this.autoType = autoType;

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

    public void autonomousInit()
    {
        selectedAuto = autos.get(autoType);

        robotHandler.driveHandler.differentialDrive.setSafetyEnabled(false);
    }

    public void autonomousPeriodic()
    {
        if (selectedAuto.length-1 > currentStep)
        {
            if (selectedAuto[currentStep].isFinished) currentStep++;
            selectedAuto[currentStep].cycle();
            System.out.println("Running");
        }
        else
        {
            System.out.println("Stopped");
        }
    }


    public DriveHandler getDriveHandler() {
        return robotHandler.driveHandler;
    }
    public BallSystemHandler getBallSystemHandler() {
        return robotHandler.ballSystemHandler;
    }


    public AutoInstruction[] autoSlalom = {
        // Clear start box, and move to left of main line
        drive(0.8, 3000),
        waitFor(1),
        turnLeft(0.7, 700),
        drive(0.8, 6800),
        waitFor(2),
        turnRight(0.8, 630),
        waitFor(1),
        // Go zoom to other end
        drive(0.9, 19400),
        waitFor(2),
        turnRight(0.8, 750),
        waitFor(1),
        drive(0.8, 7000),
        waitFor(2),
        // Do fancy and very hard loop
        tank(0.335, 0.68, 13000),
        tank(0.3, 0.67, 10000),
        waitFor(2),
        drive(0.8, 6000),
        waitFor(1),
        turnLeft(0.8, 750),
        waitFor(1),
        drive(0.8, 6000),
        waitFor(1),
        turnRight(0.8, 900),
        waitFor(1),
        drive(0.9, 19400),
        waitFor(1),
        stop()
    };
    public AutoInstruction[] autoJoe = {
        waitFor(2),
        drive(0.8, 1000),
        waitFor(1),
        turnLeft(0.6, 3000),
        waitFor(1),
        turnRight(0.6, 3000),
        waitFor(1),
        drive(-0.6, 1000),
        waitFor(2),
        drive(0.6, 1000),
        waitFor(1),
        turnLeft(0.6, 3000),
        waitFor(1),
        turnRight(0.6, 3000),
        waitFor(1),
        drive(-0.6, 1000),
        stop()
    };
    public AutoInstruction[] autoTheChugLife = {
        waitFor(4),
        tank(1.0, 0.4, 750),
        waitFor(1),
        tank(0.4, 1.0, 750),
        stop()
    };
    
    public AutoInstruction[] autoBounce = {
        tank(0.3, 0.67, 5000), //curved left turn
        waitFor(1),
        drive(0.8, 6000),
        waitFor(1),
        tank(-0.8, -0.1, 10000),//funny curved backwards turn
        tank(-0.6, -0.2, 15000),//funnier curved backwards turn
        waitFor(1),
        drive(-0.8, 15000),
        waitFor(1),
        drive(0.8, 20000),
        waitFor(1),
        tank(0.3, 0.8, 10000),
        waitFor(1),
        drive(0.8, 10000),
        tank(-0.8, -0.3, 5000),
        stop()

    };

    public AutoInstruction[] autoBarrel = {
        drive(0.8, 10000),
        waitFor(1),
        tank(0.8, 0.3, 10000),
        waitFor(1),
        drive(0.8, 5000),
        waitFor(1),
        tank(0.8, 0.3, 3000),
        waitFor(1),
        drive(0.8, 10000),
        waitFor(1),
        tank(0.3, 0.8, 10000),
        waitFor(1),
        drive(0.8, 5000),
        waitFor(1),
        drive(0.8, 5000),
        waitFor(1),
        tank(0.8, 0.3, 5000),
        waitFor(1),
        drive(0.6, 250),
        drive(0.8, 500),
        drive(1.0, 20000),
        stop()
    };

    public AutoInstruction[] autoHyperPathA = {
        drive(0.6, 3000),
        //add zucc and tank here
        waitFor(1),
        turnRight(0.6, 2000),
        waitFor(1),
        drive(0.6, 2000),
        //add zucc and tank here
        waitFor(1),
        turnLeft(0.6, 5000),
        waitFor(1),
        drive(0.8, 4000),
        //add zucc and tank here
        waitFor(1),
        turnRight(0.6, 2000),
        waitFor(1),
        drive(0.6, 250),
        drive(0.8, 500),
        drive(1, 8000),

    };

    public AutoInstruction[] autoHyperPathB = {
        drive(0.6, 3000),
        //add zucc and tank here
        waitFor(1),
        turnRight(0.6, 2000),
        waitFor(1),
        drive(0.6, 2000),
        //add zucc and tank here
        waitFor(1),
        turnLeft(0.6, 5000),
        waitFor(1),
        drive(0.8, 4000),
        //add zucc and tank here
        waitFor(1),
        turnRight(0.6, 2000),
        waitFor(1),
        drive(0.6, 250),
        drive(0.8, 500),
        drive(1, 8000),

    };

    public AutoInstruction[] autoTestAuto = {
        drive(0.6, 1000)
    };












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

