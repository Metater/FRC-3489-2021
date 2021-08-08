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

    public SetupHandler setupHandler;
    public JoystickHandler joystickHandler;
    public ShuffleboardHandler shuffleboardHandler;
    public CameraHandler cameraHandler;
    public DriveHandler driveHandler;
    //public BallSystemHandler ballSystemHandler;
    public IntakeHandler intakeHandler;
    public ShooterHandler shooterHandler;
    public LimelightHandler limelightHandler;
    public AutoHandler autoHandler;
    public ControlPanelHandler controlPanelHandler;

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

        setupHandler = robotHandler.setupHandler;
        joystickHandler = robotHandler.joystickHandler;
        shuffleboardHandler = robotHandler.shuffleboardHandler;
        cameraHandler = robotHandler.cameraHandler;
        driveHandler = robotHandler.driveHandler;
        //ballSystemHandler = robotHandler.ballSystemHandler;
        intakeHandler = robotHandler.intakeHandler;
        shooterHandler = robotHandler.shooterHandler;
        limelightHandler = robotHandler.limelightHandler;
        autoHandler = robotHandler.autoHandler;
        controlPanelHandler = robotHandler.controlPanelHandler;
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
