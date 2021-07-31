package frc.robot.auto;

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
        while ((Character.isDigit(currentChar) || (currentChar == '.' && !isDecimal)) && !eof)
        {
            if (currentChar == '.') isDecimal = true;
            result += currentChar;
            advance(1);
        }
        if (isDecimal)
            return new AutoToken("double", Double.parseDouble(result));
        return new AutoToken("integer", Integer.parseInt(result));
    }

    private AutoToken readIdentifier()
    {
        String result = "";
        while (Character.isLetterOrDigit(currentChar) && !eof)
        {
            result += currentChar;
            advance(1);
        }
        return new AutoToken("identifier", result);
    }

    private AutoToken readString()
    {
        String result = "";
        advance(1);
        while (currentChar != '\"')
        {
            result += currentChar;
            advance(1);
        }
        advance(1);
        return new AutoToken("string", result);
    }

    private AutoToken readConstant()
    {
        String result = "";
        advance(1);
        while (currentChar != '\'')
        {
            result += currentChar;
            advance(1);
        }
        advance(1);
        return new AutoToken("constant", result);
    }

    public AutoToken getNextToken()
    {
        while (!eof)
        {
            if (currentChar == ' ')
                skipWhitespace();
            else if (Character.isLetter(currentChar))
                return readIdentifier();
            else if (Character.isDigit(currentChar))
                return readNumeric();
            else if (currentChar == '\"')
                return readString();
            else if (currentChar == '\'')
                return readConstant();
            else
            {
                char c = currentChar;
                Object o = null;
                advance(1);
                return new AutoToken(Character.toString(c), o);
            }
        }
        Object o = null;
        return new AutoToken("eof", o);
    }
}
