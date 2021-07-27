package auto;

import java.util.ArrayList;
import java.util.List;

public class AutoInterpreter {

    private AutoLexer lexer;
    
    public AutoInterpreter(List<String> codeLines)
    {
        List<String> strippedCodeLines = new ArrayList<String>();
        for (String line : codeLines)
        {
            if (line.substring(0, 2) == "//") continue;
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
