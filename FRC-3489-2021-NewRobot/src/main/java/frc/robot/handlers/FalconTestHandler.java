package frc.robot.handlers;

import frc.robot.shared.handlers.BaseHandler;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.RawButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;


public class FalconTestHandler extends BaseHandler implements ITeleopListener, IButtonListener {

    public FalconTestHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addTeleopListener(this);
        RawButtonUpdate buttonUpdate = new RawButtonUpdate(this, PeriodicType.Teleop, "TestFalcon", new ButtonLocation(1, JoystickType.Manipulator));
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(buttonUpdate);
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        System.out.println(robotHandler.deviceContainer.turretShooterLeft.getTemperature() + "::" + robotHandler.deviceContainer.turretShooterRight.getTemperature());
    }

    public void update(BaseButtonUpdate update)
    {
        if (update.buttonUpdateName == "TestFalcon")
        {
            if (update.buttonUpdateEventType == ButtonUpdateEventType.On)
            {
                robotHandler.deviceContainer.turretShooterLeft.set(-1);
                robotHandler.deviceContainer.turretShooterRight.set(1);
            }
            else if (update.buttonUpdateEventType == ButtonUpdateEventType.Off)
            {
                robotHandler.deviceContainer.turretShooterLeft.stopMotor();
                robotHandler.deviceContainer.turretShooterRight.stopMotor();
            }
        }
    }
    
}
