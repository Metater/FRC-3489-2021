package frc.robot.handlers;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.shared.handlers.BaseHandler;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.RawButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;

public class ShooterHandler extends BaseHandler implements IButtonListener, ITeleopListener {

    public double shooterSpeed = 0;

    private NetworkTableEntry toggleTestEntry;

    public ShooterHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addTeleopListener(this);
        RawButtonUpdate resetShooter = new RawButtonUpdate(this, PeriodicType.Teleop, "ResetShooter", new ButtonLocation(12, JoystickType.Manipulator));
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(resetShooter);
        RawButtonUpdate cellavator = new RawButtonUpdate(this, PeriodicType.Teleop, "Cellevator", new ButtonLocation(1, JoystickType.Manipulator));
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(cellavator);

        toggleTestEntry = robotHandler.shuffleboardHandler.tab.add("Toggle Test", true).getEntry();
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        shooterSpeed += robotHandler.joystickHandler.getShooterAdjust();
        setShooter(shooterSpeed);
        robotHandler.deviceContainer.turretRotate.set(robotHandler.joystickHandler.getTurretRotateSpeed());
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "ResetShooter" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.On)
        {
            shooterSpeed = 0;
        }
        if (buttonUpdate.buttonUpdateName == "Cellevator")
        {
            if (buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.On)
            {
                robotHandler.deviceContainer.cellevator.set(1);
                toggleTestEntry.setBoolean(true);
            }
            else
            {
                robotHandler.deviceContainer.cellevator.stopMotor();   
                toggleTestEntry.setBoolean(false);
            }
        }
    }

    private void setShooter(double speed)
    {
        robotHandler.deviceContainer.shooterLeft.set(-speed);
        robotHandler.deviceContainer.shooterRight.set(speed);
    }


}
