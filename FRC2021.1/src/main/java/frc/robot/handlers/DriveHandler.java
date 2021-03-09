package frc.robot.handlers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.controller.PIDController;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

//import frc.robot.*;
import frc.robot.Constants;

public class DriveHandler {

    private RobotHandler robotHandler;

    public WPI_TalonSRX _leftFront = new WPI_TalonSRX(1);
    public WPI_TalonSRX _rghtFront = new WPI_TalonSRX(2);
    public WPI_TalonSRX _leftFollower = new WPI_TalonSRX(3);
    public WPI_TalonSRX _rghtFollower = new WPI_TalonSRX(4);

    public DifferentialDrive differentialDrive = new DifferentialDrive(_leftFront, _rghtFront);

    public PIDController pidController;


    public DriveHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;

        init();
    }

    private void /*tommy*/init()
    {
        _leftFollower.follow(_leftFront);
        _rghtFollower.follow(_rghtFront);
    }

    /**
    * Called every teleopPeriodic cycle
    */
    public void teleopPeriodic() {

        trySwitchFront();
        tryScissorLift();
        drive();

        robotHandler.shuffleboardHandler.PrintDoubleToWidget("Velocity Left", -1 * _leftFront.getSelectedSensorVelocity());
        robotHandler.shuffleboardHandler.PrintDoubleToWidget("Velocity Right", _rghtFront.getSelectedSensorVelocity());
        robotHandler.shuffleboardHandler.PrintBooleanToWidget("Intake Forward", robotHandler.stateHandler.isIntakeSideFront);
    }

    /**
    * Sets the drive train sides to their corresponding speed, the corresponding speed can be switched depending on which way is the "front"
    */
    private void drive()
    {
        double backwardLeftDriveSpeed = robotHandler.inputHandler.getLeftDriveSpeed();
        double backwardRightDriveSpeed = robotHandler.inputHandler.getRightDriveSpeed();
        
        // Control is forward
        if (robotHandler.stateHandler.isIntakeSideFront)
        {
            double leftTrain = backwardLeftDriveSpeed * -1;
            double rightTrain = backwardRightDriveSpeed * -1;
            differentialDrive.tankDrive(leftTrain, rightTrain);
        }
        // Control is backwards
        else
        {
            double leftTrain = backwardRightDriveSpeed;
            double rightTrain = backwardLeftDriveSpeed;
            differentialDrive.tankDrive(leftTrain, rightTrain);
        }
    }

    /**
    * Switches the front of the robot if the switch front button was pressed
    */
    private void trySwitchFront()
    {
        if (robotHandler.inputHandler.shouldSwitchFront())
        {
            robotHandler.stateHandler.switchFront();

            robotHandler.cameraHandler.UpdateCameraDirection();
        }
    }
    /**
    * Activates the scissor lift pnuematics if both of the scissor lift buttons are held
    */
    private void tryScissorLift()
    {
        if (robotHandler.inputHandler.shouldScissorLift())
        {
            // --------------------------
            // PUT SCISSOR LIFT CODE HERE
            // --------------------------

        }
    }
}
