package frc.robot.auto;

import java.util.ArrayList;
import java.util.List;

public class AutoInstruction {
    

    public String instructionName;
    public List<AutoInstructionArg> arguments = new ArrayList<AutoInstructionArg>();

    public void loadName(String instructionName)
    {
        this.instructionName = instructionName;
    }

    public void loadArgument(AutoInstructionArg argument)
    {
        arguments.add(argument);
    }
}
