package frc.robot.handlers;

import java.util.ArrayList;
import java.util.List;

import frc.robot.interfaces.IButtonListener;
import frc.robot.interfaces.ITeleopListener;

public class ButtonListenerHandler extends BaseHandler implements ITeleopListener {

    private List<IButtonListener> buttonListeners = new ArrayList<IButtonListener>();
    // Later could add button press criteria along with adding a button listener
    
    public ButtonListenerHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addTeleopListener(this);
    }

    public void addButtonListener(IButtonListener buttonListener)
    {
        buttonListeners.add(buttonListener);
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {

    }
}
