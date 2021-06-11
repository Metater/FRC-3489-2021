package frc.robot;

public class DataLogger {

    private String fileName;
    private String dataLog = "";
    
    public DataLogger(String fileName)
    {
        this.fileName = fileName;
    }

    public void AddLine(String line)
    {
        dataLog += line + "/n";
    }

    public void SaveData()
    {
        if (!FileUtils.fileExists(fileName))
            FileUtils.createLocalFile(fileName);
        FileUtils.writeLocalFile(fileName, dataLog);
    }

    public void Reset()
    {
        dataLog = "";
    }
}
