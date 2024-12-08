package test;
import java.util.HashMap;

public class CacheManager {
    private int maxSize;
    private HashMap<String, Integer> cache;
    private CacheReplacementPolicy replacementPolicy;

    public CacheManager(int maxSize, CacheReplacementPolicy replacementPolicy) {
        this.maxSize = maxSize;
        this.cache = new HashMap<>();
        this.replacementPolicy = replacementPolicy;
    }

    public boolean query(String word) {
        if( cache.containsKey(word))
        return true;
    
    else
        return false;

    }

    public void add(String word) {
        if (cache.size() >= maxSize) {
            String wordToRemove = replacementPolicy.remove();
            cache.remove(wordToRemove);
        }
        cache.put(word, 1);
        replacementPolicy.add(word);
    }
}
