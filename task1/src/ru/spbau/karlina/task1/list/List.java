package ru.spbau.karlina.task1.list;

import ru.spbau.karlina.task1.SimpleHash;

/** Simply connected List class */
public class List {
    /** List element contains two String and pointer to next element */
    static class Node {
        private String key;
        private String value;
        private Node next = null;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node head = null;

    /**
     * Add instead of node with input key or make new one in the end of list
     * @param key Qualifier of input string value
     * @return previous string value from node with input key and null if there isn't such node
     */
    public String add(String key, String value) {
        Node current = head;

        while (current != null) {

            if (current.key.equals(key)) {
                String prev = current.value;
                current.value = value;
                return prev;
            }

            current = current.next;
        }

        current = head;
        head = new Node(key, value);
        head.next = current;
        return null;
    }

    /**
     * @param key Qualifier for finding
     * @return string value from node with input key and null if there isn't such node
     */
    public String get(String key) {
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
     * @param key Qualifier for finding
     * @return true if there is node with input key and false in other case
     */
    public boolean contains(String key) {
        String string = get(key);

        if (string == null) {
            return false;
        }

        return true;
    }

    /**
     * @param key Qualifier for finding
     * @return string value from node with input key and null if there isn't such node
     */
    public String remove(String key) {
        if (head == null) {
            return null;
        }

        if (head.key.equals(key)) {
            String value = head.value;
            head = head.next;
            return value;
        }

        Node prev = head;
        Node current = head.next;

        while (current != null) {

            if (current.key.equals(key)) {
                String value = current.value;
                prev.next = current.next;
                return value;
            }

            prev = current;
            current = current.next;
        }

        return null;
    }

    public void fillArrayWithStoringPairs( List[] newTable, SimpleHash hash ) {
        Node current = head;

        while (current != null) {
            newTable[hash.getHash(current.key)].add(current.key, current.value);
            current = current.next;
        }
    }
}