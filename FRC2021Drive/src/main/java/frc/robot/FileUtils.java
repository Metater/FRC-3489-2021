package frc.robot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Filesystem;

public class FileUtils {
    public static void printAllDirs()
    {
        String[] fileNames = getFileNamesInDir(Filesystem.getOperatingDirectory());
        for (String fileName : fileNames)
        {
            System.out.println(fileName);
        }
    }

    public static void writeLocalFile(String fileName, String data)
    {
        File file = new File(getUsedDirAndFile(fileName));
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset)) {
            writer.write(data, 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }
    public static ArrayList<String> readLocalFile(String fileName)
    {
        ArrayList<String> lines = new ArrayList<String>();
        try  
        {
            File file = new File(getUsedDirAndFile(fileName));    //creates a new file instance  
            FileReader fr=new FileReader(file);   //reads the file  
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
            String line;
            while((line=br.readLine())!=null)  
            {  
                lines.add(line);
            }  
            fr.close();    //closes the stream and release the resources  
        }  
        catch(IOException e)  
        {  
            e.printStackTrace();  
        }  
        return lines;
    }

    public static String getUsedDirAndFile(String fileName)
    {
        return Filesystem.getOperatingDirectory().toPath().toString() + "/" + fileName;
    }

    public static String[] getFileNamesInDir(File fileInDir)
    {
        String[] files;
        files = fileInDir.list();
        return files;
    }

    public static boolean fileExists(String fileName)
    {
        File tempDir = new File(Filesystem.getOperatingDirectory().toPath().toString() + "/" + fileName);
        return tempDir.exists();
    }

    public static void createLocalFile(String fileName)
    {
        try
        {
            File f = new File(Filesystem.getOperatingDirectory().toPath().toString() + "/" + fileName);
            f.createNewFile();
        }
        catch (IOException e)
        {

        }
    }
}
