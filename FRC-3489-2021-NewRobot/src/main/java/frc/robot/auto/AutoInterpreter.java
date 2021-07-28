package frc.robot.auto;

import java.util.List;

import frc.robot.shared.utils.FileUtils;

public class AutoInterpreter {

    private List<AutoInstruction> instructions;

    public AutoInterpreter(String autoFileName)
    {
        System.out.println("Start auto load, lex and parse");
        List<String> codeLines = FileUtils.readLocalFile(autoFileName + ".auto");
        AutoParser autoParser = new AutoParser(codeLines);
        instructions = autoParser.getInstructions();
        System.out.println("End auto load, lex and parse");
    }
}
