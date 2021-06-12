package frc.robot.shared.handlers;

import java.util.ArrayList;
import java.util.List;

import frc.robot.handlers.RobotHandler;
import frc.robot.shared.interfaces.IRobotListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.interfaces.ITestListener;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;

public class ButtonUpdateListenerHandler extends BaseHandler implements IRobotListener, ITeleopListener, ITestListener {

    private List<BaseButtonUpdate> updatedButtons = new ArrayList<BaseButtonUpdate>();
    
    public ButtonUpdateListenerHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addRobotListener(this);
        robotHandler.functionListenerHandler.addTeleopListener(this);
        robotHandler.functionListenerHandler.addTestListener(this);
    }

    public void addButtonUpdate(BaseButtonUpdate buttonUpdate)
    {
        updatedButtons.add(buttonUpdate);
    }

    public void robotInit() {}
    public void robotPeriodic() {}
    public void teleopInit() {}
    public void teleopPeriodic() {}
    public void testInit() {}
    public void testPeriodic() {}
}
