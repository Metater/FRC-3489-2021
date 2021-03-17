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

        robotHandler.shuffleboardHandler.printDoubleToWidget("Velocity Left", -1 * _leftFront.getSelectedSensorVelocity());
        robotHandler.shuffleboardHandler.printDoubleToWidget("Velocity Right", _rghtFront.getSelectedSensorVelocity());
        robotHandler.shuffleboardHandler.printBooleanToWidget("Intake Forward", robotHandler.stateHandler.isIntakeSideFront);
    }

    /**
    * Sets the drive train sides to their corresponding speed, the corresponding speed can be switched depending on which way is the "front"
    */
    private void drive()
    {
        double backwardLeftDriveTrainSpeed = robotHandler.inputHandler.getLeftDriveSpeed();
        double backwardRightDriveTrainSpeed = robotHandler.inputHandler.getRightDriveSpeed();
        
        double leftTrain;
        double rightTrain;

        // Control is forward
        if (robotHandler.stateHandler.isIntakeSideFront)
        {
            leftTrain = backwardLeftDriveTrainSpeed * -1;
            rightTrain = backwardRightDriveTrainSpeed * -1;
        }
        // Control is backwards
        else
        {
            leftTrain = backwardRightDriveTrainSpeed;
            rightTrain = backwardLeftDriveTrainSpeed;
        }

        robotHandler.recordingAndPlaybackHandler.recordValues(leftTrain, rightTrain);

        differentialDrive.tankDrive(leftTrain, rightTrain);
    }

    /**
    * Switches the front of the robot if the switch front button was pressed
    */
    private void trySwitchFront()
    {
        if (robotHandler.inputHandler.shouldSwitchFront())
        {
            robotHandler.stateHandler.switchFront();

            robotHandler.cameraHandler.updateCameraDirection();
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
