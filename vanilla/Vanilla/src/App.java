import java.util.List;
import auto.AutoParser;
import auto.AutoToken;

public class App {
    public static void main(String[] args) throws Exception
    {
        AutoParser autoParser;

        List<String> codeLines = FileUtils.getLines(System.getProperty("user.dir") + "/Vanilla/src/test.auto");
        System.out.println("--------------------------------");
        for (String line : codeLines)
        {
            System.out.println(line);
        }
        System.out.println("--------------------------------");


        autoParser = new AutoParser(codeLines);

        /*
        AutoToken token = autoParser.lexer.getNextToken();
        while (token.type != "eof")
        {
            System.out.println(token.type + "   " + token.value);
            token = autoParser.lexer.getNextToken();
        }
        */
    }
}
