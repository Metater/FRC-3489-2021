package frc.robot.handlers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.shared.handlers.BaseHandler;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.IRobotListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.ToggleButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;

public class LimelightHandler extends BaseHandler implements IButtonListener, IRobotListener {
    
    NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry pipeline = limelight.getEntry("pipeline");
    NetworkTableEntry targetX = limelight.getEntry("tx");
    NetworkTableEntry targetY = limelight.getEntry("ty");
    NetworkTableEntry targetArea = limelight.getEntry("ta");

    private boolean limelightActivated = false;

    public LimelightHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;

        ToggleButtonUpdate toggleLimelight = new ToggleButtonUpdate(this, PeriodicType.Robot, "ToggleLimelight", new ButtonLocation(8, JoystickType.Manipulator), 0.05);
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
        robotHandler.shuffleboardHandler.displayDouble("Distance to Target (in)", distance);
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
