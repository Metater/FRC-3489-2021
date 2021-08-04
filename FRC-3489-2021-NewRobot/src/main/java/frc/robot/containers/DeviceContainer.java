package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

public class DeviceContainer {

    // Joysticks
    public Joystick joystickDriveLeft = new Joystick(0);
    public Joystick joystickDriveRight = new Joystick(1);
    public Joystick joystickManipulator = new Joystick(2);

    // Drive
    public WPI_TalonSRX driveFrontLeft = new WPI_TalonSRX(1);
    public WPI_TalonSRX driveFrontRight = new WPI_TalonSRX(2);
    public WPI_TalonSRX driveBackLeft = new WPI_TalonSRX(3);
    public WPI_TalonSRX driveBackRight = new WPI_TalonSRX(4);


    // Ball System
    public Solenoid intakeArm = new Solenoid(19,0);
    public DigitalInput turretStopLeft = new DigitalInput(0);
    public DigitalInput turretStopRight = new DigitalInput(1);
    public WPI_TalonSRX turretRotate = new WPI_TalonSRX(5);
    public WPI_TalonSRX cellevator = new WPI_TalonSRX(6);
    public WPI_TalonSRX intake = new WPI_TalonSRX(7);
    public WPI_TalonFX shooterLeft = new WPI_TalonFX(8);
    public WPI_TalonFX shooterRight = new WPI_TalonFX(9);
    public WPI_TalonSRX hopperMover = new WPI_TalonSRX(10);

    // Control Panel
    //public Solenoid controlPanelPnuematics = new Solenoid(11, 3);
    //public WPI_TalonSRX controlPanelSpinner = new WPI_TalonSRX(6);
    
}
