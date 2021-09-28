package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class NoSwiper {

    public DigitalInput turretStopLeft = new DigitalInput(0);
    public DigitalInput turretStopRight = new DigitalInput(1);
    public WPI_TalonSRX turretRotate = new WPI_TalonSRX(5);

    public WPI_TalonFX shooterLeft = new WPI_TalonFX(8);
    public WPI_TalonFX shooterRight = new WPI_TalonFX(9);

    public WPI_TalonSRX cellevator = new WPI_TalonSRX(6);

    public Joystick manipulator = new Joystick(2);
    

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

    int print = 0;
    public void teleopPeriodic()
    {
        setCellevator(manipulator.getRawButton(1));
        double speed = manipulator.getZ();
        if (Math.abs(speed) < 0.15) speed = 0;
        double rotate = -0.25 * speed;
        turretRotate.set(rotate);
        double shootSpeed = -manipulator.getY();
        setShooter(shootSpeed);
        if (print % 10 == 0)
        {
            System.out.println("Rotate: " + rotate + " Shoot: " + (int)(shootSpeed * 100) + "%");
        }
        print++;
    }

    public static double lerp(double minClamp, double maxClamp, double a, double b, double t)
    {
        double unclamedLerp = a + t * (b - a);
        return Math.max(minClamp, Math.min(maxClamp, unclamedLerp));
    }

    
    public void setCellevator(boolean value)
    {
        if (value)
            cellevator.set(1);
        else
            cellevator.stopMotor();
    }

    public void setShooter(double speed)
    {
        shooterLeft.set(-speed);
        shooterRight.set(speed);
    }
}
