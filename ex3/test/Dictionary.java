package test;

import java.io.*;
import java.util.*;
public class Dictionary {
    private CacheManager wordsExist;
    private CacheManager wordsNotExist;
    private BloomFilter bloomFilter;
    private String[] files;

    public Dictionary(String...fileNames) {
        this.files = fileNames;
        this.wordsExist = new CacheManager(400, new LRU());
        this.wordsNotExist = new CacheManager(100, new LFU());
        this.bloomFilter = new BloomFilter(256, "MD5", "SHA1");
        AddWordToBloomFilter(fileNames);
    }

    public void AddWordToBloomFilter(String...fileNames) {
        Scanner scanner = null;
        for (String name : fileNames) {
            try {
                scanner = new Scanner(new BufferedReader(new FileReader(name)));
                while(scanner.hasNext()) {
                    String word = scanner.next();
                    bloomFilter.add(word);
                }
            }
            catch (IOException e) {System.err.println("Cannot read from file: "  + name);}
            finally {if(scanner != null) {scanner.close();}}
        }
    }

    public boolean query(String word) {
        if(wordsExist.query(word)) {return true;}
        if(wordsNotExist.query(word)) {return true;}
        if (bloomFilter.contains(word)) {wordsExist.add(word); return true;}
        else {wordsNotExist.add(word); return false;}
    }
    
    public boolean challenge(String word) {
        try {return IOSearcher.search(word, files);}
        catch(Exception e) {return false;}
    }
}
