package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj.TimedRobot;
import com.kauailabs.navx.frc.AHRS;

public class Robot extends TimedRobot {

  // examples:
  // https://github.com/maxgdn/NavX-Mxp-java-examples/blob/master/DataMonitor/src/org/usfirst/frc/team2465/robot/Robot.java
  // https://pdocs.kauailabs.com/navx-mxp/examples/
  // https://www.kauailabs.com/public_files/navx-mxp/apidocs/java/com/kauailabs/navx/frc/AHRS.html
  // https://pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/

  private AHRS ahrs;
  private Joystick manipulator = new Joystick(2);

  @Override
  public void robotInit() {
    ahrs = new AHRS(SPI.Port.kMXP);
  }
  @Override
  public void robotPeriodic() {

  }
  @Override
  public void disabledInit() {

  }
  @Override
  public void disabledPeriodic() {

  }
  @Override
  public void autonomousInit() {

  }
  @Override
  public void autonomousPeriodic() {

  }
  @Override
  public void teleopInit() {

  }
  @Override
  public void teleopPeriodic() {
    if (manipulator.getRawButton(1)) {
      ahrs.reset();
    }
    System.out.println(ahrs.getAngle());
  }
  @Override
  public void testInit() {

  }
  @Override
  public void testPeriodic() {

  }
}
