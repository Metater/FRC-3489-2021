package frc.robot.handlers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
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

    public boolean limelightActivated = false;
    public boolean autoAimActivated = false;

    private double lastValidDistance = 0;

    private long run = 0;

    public LimelightHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);

        ToggleButtonUpdate toggleLimelight = new ToggleButtonUpdate(this, PeriodicType.Robot, "ToggleLimelight", new ButtonLocation(Constants.Buttons.ToggleLimelight, JoystickType.Manipulator), 0.05);
        buttonUpdateListenerHandler.addButtonUpdate(toggleLimelight);

        ToggleButtonUpdate toggleAutoAim = new ToggleButtonUpdate(this, PeriodicType.Teleop, "ToggleAutoAim", new ButtonLocation(Constants.Buttons.ToggleAutoAim, JoystickType.Manipulator), 0.05);
        buttonUpdateListenerHandler.addButtonUpdate(toggleAutoAim);

        setLimelight(false);
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "ToggleLimelight" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
        {
            limelightActivated = !limelightActivated;
            setLimelight(limelightActivated);
            if (!limelightActivated)
            {
                autoAimActivated = false;
            }
        }
        if (buttonUpdate.buttonUpdateName == "ToggleAutoAim" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.RisingEdge)
        {
            autoAimActivated = !autoAimActivated;

            if (!limelightActivated && autoAimActivated)
            {
                limelightActivated = true;
                setLimelight(true);
            }
        }
    }

    public void robotInit()
    {

    }

    public void robotPeriodic()
    {
        if (run % 50 == 0)
        {
            shuffleboardHandler.displayDouble("Target X Offset", targetX.getDouble(0));
            shuffleboardHandler.displayDouble("Target Y Offset", targetY.getDouble(0));
            shuffleboardHandler.displayDouble("Target Area", targetArea.getDouble(0));
    
            double distance = getDistanceEstimate();
            shuffleboardHandler.displayDouble("Distance to Target (in)", distance);
        }
        run++;
    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        if (autoAimActivated)
        {
            autoAim();
        }
    }

    public int autoAim()
    {
        if (targetX.getDouble(0) > 1)
        {
            if (targetX.getDouble(0) > 5) shooterHandler.setTurretRotate(-0.16);
            else if (targetX.getDouble(0) > 12) shooterHandler.setTurretRotate(-0.2);
            else shooterHandler.setTurretRotate(-0.14);
            return 1;
        }
        else if (targetX.getDouble(0) < -1)
        {
            if (targetX.getDouble(0) < -5) shooterHandler.setTurretRotate(0.16);
            else if (targetX.getDouble(0) < -12) shooterHandler.setTurretRotate(0.2);
            else shooterHandler.setTurretRotate(0.14);
            return -1;
        }
        else
        {
            shooterHandler.setTurretRotate(0);
            return 0;
        }
    }

    public void setLimelight(boolean value)
    {
        limelightActivated = value;
        if (limelightActivated) pipeline.setNumber(0);
        else pipeline.setNumber(5);
        shuffleboardHandler.displayBool("Limelight Activated", limelightActivated);
    }

    private double getDistanceEstimate()
    {
        double targetYOffset = targetY.getDouble(0);
        if (targetYOffset == 0) return -1;
        // double angle = 31.5d + targetYOffset;
        // double distance = 70.75d / Math.tan(31.5d + targetYOffset);
        double distance = 70.75d / Math.sin(31.5d + targetYOffset);
        //System.out.println("Y offset:" + targetYOffset + ":Distance:" + distance + ":Formula:" + Math.sin(31.5d + targetYOffset));
        return distance;
    }
}
