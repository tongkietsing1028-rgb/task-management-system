package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    public static List<String> readAllLines(String filePath)
    {
        List<String> lines = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = br.readLine()) != null){
                lines.add(line);
            }
            br.close();
        }
        catch(IOException e){
            System.out.println("File read error：" + filePath);
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeAllLines(String filePath, List<String> lines)
    {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            for(String line :lines)
            {
                bw.write(line);
                bw.newLine();
            }
        }
        catch(IOException e){
            System.out.println("File write error：" + filePath);
            e.printStackTrace();
        }
    }

    public static void appendLine(String filePath,String line) {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true));
            bw.write(line);
            bw.newLine();
            bw.close();
        }
        catch (IOException e){
            System.out.println("File append error：" + filePath);
            e.printStackTrace();
        }
    }
}
