package frc.robot.handlers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TestHandler {

    private RobotHandler robotHandler;

    WPI_TalonSRX testMotor8 = new WPI_TalonSRX(1);
    final int testMotor8Button = 7;

    public TestHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        testMotor8.setSafetyEnabled(false);
    }

    public void teleopPeriodic()
    {
        if(robotHandler.inputHandler.joystickDriveLeft.getRawButton(testMotor8Button))
        {
            testMotor8.set(0.3);
            //System.out.println("Motor Go BRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        }
        //System.out.println("Teleop Cycle");
        if (robotHandler.inputHandler.joystickDriveLeft.getRawButtonReleased(testMotor8Button))
        {
            //testMotor8.stopMotor();
            //System.out.println("Motor stop ):");
        }
    }
}
