/**
 * Created by Liuba Karlina on 03.09.15.
 * @author Liuba Karlina
 */

package ru.spbau.karlina.task1;
import ru.spbau.karlina.task1.list.List;

/**
 * Hash table class:
 * Store pair: String key and String value
 * @author Liuba Karlina
 */
public class HashTable {
    /**
     * Container of lists with data values
     */
    private List[] table;

    /**
     * Size of storing pairs
     */
    private int sizeOfPair;

    private int tableMax = 33;

    /**
     * Hash table constructor
     * Set default values for pairs container and pairs size
     * @author Liuba Karlina
     */
    public HashTable () {
        table = new List[tableMax];

        for (int i = 0; i < tableMax; ++i) {
            table[i] = new List();
        }
        sizeOfPair = 0;
    }

    /**
     * Return amount of all pairs
     * @author Liuba Karlina
     * @return sizeOfPairs
     */
    public int size () {
        return sizeOfPair;
    }

    /**
     * Clean pairs container
     * @author Liuba Karlina
     */
    public void clean() {
       table = null;
       sizeOfPair = 0;
    }

    /**
     * Remove node with input key from hashtable
     * @author Liuba Karlina
     * @param key Qualifier for finding
     * @return string value with input key and null if there isn't such pair
     */
    public String remove (String key) {

        List list = table[hash(key)];
        sizeOfPair -= list.size();
        String string = list.remove(key);
        sizeOfPair += list.size();

        return string;
    }

    /**
     * Find pair with input key
     * @author Liuba Karlina
     * @param key Qualifier for finding
     * @return string value from pair with input key and null if there isn't such pair
     */
    public String get (String key) {

        return table[hash(key)].get(key);
    }

    /**
     * Checking existing pair with input key
     * @author Liuba Karlina
     * @param key Qualifier for finding
     * @return true if there is a pair with input key and false in other case
     */
    public boolean contains(String key) {
       return table[hash(key)].contains(key);
    }

    /**
     * Add to hash table pair with input data
     * @author Liuba Karlina
     * @param key Qualifier of input string value
     * @param value String data
     * @return previous string value from pair with input key and null if there isn't such pair before
     */
    public String put(String key, String value) {

        List list = table[hash(key)];
        sizeOfPair -= list.size();
        String string = list.add(key, value);
        sizeOfPair += list.size();

        return string;
    }

    /**
     * Make simple hash function
     * @author Liuba Karlina
     * @param key String to make hash
     * @return Number in range 0...(tableMax-1) to identify index in list of pairs container
     */
    private int hash( String key ) {

        int hash = 7;
        for (int i = 0; i < key.length(); i++) {
            hash = hash*3 + key.charAt(i);
        }

        return hash % tableMax;
    }
}
