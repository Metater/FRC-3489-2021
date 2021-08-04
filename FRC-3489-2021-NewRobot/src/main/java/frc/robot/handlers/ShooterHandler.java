package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.RawButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;

public class ShooterHandler extends BaseHandler implements IButtonListener, ITeleopListener {

    public double shooterSpeed = 0.9;

    public boolean shooting = false;

    public ShooterHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
        
        RawButtonUpdate shoot = new RawButtonUpdate(this, PeriodicType.Teleop, "Shoot", new ButtonLocation(Constants.Buttons.Shoot, JoystickType.Manipulator));
        buttonUpdateListenerHandler.addButtonUpdate(shoot);

        RawButtonUpdate closePreset = new RawButtonUpdate(this, PeriodicType.Teleop, "ClosePreset", new ButtonLocation(Constants.Buttons.ShooterPresetClose, JoystickType.Manipulator));
        RawButtonUpdate midPreset = new RawButtonUpdate(this, PeriodicType.Teleop, "MidPreset", new ButtonLocation(Constants.Buttons.ShooterPresetMid, JoystickType.Manipulator));
        RawButtonUpdate farPreset = new RawButtonUpdate(this, PeriodicType.Teleop, "FarPreset", new ButtonLocation(Constants.Buttons.ShooterPresetFar, JoystickType.Manipulator));
        buttonUpdateListenerHandler.addButtonUpdate(closePreset);
        buttonUpdateListenerHandler.addButtonUpdate(midPreset);
        buttonUpdateListenerHandler.addButtonUpdate(farPreset);
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        int pov = deviceContainer.joystickManipulator.getPOV();
        if (pov == 90) shooterSpeed = 1; 
        else if (pov == 270) shooterSpeed = 0.9;
        if (pov == 180 || pov == 225 || pov == 135)
        {
            setShooter(0);
            shooting = false;
        }
        else if (pov == 0 || pov == 45 || pov == 315)
        {
            if (shooterSpeed < 0.5) shooterSpeed = 0.9;
            setShooter(shooterSpeed);
            shooting = true;
        }
        else
        {
            double adjust = joystickHandler.getShooterAdjust();
            if (adjust > 0)
            {
                if (shooterSpeed <= 1) shooterSpeed += adjust;
            }
            else if (adjust < 0)
            {
                if (shooterSpeed > 0) shooterSpeed += adjust;
            }
            if (shooting) setShooter(shooterSpeed);
        }
        shuffleboardHandler.displayDouble("Shooter Speed", shooterSpeed);
        if (!limelightHandler.autoAimActivated) setTurretRotation();
    }

    private void setTurretRotation()
    {
        double turretRotateSpeed = joystickHandler.getTurretRotateSpeed();
        if ((turretRotateSpeed > 0 && deviceContainer.turretStopLeft.get()) || (turretRotateSpeed < 0 && deviceContainer.turretStopRight.get()))
            setTurretRotate(turretRotateSpeed);
        else setTurretRotate(0);
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "Shoot")
        {
            if (buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.On)
            {
                if (shooting)
                {
                    deviceContainer.cellevator.set(1);
                    deviceContainer.hopperMover.set(.5);
                    shuffleboardHandler.displayBool("Is Shooting", true);
                }
                else
                {
                    deviceContainer.cellevator.stopMotor();
                    deviceContainer.hopperMover.stopMotor();
                    shuffleboardHandler.displayBool("Is Shooting", false);
                }
            }
            else if (buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.Off)
            {
                deviceContainer.cellevator.stopMotor();
                deviceContainer.hopperMover.stopMotor();
                shuffleboardHandler.displayBool("Is Shooting", false);
            }
        }
        else if (buttonUpdate.buttonUpdateName == "ClosePreset")
        {
            shooterSpeed = 0.86;
            setShooter(shooterSpeed);
            shooting = true;
        }
        else if (buttonUpdate.buttonUpdateName == "MidPreset")
        {
            shooterSpeed = 0.92;
            setShooter(shooterSpeed);
            shooting = true;
        }
        else if (buttonUpdate.buttonUpdateName == "FarPreset")
        {
            shooterSpeed = 1;
            setShooter(shooterSpeed);
            shooting = true;
        }
    }

    /*
    private double autoThrottle()
    {
        String balltype = "";
        //Get Current Value
        double cellevatorCurrent = deviceContainer.cellevator.getStatorCurrent();

        //Determine ball type
        if (cellevatorCurrent > Constants.Turret.GoodBallMinCurrentAvg ){
            balltype = "good";
        } 
        else if(cellevatorCurrent <= Constants.Turret.MediumBallMinCurrentAvg && cellevatorCurrent >= Constants.Turret.BadBallMinCurrentAvg)
        {
            balltype = "medium";
        }
        else {
            balltype = "bad";
        }
        System.out.println("Ball type detected: " + balltype + "; Detected current: " + cellevatorCurrent);

        //Set shooter speed
        switch (balltype) {
            case "good":
                return Constants.Turret.GoodBallSpeed;
            case "medium":
                return Constants.Turret.MediumBallSpeed;
            case "bad":
                return Constants.Turret.BadBallSpeed;
            default:
                break;
        }
        return 0;
    }
    */

    /*
    private boolean hasNotShot()
    {
        
        //Default ball has not shot yet
        boolean hasNotShot = true;
        double cellevatorCurrent = deviceContainer.cellevator.getStatorCurrent();

        //Check for current spike to indicate ball has been shot
        if (cellevatorCurrent > Constants.Turret.BadBallMinCurrentAvg) 
        {
            hasNotShot = false;
            System.out.println("Shot detected: " + cellevatorCurrent);
        }
        else {
            System.out.println("Nothing shot yet: " + cellevatorCurrent);
        }
        
        return hasNotShot;
    }
    */

    public void setShooter(double speed)
    {
        deviceContainer.shooterLeft.set(-speed);
        deviceContainer.shooterRight.set(speed);
    }

    public void setTurretRotate(double speed)
    {
        deviceContainer.turretRotate.set(speed);
    }


}
