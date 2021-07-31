package auto;

public class AutoInstructionArg {
    
    public enum ArgType
    {
        Integer,
        Double,
        String,
        Constant,
        Identifier
    }

    public ArgType argType;

    public int _integer;
    public double _double;
    public String _string;
    public String _constant;
    public String _identifier;

    public AutoInstructionArg(ArgType argType, int _integer)
    {
        this.argType = argType;
        this._integer = _integer;
    }
    public AutoInstructionArg(ArgType argType, double _double)
    {
        this.argType = argType;
        this._double = _double;
    }
    public AutoInstructionArg(ArgType argType, String str)
    {
        this.argType = argType;
        switch (argType)
        {
            case String:
                _string = str;
                break;
            case Constant:
                _constant = str;
                break;
            case Identifier:
                _identifier = str;
                break;
            default:
                AutoParser.error();
                break;
        }
    }
}
