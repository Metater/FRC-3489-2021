package frc.robot.auto;

import java.util.ArrayList;
import java.util.List;

public class AutoParser {

    private AutoLexer lexer;

    private List<AutoInstruction> instructions = new ArrayList<AutoInstruction>();
    private AutoInstruction workingInstruction = new AutoInstruction();

    private AutoToken currentToken;

    public AutoParser(List<String> codeLines)
    {
        List<String> strippedCodeLines = new ArrayList<String>();
        for (String line : codeLines)
        {
            if (line.length() > 2) if (line.substring(0, 2).equals("//")) continue;
            strippedCodeLines.add(line);
        }
        String strippedCode = "";
        for (String line : strippedCodeLines)
        {
            strippedCode += line;
        }
        lexer = new AutoLexer(strippedCode);
        next();
        generateInstructions();
    }

    public List<AutoInstruction> getInstructions()
    {
        return instructions;
    }

    private void next()
    {
        currentToken = lexer.getNextToken();
    }
    
    public static void error(String message)
    {
        System.out.println(message);
        System.out.println(1/0);
    }

    private void eat(String tokenType)
    {
        if (currentToken.type.equals(tokenType))
            next();
        else
        {
            String message = "ERROR: Unexpected token: " + currentToken.type + "\nERROR: Expected token: " + tokenType;
            error(message);
        }
    }

    private void lick(String tokenType)
    {
        if (!currentToken.type.equals(tokenType))
        {
            String message = "ERROR: Unexpected token: " + currentToken.type + "\nERROR: Expected token: " + tokenType;
            error(message);
        }
    }

    private void readArgs()
    {
        switch (currentToken.type)
        {
            case ")":
                next();
                return;
            case ",":
                next();
                break;
            case "integer":
                workingInstruction.loadArgument(new AutoInstructionArg(AutoInstructionArg.ArgType.Integer, (int)currentToken.value));
                break;
            case "double":
                workingInstruction.loadArgument(new AutoInstructionArg(AutoInstructionArg.ArgType.Double, (double)currentToken.value));
                break;
            case "string":
                workingInstruction.loadArgument(new AutoInstructionArg(AutoInstructionArg.ArgType.String, (String)currentToken.value));
                break;
            case "constant":
                workingInstruction.loadArgument(new AutoInstructionArg(AutoInstructionArg.ArgType.Constant, (String)currentToken.value));
                break;
            case "identifier":
                workingInstruction.loadArgument(new AutoInstructionArg(AutoInstructionArg.ArgType.Identifier, (String)currentToken.value));
                break;
            default:
                String message = "ERROR: Unexpected token: " + currentToken.type + "\nERROR: Expected a token that can be an argument!";
                error(message);
                break;
        }
        next();
        readArgs();
    }

    private void generateInstruction()
    {
        lick("identifier");
        String instructionName = (String)currentToken.value;
        next();
        workingInstruction.loadName(instructionName);
        eat("(");
        readArgs();
        instructions.add(workingInstruction);
        System.out.println((instructions.size()-1) + ": " + workingInstruction.instructionName + " Arg Count: " + workingInstruction.arguments.size());
        System.out.print("Arg types: ");
        for (int i = 0; i < workingInstruction.arguments.size() - 1; i++)
        {
            System.out.print(workingInstruction.arguments.get(i).argType.toString() + ", ");
        }
        if (workingInstruction.arguments.size() > 0) System.out.print(workingInstruction.arguments.get(workingInstruction.arguments.size() - 1).argType.toString());
        System.out.println();
        workingInstruction = new AutoInstruction();
    }

    private void generateInstructions()
    {
        System.out.println("Start generating instructions!");
        while (!currentToken.type.equals("eof"))
        {
            generateInstruction();
        }
        System.out.println("Done generating instructions!");
    }
}
