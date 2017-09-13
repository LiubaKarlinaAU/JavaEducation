/**
 * Created by Liuba Karlina on 03.09.15.
 * @author Liuba Karlina
 */

package ru.spbau.karlina.task1.list;

/**
 * Simply connected List class
 * Contains head of list and size
 * @author Liuba Karlina
 */

public class List {
    /**
     * List head
     */
    private Node head;
    /**
     * Lisr size
     */
    private int sizeOfList;

    /**
     * List constructor
     * Make default value for head and sizeOfList
     * @author Liuba Karlina
     */
    public List () {
       head = null;
       sizeOfList = 0;
    }

    /**
     * Return list size function
     * @author Liuba Karlina
     * @return sizeOfList
     */
    public int size() {
        return sizeOfList;
    }

    /**
     * Add to list data pair:
     * Instead of node with input key or make new one in the end of list
     * @author Liuba Karlina
     * @param key Qualifier of input string value
     * @param value String data
     * @return previous string value from node with input key and null if there isn't such node
     */
    public String add (String key, String value) {

        Node current = head;

        while (current != null) {

            if (current.key.equals(key)) {
                String prev = current.value;
                current.value = value;

                return prev;
            }

            if (current.next == null) {
                current.next = new Node(key, value);
                sizeOfList++;

                return null;
            }

            current = current.next;
        }

        head = new Node(key, value);
        sizeOfList++;

        return null;
    }

    /**
     * Find node with input key
     * @author Liuba Karlina
     * @param key Qualifier for finding
     * @return string value from node with input key and null if there isn't such node
     */
    public String get (String key) {

        Node current = head;
        while (current != null) {

            if (current.key.equals(key)) {

                return current.value;
            }

            current = current.next;
        }

        return null;
    }

    /**
     * Checking existing node with input key
     * @author Liuba Karlina
     * @param key Qualifier for finding
     * @return true if there is node with input key and false in other case
     */
    public boolean contains (String key) {

        Node current = head;
        while (current != null) {

            if (current.key.equals(key)) {

                return true;
            }

            current = current.next;
        }

        return false;
    }

    /**
     * Find node with input key and delete it from list
     * @author Liuba Karlina
     * @param key Qualifier for finding
     * @return string value from node with input key and null if there isn't such node
     */
    public String remove (String key) {

        if (head == null) {
            return null;
        }
        if (head.key.equals(key)) {
            String value = head.value;
            head = head.next;
            sizeOfList--;

            return value;
        }

        Node prev = head;
        Node current = head.next;

        while (current != null) {
            if (current.key.equals(key)) {
                String value = current.value;
                prev.next = current.next;
                sizeOfList--;

                return value;
            }

            prev = current;
            current = current.next;
        }

        return null;
    }
}
