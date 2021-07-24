package frc.robot.auto;

import java.util.ArrayList;
import java.util.List;

public class AutoTokenizer {

    public List<AutoInstruction> autoInstructions = new ArrayList<AutoInstruction>();

    public AutoTokenizer()
    {

    }
    
    public void tokenizeLine(String line)
    {
        if (line.substring(0, 2) == "//") return;
        char[] chars = line.toCharArray();
        int readPos = 0;
        while(readPos !=)
    }
}
