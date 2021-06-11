package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

public class TurretTesting {

    public Joystick joystickDriveLeft = new Joystick(0);
    public Joystick joystickDriveRight = new Joystick(1);
    public Joystick joystickManipulator = new Joystick(2);
  
    public WPI_TalonSRX driveFrontLeft = new WPI_TalonSRX(7);
    public WPI_TalonSRX driveFrontRight = new WPI_TalonSRX(4);
    public WPI_TalonSRX driveBackLeft = new WPI_TalonSRX(5);
    public WPI_TalonSRX driveBackRight = new WPI_TalonSRX(2);
  
    public WPI_TalonSRX turretCellevator = new WPI_TalonSRX(8);
    //public WPI_TalonSRX turretRotate = new WPI_TalonSRX(21);
    public WPI_TalonFX turretShooterLeft = new WPI_TalonFX(6);
    public WPI_TalonFX turretShooterRight = new WPI_TalonFX(15);

    private DataLogger dataLogger = new DataLogger("turretTesting.txt");


    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        if (joystickManipulator.getRawButton(1))
        {
            turretShooterLeft.set(-1);
            turretShooterRight.set(1);
        }
        else
        {
            turretShooterLeft.stopMotor();
            turretShooterRight.stopMotor();
        }
        if (joystickManipulator.getRawButton(12))
        {
            turretCellevator.set(1);
        }
        else
        {
            turretCellevator.stopMotor();
        }
    }
}
