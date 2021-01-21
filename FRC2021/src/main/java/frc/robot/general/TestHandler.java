package frc.robot.general;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TestHandler {

    private RobotHandler robotHandler;

    WPI_TalonSRX testMotor8 = new WPI_TalonSRX(8);
    final int testMotor8Button = 8;

    public TestHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void teleopPeriodic()
    {
        if (robotHandler.inputHandler.joystickDriveLeft.getRawButtonPressed(testMotor8Button))
            testMotor8.set(0.3);
        if (robotHandler.inputHandler.joystickDriveLeft.getRawButtonReleased(testMotor8Button))
            testMotor8.stopMotor();
    }
}
