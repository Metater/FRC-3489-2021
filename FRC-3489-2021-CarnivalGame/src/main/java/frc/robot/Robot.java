package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  //Swiper swiper = new Swiper();

  NoSwiper swiperNoSwiping = new NoSwiper();

  @Override
  public void robotInit()
  {
    //swiper.robotInit();
    swiperNoSwiping.robotInit();
  }
  @Override
  public void robotPeriodic()
  {

  }

  @Override
  public void disabledInit()
  {

  }
  @Override
  public void disabledPeriodic()
  {

  }

  @Override
  public void autonomousInit()
  {

  }
  @Override
  public void autonomousPeriodic()
  {

  }

  @Override
  public void teleopInit()
  {
    //swiper.teleopInit();
    swiperNoSwiping.teleopInit();
  }
  @Override
  public void teleopPeriodic()
  {
    //swiper.teleopPeriodic();
    swiperNoSwiping.teleopPeriodic();

  }

  @Override
  public void testInit()
  {

  }
  @Override
  public void testPeriodic()
  {

  }
}
