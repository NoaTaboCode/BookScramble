package test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOSearcher {

    public static boolean search(String word,String...fileNames)throws IOException
    {
        for(String fileName: fileNames)
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) 
            {
                String line;
            
            while ((line = reader.readLine()) != null) 
            {
                if (line.contains(word))
                 {
                    return true;
                 }

            }
        }}
        return false;
    }
}