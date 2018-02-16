package ru.spbau.karlina.task1.list;

import jdk.internal.util.xml.impl.Pair;
import ru.spbau.karlina.task1.SimpleHash;

/** Simply connected List class */
public class List {
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

    /** Find a pair(Node) with input key
     *  @param key Qualifier for finding
     *  @return string value from node with input key and null if there isn't such node
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

    /** Checking existing element with such key in list using get method
     *  @param key Qualifier for finding
     *  @return true if there is node with input key and false in other case
     */
    public boolean contains(String key) {
        String string = get(key);

        if (string == null) {
            return false;
        }

        return true;
    }

    /** Remove Node with input key if it was in list
     *  @param key Qualifier for finding
     *  @return string value from node with input key and null if there isn't such node
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

    public String getHeadKey() {
           if (head != null) {
               return head.key;
           }

           return null;
    }

    public String getHeadValue() {
        if (head != null) {
            return head.value;
        }

        return null;
    }

    /** Remove list head */
    public void pop() {
        if (head != null) {
            head = head.next;
        }
    }

    /** List element contains two String and pointer to next element */
    static private class Node {
        private String key;
        private String value;
        private Node next = null;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node head = null;
}