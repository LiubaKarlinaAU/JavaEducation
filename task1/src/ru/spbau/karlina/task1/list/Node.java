/**
 * Created by Liuba Karlina on 03.09.15.
 * @author Liuba Karlina
 */

package ru.spbau.karlina.task1.list;

/**
 * List element
 * Node contain two String object and pointer to next element.
 */
public class Node {
    /**
     * First Node data
     */
    public String key;

    /**
     * Second Node data
     */
    public String value;

    /**
     * Pointer to next element
     */
    public Node next = null;

    /**
     * Node constructor
     * @author Liuba Karlina
     * @param key first string data
     * @param value   second string data
     */
    public Node (String key, String value) {
        this.key = key;
        this.value = value;
    }
}