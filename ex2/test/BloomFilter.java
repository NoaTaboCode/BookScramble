package test;

import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class BloomFilter {
    private BitSet bitSet;
    private final int bitSetSize;
    private MessageDigest[] hashFunctions;

    public BloomFilter(int size, String...algs) {
        this.bitSet = new BitSet(size);
        this.bitSetSize = size;
        this.hashFunctions = new MessageDigest[algs.length];
        for(int i=0; i<algs.length; i++) {
            try {hashFunctions[i] = MessageDigest.getInstance(algs[i]);}
            catch (NoSuchAlgorithmException e) {System.err.println("Hash Function does not found");}
        }

    }
    public void add(String str) {
        for(int i = 0; i < hashFunctions.length; i++) {
            byte[] bts=hashFunctions[i].digest(str.getBytes());
            BigInteger bi=new BigInteger(bts);   // להמיר את המערך בתים למספר BigInteger
            int num=bi.intValue(); // למספר שלם BigIntegerכדי להמיר את ה
            num=Math.abs(num);//לשים ערך מוחלט כדי שלא יהיה מספר שלילי 
            num=num% bitSetSize;
            if(!(bitSet.get(num)))//אם הערך המתאים לא מופעל במערך הביטים 
            bitSet.flip(num);//נדליק את הערך
        }
    }

    public boolean contains(String str)
    {
        for(int i = 0; i < hashFunctions.length; i++) {
            byte[] bts=hashFunctions[i].digest(str.getBytes());
            BigInteger bi=new BigInteger(bts);   // להמיר את המערך בתים למספר BigInteger
            int num=bi.intValue(); // למספר שלם BigIntegerכדי להמיר את ה
            num=Math.abs(num);//לשים ערך מוחלט כדי שלא יהיה מספר שלילי 
            num=num% bitSetSize;
            if(!(bitSet.get(num)))
            {
                return false;
            }
        

        }

        return true;

    }

    @Override
    public String toString() {
        
        StringBuilder st=new StringBuilder(bitSet.size());
        for(int i=0; i< bitSet.length(); i++){
            st.append(bitSet.get(i)? "1":"0");
        }
        return  st.toString();
        
    }

   




    

}