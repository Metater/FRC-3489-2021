package frc.robot.general;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleboardHandler {

    private RobotHandler robotHandler;

    ShuffleboardTab tab = Shuffleboard.getTab("3489 2021");

    private List<NetworkTableEntry> tabs

    public ShuffleboardHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    //https://docs.wpilib.org/en/stable/docs/software/wpilib-tools/shuffleboard/layouts-with-code/sending-data.html

    public void PrintDoubleToTabTitle(String title, double value)
    {
        NetworkTableEntry tabWithTitle = tab.add(title, value).getEntry();
        tabWithTitle.setDouble(value);
        tab.
    }
}
