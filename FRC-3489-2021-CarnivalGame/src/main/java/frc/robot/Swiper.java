package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class Swiper {

    public DigitalInput turretStopLeft = new DigitalInput(0);
    public DigitalInput turretStopRight = new DigitalInput(1);
    public WPI_TalonSRX turretRotate = new WPI_TalonSRX(5);

    NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry pipeline = limelight.getEntry("pipeline");

    public WPI_TalonFX shooterLeft = new WPI_TalonFX(8);
    public WPI_TalonFX shooterRight = new WPI_TalonFX(9);

    public WPI_TalonSRX cellevator = new WPI_TalonSRX(6);

    public Joystick manipulator = new Joystick(2);
  
    private boolean swipeRight = true;
    private double lastSwipeTime = 0;
    private double timeSinceLastSwipe = 0;

    private boolean shooting = false;
    private double shootingSequenceStartTime = 1000;

    private int difficulty = 0;

    private double stopSpeed = 0.8;

    public void robotInit()
    {
        turretRotate.setSafetyEnabled(false);
        shooterLeft.setSafetyEnabled(false);
        shooterRight.setSafetyEnabled(false);
        cellevator.setSafetyEnabled(false);

    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        if (manipulator.getRawButtonPressed(8))
        {
            difficulty = 0;
            System.out.println("Difficulty: Easy");
        }
        else if (manipulator.getRawButtonPressed(10))
        {
            difficulty = 1;
            System.out.println("Difficulty: Medium");
        }
        else if (manipulator.getRawButtonPressed(12))
        {
            difficulty = 2;
            System.out.println("Difficulty: Hard");
        }

        if (manipulator.getRawButton(1) || (Timer.getFPGATimestamp() - shootingSequenceStartTime <= 5 && shooting))
        {
            if (shooting == false)
            {
                shooting = true;
                shootingSequenceStartTime = Timer.getFPGATimestamp();
                pipeline.setNumber(1);
                stopSpeed = (-0.2 * manipulator.getY()) + 0.8;
                System.out.println("Shooting Speed: " + stopSpeed * 100 + "%");
                System.out.println("Shooting!!!");
            }

            double timeInShootingSequence = Timer.getFPGATimestamp() - shootingSequenceStartTime;
            double speed = timeInShootingSequence/4;
            if (speed <= stopSpeed) setShooter(speed);
            if (timeInShootingSequence >= 4) setBallSystem(true);

            turretRotate.set(0);
            return;
        }
        else
        {
            shooting = false;
            setBallSystem(false);
            setShooter(0);
            pipeline.setNumber(0);
        }

        System.out.println("Shooting Speed: " + ((-0.2 * manipulator.getY()) + 0.8) * 100 + "%");

        double vel = getVelocity();
        if (swipeRight)
        {
            turretRotate.set(vel);
            if (!turretStopLeft.get() && timeSinceLastSwipe() > 0.5)
            {
                timeSinceLastSwipe();
                swipe();
            }
        }
        else
        {
            turretRotate.set(-vel);
            if (!turretStopRight.get() && timeSinceLastSwipe() > 0.5)
            {
                timeSinceLastSwipe();
                swipe();
            }
        }

    }

    private double timeSinceLastSwipe()
    {
        return Timer.getFPGATimestamp() - lastSwipeTime;
    }
    private void swipe()
    {
        lastSwipeTime = Timer.getFPGATimestamp();
        swipeRight = !swipeRight;
    }

    private double getVelocity()
    {
        double x = timeSinceLastSwipe;
        double y = 0;
        switch (difficulty)
        {
            case 0:
                //y = (1.9 * Math.pow(x - 0.2, 2)) - (1.2 * Math.pow(x, 3)) + 0.0139;
                y = (-3 * Math.pow(x, 2)) + 0.09;
                //y = 0.1;
                break;
            case 1:
                //y = (2 * Math.pow(x - 0.2, 2)) - (1.2 * Math.pow(x, 3)) + 0.0139;
                y = (-12 * Math.pow(x, 2)) + 0.125;
                //y = 0.15;
                break;
            default:
                //y = (2.5 * Math.pow(x - 0.2, 2)) - (1.2 * Math.pow(x, 3)) + 0.0139;\
                y = (-16 * Math.pow(x, 2)) + 0.15;
                //y = 0.2;
                break;
        }
        if (y < 0) y = 0;
        return y + 0.15;
    }

    public void setShooter(double speed)
    {
        shooterLeft.set(-speed);
        shooterRight.set(speed);
    }

    public void setBallSystem(boolean value)
    {
        if (value)
        {
            cellevator.set(1);
        }
        else
        {
            cellevator.stopMotor();
        }
    }

}
