package frc.robot.handlers;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.shared.handlers.BaseHandler;
import frc.robot.shared.interfaces.IRobotListener;

public class ShuffleboardHandler extends BaseHandler implements IRobotListener {

    public ShuffleboardTab tab = Shuffleboard.getTab("3489 New Robot");

    private List<NetworkTableEntry> networkTableEntries = new ArrayList<NetworkTableEntry>();
    
    public ShuffleboardHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        robotHandler.functionListenerHandler.addRobotListener(this);
    }

    public void robotInit()
    {

    }

    public void robotPeriodic()
    {

    }

    public void addEntry(NetworkTableEntry entry)
    {
        networkTableEntries.add(entry);
    }

    public NetworkTableEntry getEntry(String name)
    {
        for (NetworkTableEntry networkTableEntry : networkTableEntries)
        {
            if (networkTableEntry.getName() == name) return networkTableEntry;
        }
        return null;
    }
}
