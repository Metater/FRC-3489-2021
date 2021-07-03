package frc.robot.handlers;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.shared.interfaces.*;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.utils.FileUtils;


public class BallSystemHandler extends BaseHandler implements IRobotListener, IDisabledListener, ITeleopListener, IButtonListener {

    public enum TurretState
    {
        Deactivated,
        Activated,
        LoggingCurrent,
        TurretAccelerating
    }

    public enum BallCondition
    {
        Good,
        Medium,
        Bad
    }

    private TurretState turretState;

    private double logCurrentStartTime = -1;
    private List<Double> cellavatorLoggedCurrents = new ArrayList<Double>();
    private BallCondition lastDetectedBallCondition = BallCondition.Medium;

    private double accelerateStartTime = -1;

    private String turretData = "";

    public BallSystemHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
    }

    public void robotInit()
    {
        
    }

    public void robotPeriodic()
    {

    }

    public void disabledInit()
    {
        if (!FileUtils.fileExists("turretData.txt"))
            FileUtils.createLocalFile("turretData.txt");
        FileUtils.writeLocalFile("turretData.txt", turretData);
    }

    public void disabledPeriodic()
    {
        
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        double shooterStatorCurrent = (deviceContainer.shooterLeft.getStatorCurrent() + deviceContainer.shooterRight.getStatorCurrent())/2;
        double rotateStatorCurrent = deviceContainer.turretRotate.getStatorCurrent();
        System.out.println("SC:" + shooterStatorCurrent + " RC:" + rotateStatorCurrent);

        tryShoot();
    }

    private void tryShoot()
    {
        switch (turretState)
        {
            case Deactivated:
                if (deviceContainer.joystickManipulator.getRawButton(1))
                {
                    turretState = TurretState.Activated;
                    deviceContainer.cellevator.set(Constants.Turret.CellavatorWaitSpeed);
                }
                else
                {
                    deviceContainer.cellevator.stopMotor();
                    setTurret(0);
                }
                break;
            case Activated:
                double cellavatorCurrent = deviceContainer.cellevator.getStatorCurrent();
                if (cellavatorCurrent < Constants.Turret.BallEntryCurrent)
                    deviceContainer.cellevator.set(Constants.Turret.CellavatorWaitSpeed);
                else
                {
                    turretState = TurretState.LoggingCurrent;
                    logCurrentStartTime = Timer.getFPGATimestamp();
                    cellavatorLoggedCurrents.clear();
                    cellavatorLoggedCurrents.add(cellavatorCurrent);
                }
				break;
            case LoggingCurrent:
                if (Timer.getFPGATimestamp() - logCurrentStartTime < Constants.Turret.CellavatorLoggingPeriod)
                {
                    deviceContainer.cellevator.set(Constants.Turret.CellavatorLogSpeed);
                    double loggedCurrent = deviceContainer.cellevator.getStatorCurrent();
                    cellavatorLoggedCurrents.add(loggedCurrent);
                }
                else
                {
                    double sum = 0;
                    for (double current : cellavatorLoggedCurrents)
                    {
                        sum += current;
                    }
                    double avgCurrent = sum / ((double)cellavatorLoggedCurrents.size());
                    if (avgCurrent < Constants.Turret.GoodBallMinCurrentAvg)
                        lastDetectedBallCondition = BallCondition.Good;
                    else if (avgCurrent < Constants.Turret.MediumBallMinCurrentAvg)
                        lastDetectedBallCondition = BallCondition.Medium;
                    else
                        lastDetectedBallCondition = BallCondition.Bad;
                    turretState = TurretState.TurretAccelerating;
                    deviceContainer.cellevator.stopMotor();
                    accelerateStartTime = Timer.getFPGATimestamp();
                }
				break;
            case TurretAccelerating:
                if (Timer.getFPGATimestamp() - accelerateStartTime < Constants.Turret.TurretAcceleratePeriod)
                {
                    setTurret(getTurretSpeed());
                }
                else
                {
                    if (deviceContainer.joystickManipulator.getRawButton(1))
                    {
                        turretState = TurretState.Activated;
                        deviceContainer.cellevator.set(Constants.Turret.CellavatorWaitSpeed);
                    }
                    else
                    {
                        turretState = TurretState.Deactivated;
                    }
                }
				break;
			default:
				break;
        }
    }

    /*
    private void tryAim()
    {

    }
    */

    public void update(BaseButtonUpdate update)
    {
        
    }

    private double getTurretSpeed()
    {
        switch (lastDetectedBallCondition)
        {
            // Take in distance later
            case Bad:
                return 0.95;
            case Good:
                return 0.9;
            case Medium:
                return 0.85;
            default:
                break;
        }
        return 1;
    }

    private void setTurret(double speed)
    {
        deviceContainer.shooterLeft.set(-speed);
        deviceContainer.shooterRight.set(speed);
    }
}
