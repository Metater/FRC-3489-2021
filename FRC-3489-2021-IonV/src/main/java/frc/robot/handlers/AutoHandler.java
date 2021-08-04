package frc.robot.handlers;


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
        autoChooser.addOption("1: Drive Dump", "driveDump");
        shuffleboardHandler.autoTab.add(autoChooser).withSize(2, 1);
    }

    public void robotPeriodic()
    {

    }

    public void autonomousInit()
    {
        String selectedAutoString = autoChooser.getSelected();
        if (selectedAutoString.equals("none")) return;
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

    public void delay(AutoInstruction instruction)
    {
        if (!instruction.runtimeData.init)
        {
            instruction.runtimeData.init = true;
            instruction.runtimeData.time = Timer.getFPGATimestamp();
        }
        if (Timer.getFPGATimestamp() >= instruction.runtimeData.time + instruction.arguments.get(0)._double)
        {
            interpreter.finished();
        }
    }

    public void moveForSeconds(AutoInstruction instruction)
    {
        if (!instruction.runtimeData.init)
        {
            instruction.runtimeData.init = true;
            instruction.runtimeData.time = Timer.getFPGATimestamp();
        }
        if (Timer.getFPGATimestamp() >= instruction.runtimeData.time + instruction.arguments.get(2)._double)
        {
            driveHandler.differentialDrive.stopMotor();
            interpreter.finished();
        }
        else
        {
            driveHandler.differentialDrive.tankDrive(instruction.arguments.get(0)._double, instruction.arguments.get(1)._double);
        }
    }

    public void move(AutoInstruction instruction)
    {
        if (!instruction.runtimeData.init)
        {
            instruction.runtimeData.init = true;
            instruction.runtimeData.startClicks = deviceContainer.driveLeftFront.getSelectedSensorPosition();
            String direction = instruction.arguments.get(0)._constant;
            double speed = instruction.arguments.get(1)._double;
            if (direction.equals("FORWARD"))
            {
                driveHandler.differentialDrive.tankDrive(-speed, -speed);
            }
            else if (direction.equals("REVERSE"))
            {
                driveHandler.differentialDrive.tankDrive(speed, speed);
            }
        }
        double clicks = instruction.arguments.get(2)._double;
        if (Math.abs(deviceContainer.driveLeftFront.getSelectedSensorPosition() - instruction.runtimeData.startClicks) >= clicks)
        {
            driveHandler.differentialDrive.stopMotor();
            interpreter.finished();
        }
    }

    public void dump(AutoInstruction instruction)
    {
        double speed = instruction.arguments.get(0)._double;
        if (!instruction.runtimeData.init)
        {
            instruction.runtimeData.init = true;
            instruction.runtimeData.time = Timer.getFPGATimestamp();

            deviceContainer.intakeBeltFront.set(-speed);
            deviceContainer.intakeBeltRear.set(-speed);
        }
        double dumpTime = instruction.arguments.get(1)._double;
        if (Timer.getFPGATimestamp() - instruction.runtimeData.time >= dumpTime)
        {
            deviceContainer.intakeBeltFront.stopMotor();
            deviceContainer.intakeBeltRear.stopMotor();
            interpreter.finished();
        }
    }
}
