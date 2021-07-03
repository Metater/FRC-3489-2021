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

    protected RobotHandler robotHandler;

    protected DeviceContainer deviceContainer;

    protected FunctionListenerHandler functionListenerHandler;
    protected ButtonUpdateListenerHandler buttonUpdateListenerHandler;

    protected SetupHandler setupHandler;
    protected JoystickHandler joystickHandler;
    protected ShuffleboardHandler shuffleboardHandler;
    protected CameraHandler cameraHandler;
    protected DriveHandler driveHandler;
    //protected BallSystemHandler ballSystemHandler;
    protected IntakeHandler intakeHandler;
    protected ShooterHandler shooterHandler;
    protected LimelightHandler limelightHandler;

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
