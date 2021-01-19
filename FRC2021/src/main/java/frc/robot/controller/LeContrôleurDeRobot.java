package frc.robot.controller;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.controller.*;

public class LeContrôleurDeRobot {

    public ÉtatDuContrôleurDeRobot robotControllerState;
    public EntréeDuRobot robotInput;

    public WPI_TalonSRX _leftFront = new WPI_TalonSRX(1);
    public WPI_TalonSRX _rghtFront = new WPI_TalonSRX(2);
    public WPI_TalonSRX _leftFollower = new WPI_TalonSRX(3);
    public WPI_TalonSRX _rghtFollower = new WPI_TalonSRX(4);

    public DifferentialDrive differentialDrive = new DifferentialDrive(_leftFront, _rghtFront);

    public LeContrôleurDeRobot() {
        _leftFollower.follow(_leftFront);
        _rghtFollower.follow(_rghtFront);
    }

    public void init()
    {
        
    }

    public void cycle() {
        checkActions();
        drive();
    }

    private void drive()
    {
        double forwardLeftDriveSpeed = robotInput.getLeftDriveSpeed();
        double forwardRightDriveSpeed = robotInput.getRightDriveSpeed();

        if (robotControllerState.inputForward)
            differentialDrive.tankDrive(forwardLeftDriveSpeed, forwardRightDriveSpeed);
        else
            differentialDrive.tankDrive(forwardRightDriveSpeed * -1, forwardLeftDriveSpeed * -1);
    }

    private void checkActions()
    {
        checkSwitchFront();
        checkScissorLift();
    }

    private void checkSwitchFront()
    {
        if (robotInput.getLeftDriveButtonPressed(Constants.Buttons.SWITCH_FRONT))
            robotControllerState.switchFront();
    }

    private void checkScissorLift()
    {
        if (robotInput.doScissorLift()) {
            // do scissor lift lift stuff
        }
    }
}
