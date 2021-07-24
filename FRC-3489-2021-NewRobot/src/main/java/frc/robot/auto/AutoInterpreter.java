package frc.robot.auto;

import java.util.ArrayList;

import frc.robot.shared.utils.FileUtils;

public class AutoInterpreter {

    private ArrayList<String> autoTextFileLines;

    public AutoInterpreter(String autoFileName)
    {
        autoTextFileLines = FileUtils.readLocalFile(autoFileName + ".auto");

        System.out.println("Start auto load");
        for (String line : autoTextFileLines)
        {
            System.out.println(line);
        }
        System.out.println("End auto load");
    }
}
