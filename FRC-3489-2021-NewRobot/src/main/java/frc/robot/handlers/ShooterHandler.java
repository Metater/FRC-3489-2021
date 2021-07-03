package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.RawButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.ToggleButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;

public class ShooterHandler extends BaseHandler implements IButtonListener, ITeleopListener {

    public double shooterSpeed = 0;

    public boolean shooting = false;

    public ShooterHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
        
        RawButtonUpdate resetShooter = new RawButtonUpdate(this, PeriodicType.Teleop, "ResetShooter", new ButtonLocation(Constants.Buttons.ResetShooter, JoystickType.Manipulator));
        buttonUpdateListenerHandler.addButtonUpdate(resetShooter);
        ToggleButtonUpdate shoot = new ToggleButtonUpdate(this, PeriodicType.Teleop, "Shoot", new ButtonLocation(Constants.Buttons.Shoot, JoystickType.Manipulator), 0.05);
        buttonUpdateListenerHandler.addButtonUpdate(shoot);
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        if (shooterSpeed > 0 && shooterSpeed <= 1) shooterSpeed += robotHandler.joystickHandler.getShooterAdjust();
        setShooter(shooterSpeed);
        setTurretRotate();

        double shooterCurrent = (robotHandler.deviceContainer.shooterLeft.getStatorCurrent() + robotHandler.deviceContainer.shooterRight.getStatorCurrent()) / 2d;
        robotHandler.shuffleboardHandler.displayDouble("Shooter Current", shooterCurrent);
        double shooterTemp = (robotHandler.deviceContainer.shooterLeft.getTemperature() + robotHandler.deviceContainer.shooterRight.getTemperature()) / 2d;
        robotHandler.shuffleboardHandler.displayDouble("Shooter Temp", shooterTemp);
        double shooterVelocity = (robotHandler.deviceContainer.shooterLeft.getSelectedSensorVelocity() + robotHandler.deviceContainer.shooterRight.getSelectedSensorVelocity()) / 2d;
        robotHandler.shuffleboardHandler.displayDouble("Shooter Velocity", shooterVelocity);
        double cellevatorCurrent = robotHandler.deviceContainer.cellevator.getStatorCurrent();
        robotHandler.shuffleboardHandler.displayDouble("Cellevator Current", cellevatorCurrent);
        double cellevatorVelocity = robotHandler.deviceContainer.cellevator.getSelectedSensorVelocity();
        robotHandler.shuffleboardHandler.displayDouble("Cellevator Velocity", cellevatorVelocity);
    }

    private void setTurretRotate()
    {
        double turretRotateSpeed = robotHandler.joystickHandler.getTurretRotateSpeed();
        if ((turretRotateSpeed > 0 && robotHandler.deviceContainer.turretStopLeft.get()) || (turretRotateSpeed < 0 && robotHandler.deviceContainer.turretStopRight.get()))
        {
            robotHandler.deviceContainer.turretRotate.set(turretRotateSpeed);
        }
        else
        {
            robotHandler.deviceContainer.turretRotate.stopMotor();
        }
        robotHandler.shuffleboardHandler.displayDouble("Turret Rotate Speed", turretRotateSpeed);
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "ResetShooter" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.On)
        {
            shooterSpeed = 0;
        }
        if (buttonUpdate.buttonUpdateName == "Shoot")
        {
            if (buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
            {
                shooting = !shooting;
                if (shooting)
                {
                    robotHandler.deviceContainer.cellevator.set(1);
                    robotHandler.shuffleboardHandler.displayBool("Is Shooting", true);
                    if (shooterSpeed < 0.3) shooterSpeed = 0.9;
                }
                else
                {
                    robotHandler.deviceContainer.cellevator.stopMotor();
                    robotHandler.shuffleboardHandler.displayBool("Is Shooting", false);
                }
            }
        }
    }

    private void setShooter(double speed)
    {
        robotHandler.shuffleboardHandler.displayDouble("Shooter Speed", speed);
        robotHandler.deviceContainer.shooterLeft.set(-speed);
        robotHandler.deviceContainer.shooterRight.set(speed);
    }


}
