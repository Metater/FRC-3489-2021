package frc.robot.auto;

public class AutoInstructionArg {
    
    public enum ArgType
    {
        Integer,
        Float,
        String,
        Constant,
        Identifier
    }

    public ArgType argType;

    public int _integer;
    public float _float;
    public String _string;
    public String _constant;
    public String _identifier;

    public AutoInstructionArg(ArgType argType, int _integer)
    {
        this.argType = argType;
        this._integer = _integer;
    }
    public AutoInstructionArg(ArgType argType, float _float)
    {
        this.argType = argType;
        this._float = _float;
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
                AutoParser.error("ERROR: ArgType matches no arg that contains a string!");
                break;
        }
    }
}
