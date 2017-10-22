package ru.spbau.karlina.task1;

/** Main class with only one main methoud */
public class Main {
    /**
     * Main function with checking hash table function
     * @param argc Parametrs of command lina (don't use)
     */
    public static void main(String[] argc) {
        System.out.println("Hello world");
        HashTable hashTable = new HashTable();
        System.out.println(hashTable.size());
        String first = hashTable.put("key1", "value1");
        System.out.println(hashTable.size());
        System.out.println(first);
        System.out.println(hashTable.contains("key1"));
        System.out.println(hashTable.put("key1", "value2"));
        System.out.println(hashTable.size());
        System.out.println(hashTable.remove("key1"));
        System.out.println(hashTable.size());
    }
}