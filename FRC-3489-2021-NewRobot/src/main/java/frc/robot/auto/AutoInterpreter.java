package frc.robot.auto;

import java.util.List;

import frc.robot.handlers.AutoHandler;
import frc.robot.shared.utils.FileUtils;

public class AutoInterpreter {

    private AutoHandler autoHandler;
    private List<AutoInstruction> instructions;

    private int currentInstruction = 0;

    public AutoInterpreter(AutoHandler autoHandler, String autoFileName)
    {
        this.autoHandler = autoHandler;

        System.out.println("Start auto load, lex and parse");
        List<String> codeLines = FileUtils.readLocalFile(autoFileName + ".auto");
        AutoParser autoParser = new AutoParser(codeLines);
        instructions = autoParser.getInstructions();
        System.out.println("End auto load, lex and parse");
    }
}
