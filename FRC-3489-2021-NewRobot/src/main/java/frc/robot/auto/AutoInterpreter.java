package frc.robot.auto;

import java.util.ArrayList;

import frc.robot.shared.utils.FileUtils;

public class AutoInterpreter {

    private ArrayList<String> autoTextFileLines;
    private AutoTokenizer autoTokenizer = new AutoTokenizer();

    public AutoInterpreter(String autoFileName)
    {
        autoTextFileLines = FileUtils.readLocalFile(autoFileName + ".auto");

        System.out.println("Start auto load");
        for (String line : autoTextFileLines)
        {
            autoTokenizer.tokenizeLine(line);
            System.out.println(line);
        }
        System.out.println("End auto load");
    }
}
