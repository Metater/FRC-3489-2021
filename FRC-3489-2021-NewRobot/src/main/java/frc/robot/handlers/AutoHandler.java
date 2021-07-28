package frc.robot.handlers;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.auto.AutoInstruction;
import frc.robot.auto.AutoInterpreter;
import frc.robot.shared.interfaces.IAutoListener;
import frc.robot.shared.interfaces.IRobotListener;

public class AutoHandler extends BaseHandler implements IRobotListener, IAutoListener {

    public AutoInterpreter interpreter;;

    public SendableChooser<String> autoChooser;

    public AutoHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
    }

    public void robotInit()
    {
        autoChooser = new SendableChooser<String>();
        autoChooser.setDefaultOption("1: Test Auto", "test");
        autoChooser.addOption("2: Drive Off Line", "driveOffLine");
        shuffleboardHandler.autoTab.add(autoChooser).withSize(2, 1);
    }

    public void robotPeriodic()
    {

    }

    public void autonomousInit()
    {
        String selectedAutoString = autoChooser.getSelected();
        interpreter = new AutoInterpreter(selectedAutoString);
    }

    public void autonomousPeriodic()
    {
        // TODO Auto-generated method stub

    }

    private void autoProgram1()
    {
        //Do the auto thing
    }
}
