package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class DeviceContainer {
    public WPI_TalonSRX intakeBeltFront = new WPI_TalonSRX(Constants.Motors.INTAKE_BELT_FRONT);
    public WPI_TalonFX intakeBeltRear = new WPI_TalonFX(Constants.Motors.INTAKE_BELT_REAR);
    public WPI_TalonSRX intakeRoller = new WPI_TalonSRX(Constants.Motors.INTAKE_ROLLER);
}
