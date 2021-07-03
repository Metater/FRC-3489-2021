package frc.robot.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.shared.interfaces.IRobotListener;

public class ShuffleboardHandler extends BaseHandler implements IRobotListener {

    public ShuffleboardTab tab = Shuffleboard.getTab("3489 New Robot");

    private List<NetworkTableEntry> networkTableEntries = new ArrayList<NetworkTableEntry>();

    private HashMap<String, NetworkTableEntry> simpleWidgets = new HashMap<String, NetworkTableEntry>();
    
    public ShuffleboardHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
    }

    public void robotInit()
    {

    }

    public void robotPeriodic()
    {

    }

    private NetworkTableEntry tryMakeEntry(String widgetName, Object value)
    {
        NetworkTableEntry entry;
        if (simpleWidgets.containsKey(widgetName)) // Widget exists, display data
        {
            entry = simpleWidgets.get(widgetName);
        }
        else // Widget doesn't exist, create it, make reference and display data
        {
            entry = tab.add(widgetName, value).getEntry();
            simpleWidgets.put(widgetName, entry);
        }
        return entry;
    }

    public void displayBool(String widgetName, boolean value)
    {
        tryMakeEntry(widgetName, value).setBoolean(value);
    }

    public void displayDouble(String widgetName, double value)
    {
        tryMakeEntry(widgetName, value).setDouble(value);
    }

    public void displayString(String widgetName, String value)
    {
        tryMakeEntry(widgetName, value).setString(value);
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
