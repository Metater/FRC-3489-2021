package frc.robot.general;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

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

    private void init()
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
    }

    /**
    * Sets the drive train sides to their corresponding speed, the corresponding speed can be switched depending on which way is the "front"
    */
    private void drive()
    {
        // Gets the spin speed of the left drive joystick, when in forward mode
        double forwardLeftDriveSpeed = robotHandler.inputHandler.getLeftDriveSpeed();
        // Gets the spin speed of the right drive joystick, when in forward mode
        double forwardRightDriveSpeed = robotHandler.inputHandler.getRightDriveSpeed();
        // Control is forward, normal spin direction, and spin speed on joystick corresponds to each side, and set drive train
        if (robotHandler.stateHandler.isInputSideFront)
            differentialDrive.tankDrive(forwardLeftDriveSpeed, forwardRightDriveSpeed);
        // Control is backwards, invert spin direction and right and left, and set drive train
        else
            differentialDrive.tankDrive(forwardRightDriveSpeed * -1, forwardLeftDriveSpeed * -1); // May need to switch with above depending on which way is default "forward",
            // but then rename forwardLeftDriveSpeed and forwardRightDriveSpeed
    }

    /**
    * Switches the front of the robot if the switch front button was pressed
    */
    private void trySwitchFront()
    {
        if (robotHandler.inputHandler.joystickDriveLeft.getRawButtonPressed(Constants.Buttons.SWITCH_FRONT))
        {
            robotHandler.stateHandler.switchFront();

            // ----------------------------------------------------------------------------------------------------------
            // PUT CAMERA SWITCH CODE HERE, GET CURRENT SWITCH DIRECTION FROM: robotHandler.stateHandler.isInputSideFront
            // ----------------------------------------------------------------------------------------------------------

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
