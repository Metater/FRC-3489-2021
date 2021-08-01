package frc.robot.handlers;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;
import frc.robot.auto.AutoInstruction;
import frc.robot.auto.AutoInterpreter;
import frc.robot.shared.interfaces.IAutoListener;
import frc.robot.shared.interfaces.IRobotListener;

public class AutoHandler extends BaseHandler implements IRobotListener, IAutoListener {

    public AutoInterpreter interpreter;

    public SendableChooser<String> autoChooser;

    public AutoHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
    }

    public void robotInit()
    {
        autoChooser = new SendableChooser<String>();
        SendableRegistry.setName(autoChooser, "Auto Chooser");
        autoChooser.setDefaultOption("0: None", "none");
        autoChooser.addOption("1: Drive Off Line", "driveOffLine");
        autoChooser.addOption("2: Shoot Off Line", "shootOffLine");
        autoChooser.addOption("3: No Aim Shoot Off Line", "noAimShootOffLine");
        shuffleboardHandler.autoTab.add(autoChooser).withSize(2, 1);
    }

    public void robotPeriodic()
    {

    }

    public void autonomousInit()
    {
        String selectedAutoString = autoChooser.getSelected();
        interpreter = new AutoInterpreter(this, selectedAutoString);
        System.out.println("Started running auto: " + selectedAutoString);
    }

    public void autonomousPeriodic()
    {
        if (!interpreter.finished) interpreter.cycle();
    }

    public void print(AutoInstruction instruction)
    {
        System.out.println(instruction.arguments.get(0)._string);
        interpreter.finished();
    }

    public void aim(AutoInstruction instruction)
    {
        limelightHandler.setLimelight(true);
        int code = limelightHandler.autoAim();
        if (code == 0) interpreter.finished();
    }

    public void setShooter(AutoInstruction instruction)
    {
        double shooterSpeed = instruction.arguments.get(0)._double;
        shooterHandler.setShooter(shooterSpeed);
        interpreter.finished();
    }

    public void shoot(AutoInstruction instruction)
    {
        String input = instruction.arguments.get(0)._constant;
        if (input.equals("BEGIN"))
        {
            deviceContainer.cellevator.set(1);
            deviceContainer.hopperMover.set(.5);
        }
        else if (input.equals("END"))
        {
            deviceContainer.cellevator.stopMotor();
            deviceContainer.hopperMover.stopMotor();
        }
        interpreter.finished();
    }

    public void delay(AutoInstruction instruction)
    {
        if (!instruction.runtimeData.init)
        {
            instruction.runtimeData.init = true;
            instruction.runtimeData.delayStartTime = Timer.getFPGATimestamp();
        }
        if (Timer.getFPGATimestamp() >= instruction.runtimeData.delayStartTime + instruction.arguments.get(0)._double)
        {
            interpreter.finished();
        }
    }

    public void dropIntake(AutoInstruction instruction)
    {
        intakeHandler.setIntakePnuematics(true);
        interpreter.finished();
    }

    public void moveForSeconds(AutoInstruction instruction)
    {
        if (!instruction.runtimeData.init)
        {
            instruction.runtimeData.init = true;
            instruction.runtimeData.moveStartTime = Timer.getFPGATimestamp();
        }
        if (Timer.getFPGATimestamp() >= instruction.runtimeData.moveStartTime + instruction.arguments.get(2)._double)
        {
            driveHandler.differentialDrive.stopMotor();
            interpreter.finished();
        }
        else
        {
            driveHandler.differentialDrive.tankDrive(-instruction.arguments.get(0)._double, -instruction.arguments.get(1)._double);
        }
    }
}
