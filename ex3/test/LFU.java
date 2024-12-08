package test;

import java.util.*; 

public class LFU implements CacheReplacementPolicy {
    private LinkedHashMap<String, Integer> strings; 

    public LFU() {
        strings = new LinkedHashMap<String, Integer>();
    }

    @Override
    public void add(String word) {
        if (strings.containsKey(word)) {strings.replace(word, strings.get(word)+1);}
        else {strings.put(word,1);}        
    }

    @Override
    public String remove() {
        int minFreq = Integer.MAX_VALUE;
        String leastUsed = null;
        
        for (Map.Entry<String, Integer> entry : strings.entrySet()) {
            if (entry.getValue() < minFreq) {
                minFreq = entry.getValue();
                leastUsed = entry.getKey();
            }
        }
        return leastUsed;
    }
}
