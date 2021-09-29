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

    private int print = 0;
    private final static double min = 0.55;
    private final static double max = 0.9;
    

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
        cellevator();
        rotate();
        shoot();
        print++;
    }

    private void cellevator()
    {
        setCellevator(manipulator.getRawButton(1));
    }

    private void rotate()
    {
        double speed = manipulator.getZ();
        if (Math.abs(speed) < 0.15) speed = 0;
        double rotate = -0.25 * speed;
        turretRotate.set(rotate);
    }

    private void shoot()
    {
        double shootSpeed = -manipulator.getY();
        if (shootSpeed < 0.15)
        {
            setCellevator(false);
            shootSpeed = 0;
        }
        else shootSpeed = lerp(min, max, shootSpeed);
        setShooter(shootSpeed);
        if (print % 10 == 0)
        {
            System.out.println("Shoot: " + (int)(shootSpeed * 100) + "%");
        }
    }

    public static double lerp(double a, double b, double t)
    {
        double lerp = a + t * (b - a);
        return lerp;
    }

    public static double clerp(double minClamp, double maxClamp, double a, double b, double t, double tMin, double tMax)
    {
        double lerp = lerp(a, b, t);
        if (t <= tMin) lerp = minClamp;
        if (t >= tMax) lerp = maxClamp;
        return lerp;
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
