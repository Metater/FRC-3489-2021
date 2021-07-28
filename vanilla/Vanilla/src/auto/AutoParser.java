package auto;

import java.util.ArrayList;
import java.util.List;

public class AutoParser {


    private AutoLexer lexer;
    private List<AutoInstruction> instructions = new ArrayList<AutoInstruction>();
    private AutoInstruction workingInstruction = new AutoInstruction();

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


    }
}
