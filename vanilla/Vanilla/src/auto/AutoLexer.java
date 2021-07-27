package auto;

public class AutoLexer {

    private String code;
    private int readPos;
    private char currentChar;
    private boolean eof = false;
    

    public AutoLexer(String code)
    {
        this.code = code;
        currentChar = code.toCharArray()[0];
    }

    private void advance(int amt)
    {
        readPos += amt;
        if (code.length() > readPos)
            currentChar = code.toCharArray()[readPos];
        else
            eof = true;
    }

    private char peek(int depth)
    {
        int peekPos = readPos + depth;
        if (peekPos > code.length() - 1)
            return ' ';
        else return code.toCharArray()[peekPos];
    }

    private boolean peekIsEof(int depth)
    {
        return readPos + depth > code.length() - 1;
    }

    private void skipWhitespace()
    {
        while (currentChar == ' ' && !eof)
            advance(1);
    }

    private AutoToken readNumeric()
    {
        String result = "";
        boolean isDecimal = false;
        while ((Character.isDigit(currentChar)))
    }
}
