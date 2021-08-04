package frc.robot.auto;

import java.util.List;

import frc.robot.handlers.AutoHandler;
import frc.robot.shared.utils.FileUtils;

public class AutoInterpreter {

    private AutoHandler autoHandler;
    private List<AutoInstruction> instructions;

    private int currentInstruction = 0;

    public boolean finished = false;

    public AutoInterpreter(AutoHandler autoHandler, String autoFileName)
    {
        this.autoHandler = autoHandler;

        System.out.println("Start auto load, lex and parse");
        List<String> codeLines = FileUtils.readLocalFile(autoFileName + ".auto");
        AutoParser autoParser = new AutoParser(codeLines);
        instructions = autoParser.getInstructions();
        System.out.println("End auto load, lex and parse");
    }

    public void finished()
    {
        System.out.println("Finished instruction: " + currentInstruction);
        currentInstruction++;
    }

    public void cycle()
    {
        if (instructions.size() > currentInstruction)
        {
            run(instructions.get(currentInstruction));
        }
    }

    private void run(AutoInstruction instruction)
    {
        switch (instruction.instructionName) {
            case "print":
                autoHandler.print(instruction);
                break;
            case "delay":
                autoHandler.delay(instruction);
                break;
            case "moveForSeconds":
                autoHandler.moveForSeconds(instruction);
                break;
            case "move":
                autoHandler.move(instruction);
                break;
            default:
                AutoParser.error("Unknown instruction: " + instruction.instructionName + " at index: " + currentInstruction);
                break;
        }
    }
}
