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
        if (shooterSpeed > 0 && shooterSpeed <= 1) shooterSpeed += joystickHandler.getShooterAdjust();
        setShooter(shooterSpeed);
        setTurretRotate();

      /*   double shooterCurrent = (deviceContainer.shooterLeft.getStatorCurrent() + deviceContainer.shooterRight.getStatorCurrent()) / 2d;
        shuffleboardHandler.displayDouble("Shooter Current", shooterCurrent);
        System.out.println("Shooter Current: " + shooterCurrent);
        double shooterTemp = (deviceContainer.shooterLeft.getTemperature() + deviceContainer.shooterRight.getTemperature()) / 2d;
        shuffleboardHandler.displayDouble("Shooter Temp", shooterTemp);
        double shooterVelocity = (deviceContainer.shooterLeft.getSelectedSensorVelocity() + deviceContainer.shooterRight.getSelectedSensorVelocity()) / 2d;
        shuffleboardHandler.displayDouble("Shooter Velocity", shooterVelocity);
        System.out.println("Shooter Velocity: " + shooterVelocity);
        double cellevatorCurrent = deviceContainer.cellevator.getStatorCurrent();
        shuffleboardHandler.displayDouble("Cellevator Current", cellevatorCurrent);
        System.out.println("Cellevator Current: " + cellevatorCurrent);
        double cellevatorVelocity = deviceContainer.cellevator.getSelectedSensorVelocity();
        shuffleboardHandler.displayDouble("Cellevator Velocity", cellevatorVelocity);
        System.out.println("Cellevator Velocity: " + cellevatorVelocity); */
    }

    private void setTurretRotate()
    {
        if (limelightHandler.autoAimActivated)
        {

        }
        double turretRotateSpeed = joystickHandler.getTurretRotateSpeed();
        if ((turretRotateSpeed > 0 && deviceContainer.turretStopLeft.get()) || (turretRotateSpeed < 0 && deviceContainer.turretStopRight.get()))
        {
            setTurretRotate(turretRotateSpeed);
        }
        else
        {
            setTurretRotate(0);
        }
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "ResetShooter" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.On)
        {
            shooterSpeed = 0;
            System.out.println("Shooter speed 0");
        }
        if (buttonUpdate.buttonUpdateName == "Shoot")
        {
            if (buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
            {
                shooting = !shooting;
                if (shooting)
                {
                    deviceContainer.cellevator.set(1);
                    deviceContainer.hopperMover.set(.5);
                    shuffleboardHandler.displayBool("Is Shooting", true);
                    System.out.println("Begin Shooting Sequence");
                    if (shooterSpeed < 0.31) shooterSpeed = .9;

                    //For Debugging
                    System.out.println("Autothrottle would output: " + autoThrottle());
                    System.out.println("hasNotShot would output: " + hasNotShot());

                  /*  if (hasNotShot()) {
                        System.out.println("Autothrottle engaged");
                        shooterSpeed = autoThrottle();
                    } */
                }
                else
                {
                    deviceContainer.cellevator.stopMotor();
                    deviceContainer.hopperMover.stopMotor();
                    shooterSpeed = Constants.Turret.ShooterIdleSpeed;
                    shuffleboardHandler.displayBool("Is Shooting", false);
                    System.out.println("Finished Shooting Sequence");
                }
            }
        }
    }

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

    public void setShooter(double speed)
    {
        shuffleboardHandler.displayDouble("Shooter Speed", speed);
        deviceContainer.shooterLeft.set(-speed);
        deviceContainer.shooterRight.set(speed);
    }

    public void setTurretRotate(double speed)
    {
        shuffleboardHandler.displayDouble("Turret Rotate Speed", speed);
        deviceContainer.turretRotate.set(speed);
    }


}
