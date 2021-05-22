package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.interfaces.IButtonListener;
import frc.robot.interfaces.IDisabledListener;
import frc.robot.interfaces.IRobotListener;
import frc.robot.interfaces.ITeleopListener;
import frc.robot.types.ButtonPress;
import frc.robot.types.JoystickType;
import frc.robot.utils.FileUtils;

public class BallSystemHandler extends BaseHandler implements IRobotListener, IDisabledListener, ITeleopListener, IButtonListener {

    private String turretData = "";

    private double shooterSpeed = 0;

    private double lastShooterDecrementTime = -1;
    private double lastShooterIncrementTime = -1;
    
    public BallSystemHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addRobotListener(this);
        robotHandler.functionListenerHandler.addDisabledListener(this);
        robotHandler.functionListenerHandler.addTeleopListener(this);
        robotHandler.shuffleboardHandler.addEntry(robotHandler.shuffleboardHandler.tab.add("Shooter Speed", 0).getEntry());
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
        double shooterStatorCurrent = (robotHandler.deviceContainer.turretShooterLeft.getStatorCurrent() + robotHandler.deviceContainer.turretShooterRight.getStatorCurrent())/2;
        double rotateStatorCurrent = robotHandler.deviceContainer.turretRotate.getStatorCurrent();
        System.out.println("SC:" + shooterStatorCurrent + " RC:" + rotateStatorCurrent);
    }

    public void buttonPressed(ButtonPress buttonPress)
    {
        if (buttonPress.buttonTriggerCriteria.buttonLocation.compare(1, JoystickType.Manipulator))
        {
            robotHandler.deviceContainer.turretCellevator.set(-1);
        }
        else
        {
            robotHandler.deviceContainer.turretCellevator.set(0);
        }
        if (buttonPress.buttonTriggerCriteria.buttonLocation.compare(11, JoystickType.Manipulator) && Timer.getFPGATimestamp() - lastShooterDecrementTime > 0.25)
        {
            lastShooterDecrementTime = Timer.getFPGATimestamp();
        }
        if (buttonPress.buttonTriggerCriteria.buttonLocation.compare(12, JoystickType.Manipulator) && Timer.getFPGATimestamp() - lastShooterIncrementTime > 0.25)
        {
            lastShooterIncrementTime = Timer.getFPGATimestamp();
        }
    }
    private void setShooterSpeed()
    {
      if (Math.abs(shooterSpeed) > 1)
      {
        shooterSpeed = Math.signum(shooterSpeed);
      }
      robotHandler.deviceContainer.turretShooterLeft.set(shooterSpeed);
      robotHandler.deviceContainer.turretShooterRight.set(shooterSpeed * -1);
      robotHandler.shuffleboardHandler.getEntry("Shooter Speed").setDouble(shooterSpeed * -1);
    }
}
