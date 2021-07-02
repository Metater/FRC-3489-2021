package frc.robot.handlers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
import frc.robot.shared.handlers.BaseHandler;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.IRobotListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.ToggleButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;

public class LimelightHandler extends BaseHandler implements IButtonListener, IRobotListener, ITeleopListener {
    
    NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry pipeline = limelight.getEntry("pipeline");
    NetworkTableEntry targetX = limelight.getEntry("tx");
    NetworkTableEntry targetY = limelight.getEntry("ty");
    NetworkTableEntry targetArea = limelight.getEntry("ta");

    private boolean limelightActivated = false;

    private double lastValidDistance = 0;

    public LimelightHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addRobotListener(this);
        robotHandler.functionListenerHandler.addTeleopListener(this);

        ToggleButtonUpdate toggleLimelight = new ToggleButtonUpdate(this, PeriodicType.Robot, "ToggleLimelight", new ButtonLocation(Constants.Buttons.ToggleLimelight, JoystickType.Manipulator), 0.05);
        robotHandler.buttonUpdateListenerHandler.addButtonUpdate(toggleLimelight);

        setLimelight(false);
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "ToggleLimelight" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
        {
            limelightActivated = !limelightActivated;
            setLimelight(limelightActivated);
        }
    }

    public void robotInit()
    {

    }

    public void robotPeriodic()
    {
        robotHandler.shuffleboardHandler.displayDouble("Target X Offset", targetX.getDouble(0));
        robotHandler.shuffleboardHandler.displayDouble("Target Y Offset", targetY.getDouble(0));
        robotHandler.shuffleboardHandler.displayDouble("Target Area", targetArea.getDouble(0));

        double distance = getDistanceEstimate();
        if (distance >= 0)
        {
            if (distance < 120 || distance > 700)
                lastValidDistance = distance;
            robotHandler.shuffleboardHandler.displayDouble("Distance to Target (in)", distance);
        }
        else
            distance = lastValidDistance;
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        if (targetX.getDouble(0) > 1)
        {
            if (targetX.getDouble(0) > 5) robotHandler.deviceContainer.turretRotate.set(-0.16);
            else if (targetX.getDouble(0) > 12) robotHandler.deviceContainer.turretRotate.set(-0.2);
            else robotHandler.deviceContainer.turretRotate.set(-0.14);
        }
        else if (targetX.getDouble(0) < -1)
        {
            if (targetX.getDouble(0) < -5) robotHandler.deviceContainer.turretRotate.set(0.16);
            else if (targetX.getDouble(0) < -12) robotHandler.deviceContainer.turretRotate.set(0.2);
            else robotHandler.deviceContainer.turretRotate.set(0.14);
        }
        else
        {
            robotHandler.deviceContainer.turretRotate.stopMotor();
        }
    }

    public void setLimelight(boolean value)
    {
        limelightActivated = value;
        if (limelightActivated) pipeline.setNumber(0);
        else pipeline.setNumber(5);
        robotHandler.shuffleboardHandler.displayBool("Limelight Activated", limelightActivated);
    }

    private double getDistanceEstimate()
    {
        double angle = 31.5 + targetY.getDouble(0);
        double distance = (90.75 - 20) / Math.tan(angle);
        return distance;
    }
}
