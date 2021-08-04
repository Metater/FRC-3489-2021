package frc.robot.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.shared.interfaces.IRobotListener;

public class ShuffleboardHandler extends BaseHandler implements IRobotListener {

    public ShuffleboardTab tab = Shuffleboard.getTab("3489 Ion V");
    public ShuffleboardTab autoTab = Shuffleboard.getTab("3489 Ion V Auto");

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

    private NetworkTableEntry tryMakeEntry(ShuffleboardTab shuffleboardTab, String widgetName, Object value)
    {
        NetworkTableEntry entry;
        if (simpleWidgets.containsKey(widgetName))
        {
            entry = simpleWidgets.get(widgetName);
        }
        else
        {
            entry = shuffleboardTab.add(widgetName, value).getEntry();
            simpleWidgets.put(widgetName, entry);
        }
        return entry;
    }

    public void displayBool(ShuffleboardTab shuffleboardTab, String widgetName, boolean value)
    {
        tryMakeEntry(shuffleboardTab, widgetName, value).setBoolean(value);
    }

    public void displayDouble(ShuffleboardTab shuffleboardTab, String widgetName, double value)
    {
        tryMakeEntry(shuffleboardTab, widgetName, value).setDouble(value);
    }

    public void displayString(ShuffleboardTab shuffleboardTab, String widgetName, String value)
    {
        tryMakeEntry(shuffleboardTab, widgetName, value).setString(value);
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
