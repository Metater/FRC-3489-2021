package frc.robot.handlers;

import frc.robot.auto.AutoInterpreter;
import frc.robot.shared.interfaces.IAutoListener;

public class AutoHandler extends BaseHandler implements IAutoListener {

    public AutoInterpreter autoInterpreter = new AutoInterpreter();

    public AutoHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
    }

    @Override
    public void autonomousInit()
    {
        //Get the selected auto thing

        //Do the selected auto thing
        
    }

    @Override
    public void autonomousPeriodic()
    {
        // TODO Auto-generated method stub

    }

    private void autoProgram1()
    {
        //Do the auto thing
    }
}
