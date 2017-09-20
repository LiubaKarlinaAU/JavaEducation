package ru.spbau.karlina.task1;
import ru.spbau.karlina.task1.list.List;

/**
 * Class stores pairs: String key and String value
 * Using List and Separate chaining
 */
public class HashTable {
    private int tableMax = 33;
    private List[] table = new List[tableMax];
    private int sizeOfPairs;

    public HashTable() {

        for (int i = 0; i < tableMax; ++i) {
            table[i] = new List();
        }
    }

    /** Return amount of all stored pairs */
    public int size() {
        return sizeOfPairs;
    }

    public void clean() {
       table = null;
       sizeOfPairs = 0;
    }

    /**
     * @param key Qualifier for finding
     * @return deleted string value with input key and null if there isn't such pair
     */
    public String remove(String key) {
        List list = table[hash(key)];
        String string = list.remove(key);

        if (string != null)
            sizeOfPairs--;

        return string;
    }

    /**
     * @param key Qualifier for finding
     * @return string value from pair with input key and null if there isn't such pair
     */
    public String get(String key) {
        return table[hash(key)].get(key);
    }

    /**
     * @param key Qualifier for finding
     * @return true if there is a pair with input key and false in other case
     */
    public boolean contains(String key) {
       return table[hash(key)].contains(key);
    }

    /**
     * @param key Qualifier of input string value
     * @param value String data
     * @return previous string value from pair with input key and null if there isn't such pair before
     */
    public String put(String key, String value) {
        List list = table[hash(key)];
        String string = list.add(key, value);

        if (string == null)
            sizeOfPairs ++;

        return string;
    }

    /**
     * @param key String to make hash
     * @return Number in range 0...(tableMax-1) to identify index in list of pairs container
     */
    private int hash( String key ) {
        int hash = 7;
        for (int i = 0; i < key.length(); i++) {
            hash = hash * 3 + key.charAt(i);
        }

        return hash % tableMax;
    }
}