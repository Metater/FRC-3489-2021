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

public class AutoHandler {

    public enum AutoType
    {
        Joe,
        Slalom,
        TheChugLife,
        Bounce,
        Barrel,
        HyperPathA,
        HyperPathB
    }

    private RobotHandler robotHandler;

    public AutoType autoType;
    public int currentStep = 0;

    private AutoInstruction[] selectedAuto;
    private Map<AutoType, AutoInstruction[]> autos = new HashMap<AutoType, AutoInstruction[]>();

    private void populateAutoList()
    {
        AutosHandler autosHandler = new AutosHandler(this);

        autos.put(AutoType.Slalom, autosHandler.autoSlalom);
        autos.put(AutoType.Joe, autosHandler.autoJoe);
        autos.put(AutoType.TheChugLife, autosHandler.autoTheChugLife);
        autos.put(AutoType.Bounce, autosHandler.autoBounce);
        autos.put(AutoType.Barrel, autosHandler.autoBarrel);
        autos.put(AutoType.HyperPathA, autosHandler.autoHyperPathA);
        autos.put(AutoType.HyperPathB, autosHandler.autoHyperPathB);
    }
    
    public AutoHandler(RobotHandler robotHandler, AutoType autoType)
    {
        this.robotHandler = robotHandler;
        this.autoType = autoType;
        populateAutoList();
        selectedAuto = autos.get(autoType);
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
