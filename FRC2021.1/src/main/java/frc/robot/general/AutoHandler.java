package frc.robot.general;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.auto.*;
import frc.robot.auto.AutoInstruction.InstructionType;
import frc.robot.auto.AutosHandler.AutoType;

public class AutoHandler {

    private RobotHandler robotHandler;

    private AutosHandler autosHandler;

    public int currentStep = 0;

    private AutoInstruction[] selectedAuto;
    
    public AutoHandler(RobotHandler robotHandler, AutoType autoType)
    {
        this.robotHandler = robotHandler;
        autosHandler = new AutosHandler(this);
        selectedAuto = autosHandler.autos.get(autoType);
    }

    public void autonomousInit()
    {
        robotHandler.driveHandler.differentialDrive.setSafetyEnabled(false);
    }

    public void autonomousPeriodic()
    {
        if (selectedAuto.length-1 > currentStep)
        {
            if (selectedAuto[currentStep].isFinished) currentStep++;
            selectedAuto[currentStep].cycle();
        }
    }


    public DriveHandler getDriveHandler() {
        return robotHandler.driveHandler;
    }
    public BallSystemHandler getBallSystemHandler() {
        return robotHandler.ballSystemHandler;
    }
}
