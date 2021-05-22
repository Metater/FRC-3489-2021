package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.interfaces.IButtonListener;
import frc.robot.interfaces.ITeleopListener;
import frc.robot.types.ButtonLocation;
import frc.robot.types.ButtonPress;
import frc.robot.types.JoystickType;
import frc.robot.types.PeriodicType;
import frc.robot.types.buttonTriggerCriteria.RawButtonTriggerCriteria;

public class FalconTestHandler extends BaseHandler implements ITeleopListener, IButtonListener {

    private double lastFalconTime = -1;

    public FalconTestHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addTeleopListener(this);
        RawButtonTriggerCriteria testButton0 = new RawButtonTriggerCriteria(this, PeriodicType.Teleop, "TestFalcon", new ButtonLocation(1, JoystickType.Manipulator));
        robotHandler.buttonListenerHandler.addButtonTriggerCriteria(testButton0);
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        if (Timer.getFPGATimestamp() - lastFalconTime <= 0.1)
        {
            //robotHandler.deviceContainer.falcon.set(1);
            robotHandler.deviceContainer.turretShooterLeft.set(-1);
            robotHandler.deviceContainer.turretShooterRight.set(1);
        }
        else
        {
            //robotHandler.deviceContainer.falcon.stopMotor();
            robotHandler.deviceContainer.turretShooterLeft.stopMotor();
            robotHandler.deviceContainer.turretShooterRight.stopMotor();
        }
        System.out.println(robotHandler.deviceContainer.turretShooterLeft.getTemperature() + "::" + robotHandler.deviceContainer.turretShooterRight.getTemperature());
    }

    public void buttonPressed(ButtonPress buttonPress)
    {
        if (buttonPress.buttonTriggerCriteria.buttonTriggerName == "TestFalcon")
        {
            lastFalconTime = Timer.getFPGATimestamp();
        }
    }
    
}
