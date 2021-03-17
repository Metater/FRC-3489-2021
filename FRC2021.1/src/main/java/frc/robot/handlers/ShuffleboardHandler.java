package frc.robot.handlers;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class ShuffleboardHandler {

    private RobotHandler robotHandler;

    ShuffleboardTab tab = Shuffleboard.getTab("3489 2021");

    //ArrayList<SimpleWidget> simpleWidgets = new ArrayList<SimpleWidget>();
    private Map<String, SimpleWidget> simpleWidgets = new HashMap<String, SimpleWidget>();
    private SendableChooser<Integer> recordingChooser = new SendableChooser<Integer>();

    public ShuffleboardHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        recordingChooser.setDefaultOption("i^2", -1);
        tab.add()
    }

    public int getSelectedRecording()
    {
        return recordingChooser.getSelected();
    }

    public void addRecording(int recordingId)
    {
        recordingChooser.addOption("Recording " + recordingId, recordingId);
        
    }

    //https://docs.wpilib.org/en/stable/docs/software/wpilib-tools/shuffleboard/layouts-with-code/sending-data.html

    public void printDoubleToWidget(String name, double value)
    {
        if (simpleWidgets.containsKey(name))
        {
            simpleWidgets.get(name).getEntry().setDouble(value);
        }
        else
        {
            SimpleWidget sw = getSimpleWidgetByTitle(name, (title) -> tab.add(title, value));
            sw.getEntry().setDouble(value);
            simpleWidgets.put(name, sw);
        }
    }

    public void printStringToWidget(String name, String value)
    {
        if (simpleWidgets.containsKey(name))
        {
            simpleWidgets.get(name).getEntry().setString(value);
        }
        else
        {
            SimpleWidget sw = getSimpleWidgetByTitle(name, (title) -> tab.add(title, value));
            sw.getEntry().setString(value);
            simpleWidgets.put(name, sw);
        }
    }
    public void printBooleanToWidget(String name, Boolean value)
    {
        if (simpleWidgets.containsKey(name))
        {
            simpleWidgets.get(name).getEntry().setBoolean(value);
        }
        else
        {
            SimpleWidget sw = getSimpleWidgetByTitle(name, (title) -> tab.add(title, value));
            sw.getEntry().setBoolean(value);
            simpleWidgets.put(name, sw);
        }
    }

    private SimpleWidget getSimpleWidgetByTitle(String title, Function<String, SimpleWidget> func)
    {
        List<ShuffleboardComponent<?>> components = tab.getComponents();
        for(ShuffleboardComponent<?> sc : components)
        {
            String scTitle = sc.getTitle();
            if (scTitle == title)
            {
                return (SimpleWidget)sc;
            }
        }
        return func.apply(title);
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
