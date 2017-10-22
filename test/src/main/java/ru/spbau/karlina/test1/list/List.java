package ru.spbau.karlina.test1.list;

import java.util.Iterator;

/** Сonnected List class */
public class List<K, V> implements Iterator<K>{
    public boolean hasNext() {
        if (flagIterator == false) {
            flagIterator = true;
            if (head == null) {
                return false;
            }

            currentIterator = head;
            return true;
        }

        if (currentIterator == null) {
            flagIterator = false;
            return false;
        }

        return true;
    }

    public K next() {
        K key = currentIterator.key;
        currentIterator = currentIterator.next;

        return key;
    }

    public void remove() {
    }

    /** List element contains value and pointer to next element */
    static class Node<K, V> {
        private K key;
        private V value;
        private Node next = null, prev = null;


        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V> head = null, tail = null, currentIterator = null;
    private boolean flagIterator = false;

    public V addToEnd(K key, V value) {
        if (get(key) == null) {
            if (tail != null) {
                tail.next = new Node<K, V>(key, value);
                tail.next.prev = tail;
                tail = tail.next;
            } else {
                head = new Node<K, V>(key, value);
                tail = head;
            }
            return value;
        }

        return null;
    }

    public V get(K key) {
        Node<K, V> current = head;

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

    public boolean contains(K key) {
        V value = get(key);

        if (value == null) {
            return false;
        }

        return true;
    }

    /**
     * @param key Qualifier for finding
     * @return string value from node with input key and null if there isn't such node
     */
    public V remove(K key) {
        if (head == null) {
            return null;
        }

        if (head.key.equals(key)) {
            V value = head.value;
            head.next.prev = null;
            head = head.next;
            return value;
        }

        Node<K, V> current = head;
        while (current != null) {

            if (current.key.equals(key)) {
                V value = current.value;
                if (current == tail) {
                    tail = current.prev;
                } else {
                    current.next.prev = current.prev;
                }
                current.prev = head.next;
                return value;
            }

        }

        return null;
    }

    public K getHeadKey() {
        if (head != null) {
            return head.key;
        }

        return null;
    }

    public V getHeadValue() {
        if (head != null) {
            return head.value;
        }

        return null;
    }

    /** Remove list head */
    public void pop() {
        if (head != null) {
            if (tail == head) {
                head = null;
                tail = null;
            } else {
                head.next.prev = null;
                head = head.next;
            }
        }
    }
}