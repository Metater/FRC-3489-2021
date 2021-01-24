package frc.robot.general;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

public class ShuffleboardHandler {

    private RobotHandler robotHandler;

    ShuffleboardTab tab = Shuffleboard.getTab("3489 2021");

    ArrayList<SimpleWidget> simpleWidgets = new ArrayList<SimpleWidget>();

    public ShuffleboardHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    //https://docs.wpilib.org/en/stable/docs/software/wpilib-tools/shuffleboard/layouts-with-code/sending-data.html

    public void PrintDoubleToWidget(String name, double value)
    {
        if (doesWidgetNameExist(name))
        {
            simpleWidgets.get(indexOfName(name)).getEntry().setDouble(value);
        }
        else
        {
            SimpleWidget sw = tab.add(name, value);
            sw.getEntry().setDouble(value);
            simpleWidgets.add(sw);
        }
    }

    public void PrintStringToWidget(String name, String value)
    {
        if (doesWidgetNameExist(name))
        {
            simpleWidgets.get(indexOfName(name)).getEntry().setString(value);
        }
        else
        {
            SimpleWidget sw = tab.add(name, value);
            sw.getEntry().setString(value);
            simpleWidgets.add(sw);
        }
    }

    private boolean doesWidgetNameExist(String name)
    {
        System.out.println("Does widget name exist");
        for(SimpleWidget sw : simpleWidgets)
            if (sw.getTitle().equals(name))
                return true;
        return false;
    }
    private int indexOfName(String name)
    {
        System.out.println("Index of name stuff");
        for (int i = 0; i < simpleWidgets.size(); i++)
            if (simpleWidgets.get(i).getTitle().equals(name))
                return i;
        return -1;
    }
    // This needs to be tested later
    private ArrayList<SimpleWidget> getSimpleWidgets()
    {
        System.out.println("Get simple widgets");
        ArrayList<SimpleWidget> simpleWidgets = new ArrayList<SimpleWidget>();
        for(ShuffleboardComponent<?> sc : tab.getComponents())
            if (sc.getClass().isInstance(SimpleWidget.class))
                simpleWidgets.add((SimpleWidget)sc);
        return simpleWidgets;

        //https://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/shuffleboard/ShuffleboardContainer.html
        //https://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/shuffleboard/ShuffleboardComponent.html#getType()
        
        /*
        ArrayList<SimpleWidget> simpleWidgets = new ArrayList<SimpleWidget>();
        for(ShuffleboardComponent<?> sc : tab.getComponents())
            if (sc.getType().equals("SimpleWidget"))
                simpleWidgets.add((SimpleWidget)sc);
        return simpleWidgets;
        */
        /*
        ArrayList<SimpleWidget> simpleWidgets = new ArrayList<SimpleWidget>();
        for(ShuffleboardComponent<?> sc : tab.getComponents())
            if (sc.getClass().isInstance(SimpleWidget.class))
                simpleWidgets.add((SimpleWidget)sc);
        return simpleWidgets;
        */

        /*
        return new ArrayList<SimpleWidget>(tab.getComponents().stream().filter(SimpleWidget.class::isInstance)
                    .map(SimpleWidget.class::cast)
                    .collect(Collectors.toList()));
        */
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
