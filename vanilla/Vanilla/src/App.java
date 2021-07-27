import java.util.List;

import auto.AutoInterpreter;

public class App {
    public static void main(String[] args) throws Exception
    {
        AutoInterpreter autoInterpreter;

        List<String> codeLines = FileUtils.getLines(System.getProperty("user.dir") + "/Vanilla/src/test.auto");
        System.out.println("--------------------------------");
        for (String line : codeLines)
        {
            System.out.println(line);
        }
        System.out.println("--------------------------------");


        autoInterpreter = new AutoInterpreter(codeLines);
    }
}
