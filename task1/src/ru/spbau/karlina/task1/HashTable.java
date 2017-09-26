package ru.spbau.karlina.task1;
import ru.spbau.karlina.task1.list.List;

/**
 * Class stores pairs: String key and String value
 * Using List and Separate chaining
 * Rebuild when count of storing pairs became more than 2 * hashTableMax
 */
public class HashTable {
    private int hashTableMax = 4;
    private List[] table = new List[hashTableMax];
    private int sizeOfPairs;
    private SimpleHash hash = new SimpleHash(hashTableMax);

    public HashTable() {
        makeInitialization(table, hashTableMax);
    }

    private static void makeInitialization(List[] array, int arraySize) {
        for (int i = 0; i < arraySize; ++i) {
            array[i] = new List();
        }
    }

    /** Return amount of all stored pairs */
    public int size() {
        return sizeOfPairs;
    }

    public void clean() {
        makeInitialization(table, hashTableMax);
        sizeOfPairs = 0;
    }

    /**
     * @param key Qualifier for finding
     * @return deleted string value with input key and null if there isn't such pair
     */
    public String remove(String key) {
        List list = table[hash.getHash(key)];
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
        return table[hash.getHash(key)].get(key);
    }

    /**
     * @param key Qualifier for finding
     * @return true if there is a pair with input key and false in other case
     */
    public boolean contains(String key) {
       return table[hash.getHash(key)].contains(key);
    }

    /**
     * @param key Qualifier of input string value
     * @param value String data
     * @return previous string value from pair with input key and null if there isn't such pair before
     */
    public String put(String key, String value) {
        List list = table[hash.getHash(key)];
        String string = list.add(key, value);

        if (string == null)
            sizeOfPairs++;

        if (sizeOfPairs > hashTableMax)
            rebuild();

        return string;
    }

    /**
     * Call when count of storing pairs became more than 2 size of hashtable array
     * */
    private void rebuild() {
        hashTableMax *= 2;
        List[] newTable = new List[hashTableMax];
        makeInitialization(newTable, hashTableMax);
        hash.changeRange(hashTableMax);

        for (int i = 0; i < hashTableMax / 2; i++)
            table[i].fillArrayWithStoringPairs(newTable, hash);

        table = newTable;
    }
}