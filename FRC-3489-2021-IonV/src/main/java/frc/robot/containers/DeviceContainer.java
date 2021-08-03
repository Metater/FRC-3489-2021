package frc.robot.containers;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class DeviceContainer {


    // Joysticks
    public Joystick joystickDriveLeft = new Joystick(0);
    public Joystick joystickDriveRight = new Joystick(1);
    public Joystick joystickManipulator = new Joystick(2);

    // Ball System
    public DigitalInput ballInputSensor = new DigitalInput(0);
    public WPI_TalonSRX intakeBeltFront = new WPI_TalonSRX(5);
    public WPI_TalonFX intakeBeltRear = new WPI_TalonFX(10);
    public WPI_TalonSRX intakeRoller = new WPI_TalonSRX(8);
    public Solenoid intakeSolenoid = new Solenoid(11, 2);

    // Drive
    public WPI_TalonSRX driveLeftFront = new WPI_TalonSRX(1);
    public WPI_TalonSRX driveRightFront = new WPI_TalonSRX(2);
    public WPI_TalonSRX driveLeftFollower = new WPI_TalonSRX(3);
    public WPI_TalonSRX driveRightFollower = new WPI_TalonSRX(4);

    // Climb System
    public WPI_TalonFX winch = new WPI_TalonFX(9);
    public Solenoid winchSolenoidLeft = new Solenoid(11, 0);
    public Solenoid winchSolenoidRight = new Solenoid(11, 1);

    // Control Panel
    public Solenoid controlPanelPnuematics = new Solenoid(11, 3);
    public WPI_TalonSRX controlPanelSpinner = new WPI_TalonSRX(6);
}
