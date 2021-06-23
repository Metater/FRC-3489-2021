package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.shared.handlers.BaseHandler;
import frc.robot.shared.interfaces.ITeleopListener;

public class IntakeHandler extends BaseHandler implements ITeleopListener {
    

    public IntakeHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addTeleopListener(this);
    }

    public void teleopInit()
    {
        
    }

    public void teleopPeriodic()
    {
        robotHandler.deviceContainer.intake.set(robotHandler.deviceContainer.joystickManipulator.getY());
        System.out.println(robotHandler.deviceContainer.joystickManipulator.getY());
    }



}
