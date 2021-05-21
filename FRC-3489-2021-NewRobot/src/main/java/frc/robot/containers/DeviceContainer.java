package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

public class DeviceContainer {

    // Input
    public Joystick joystickDriveLeft = new Joystick(0);
    public Joystick joystickDriveRight = new Joystick(1);
    public Joystick joystickManipulator = new Joystick(2);

    // Motors
    public WPI_TalonSRX driveFrontLeft = new WPI_TalonSRX(7);
    public WPI_TalonSRX driveFrontRight = new WPI_TalonSRX(4);
    public WPI_TalonSRX driveBackLeft = new WPI_TalonSRX(5);
    public WPI_TalonSRX driveBackRight = new WPI_TalonSRX(2);

    public WPI_TalonSRX turretCellevator = new WPI_TalonSRX(1);
    public WPI_TalonSRX turretRotate = new WPI_TalonSRX(6);
    public WPI_TalonFX turretShooterLeft = new WPI_TalonFX(11);
    public WPI_TalonFX turretShooterRight = new WPI_TalonFX(10);
}
