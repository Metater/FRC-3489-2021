package frc.robot.handlers;

import frc.robot.containers.DeviceContainer;
import frc.robot.shared.handlers.ButtonUpdateListenerHandler;
import frc.robot.shared.handlers.FunctionListenerHandler;
import frc.robot.shared.interfaces.IAutoListener;
import frc.robot.shared.interfaces.IDisabledListener;
import frc.robot.shared.interfaces.IRobotListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.interfaces.ITestListener;

public abstract class BaseHandler {

    public RobotHandler robotHandler;

    public DeviceContainer deviceContainer;

    public FunctionListenerHandler functionListenerHandler;
    public ButtonUpdateListenerHandler buttonUpdateListenerHandler;

    // Add new handlers here
    public ShuffleboardHandler shuffleboardHandler;
    public BallSystemHandler ballSystemHandler;
    public JoystickHandler joystickHandler;
    public CameraHandler cameraHandler;
    public DriveHandler driveHandler;
    public ClimbSystemHandler climbSystemHandler;
    public ControlPanelHandler controlPanelHandler;
    public AutoHandler autoHandler;

    protected void addRobotHandlerReference(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.handlers.add(this);
    }

    protected void addReferences(RobotHandler robotHandler)
    {
        addRobotHandlerReference(robotHandler);

        ensureReferences(robotHandler);

        enforceFunctionSubscriptions();
    }

    public void ensureReferences(RobotHandler robotHandler)
    {
        deviceContainer = robotHandler.deviceContainer;

        functionListenerHandler = robotHandler.functionListenerHandler;
        buttonUpdateListenerHandler = robotHandler.buttonUpdateListenerHandler;

        // Add new handlers here
        shuffleboardHandler = robotHandler.shuffleboardHandler;
        ballSystemHandler = robotHandler.ballSystemHandler;
        joystickHandler = robotHandler.joystickHandler;
        cameraHandler = robotHandler.cameraHandler;
        driveHandler = robotHandler.driveHandler;
        climbSystemHandler = robotHandler.climbSystemHandler;
        controlPanelHandler = robotHandler.controlPanelHandler;
        autoHandler = robotHandler.autoHandler;
    }

    private void enforceFunctionSubscriptions()
    {
        if (this instanceof IRobotListener)
            functionListenerHandler.addRobotListener((IRobotListener)this);
        if (this instanceof IDisabledListener)
            functionListenerHandler.addDisabledListener((IDisabledListener)this);
        if (this instanceof IAutoListener)
            functionListenerHandler.addAutoListener((IAutoListener)this);
        if (this instanceof ITeleopListener)
            functionListenerHandler.addTeleopListener((ITeleopListener)this);
        if (this instanceof ITestListener)
            functionListenerHandler.addTestListener((ITestListener)this);
    }
}
