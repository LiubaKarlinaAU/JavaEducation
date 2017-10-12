package ru.spbau.karlina.task5;

/** Set class with three public methods^ add, size, contains */
public class Set<T extends Comparable> {
    /** Inner class for tree realization */
    private class Node {
        private T data;
        private int size = 0;
        private Node right = null, left = null;

        private Node(T data) {
            this.data = data;
            size++;
        }
    }

    private Node head = null;

    private boolean recursiveAdd(Node current, T object) {
        if (object.compareTo(current.data) == 0) {
            return false;
        }

        if (object.compareTo(current.data) > 0) {
            if (current.right == null) {
                current.right = new Node(object);
                current.size++;
                return true;
            } else if (recursiveAdd(current.right, object)) {
                current.size++;
                return true;
            }

            return false;
        }


        if (current.left == null) {
            current.left = new Node(object);
            current.size++;
            return true;
        } else if (recursiveAdd(current.left, object)) {
            current.size++;
            return true;
        }

        return false;
    }

    /** Add object to set if there wasn't such element before
     * @return true if adding was successful and false in otherwise */
    public boolean add(T object) {
        if (head == null) {
            head = new Node(object);
        }

        return recursiveAdd(head, object);
    }

    /** Return size of all elements that is storing in set now*/
    public int size() {
        if (head != null) {
            return head.size;
        }

        return 0;
    }

    public boolean contains(T object) {
        if (head == null) {
            return false;
        }

        Node current = head;

        while (current != null) {
            if (object.compareTo(current.data) == 0) {
                return true;
            }

            if (object.compareTo(current.data) > 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }

        return false;
    }
}