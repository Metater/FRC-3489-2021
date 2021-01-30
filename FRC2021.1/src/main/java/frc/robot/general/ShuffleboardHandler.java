package frc.robot.general;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

public class ShuffleboardHandler {

    private RobotHandler robotHandler;

    ShuffleboardTab tab = Shuffleboard.getTab("3489 2021");

    //ArrayList<SimpleWidget> simpleWidgets = new ArrayList<SimpleWidget>();
    Map<String, SimpleWidget> simpleWidgets = new HashMap<String, SimpleWidget>();

    public ShuffleboardHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    //https://docs.wpilib.org/en/stable/docs/software/wpilib-tools/shuffleboard/layouts-with-code/sending-data.html

    public void PrintDoubleToWidget(String name, double value)
    {
        if (simpleWidgets.containsKey(name))
        {
            simpleWidgets.get(name).getEntry().setDouble(value);
        }
        else
        {
            SimpleWidget sw = tab.add(name, value);
            sw.getEntry().setDouble(value);
            simpleWidgets.put(name, sw);
        }
    }

    public void PrintStringToWidget(String name, String value)
    {
        if (simpleWidgets.containsKey(name))
        {
            simpleWidgets.get(name).getEntry().setString(value);
        }
        else
        {
            SimpleWidget sw = tab.add(name, value);
            sw.getEntry().setString(value);
            simpleWidgets.put(name, sw);
        }
    }
    public void PrintBooleanToWidget(String name, Boolean value)
    {
        if (simpleWidgets.containsKey(name))
        {
            simpleWidgets.get(name).getEntry().setBoolean(value);
        }
        else
        {
            SimpleWidget sw = tab.add(name, value);
            sw.getEntry().setBoolean(value);
            simpleWidgets.put(name, sw);
        }
    }
    
    
    
    public double DoubleToPercent1Dec(double value)
    {
        return ((double)((int)(value*1000)))/10;
    }
    public double DoubleToPercent2Dec(double value)
    {
        return ((double)((int)(value*10000)))/100;
    }
}
