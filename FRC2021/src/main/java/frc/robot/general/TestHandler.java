package frc.robot.general;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TestHandler {

    private RobotHandler robotHandler;

    WPI_TalonSRX testMotor8 = new WPI_TalonSRX(8);

    WPI_TalonFX

    public TestHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void cycle()
    {
        if (robotHandler.inputHandler.joystickDriveLeft.getRawButton(8))
            testMotor8.set(0.3);
        else
            testMotor8.stopMotor();
    }
}
