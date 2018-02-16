package ru.spbau.karlina.test4;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Generic support class for PQueue
 */
public class PList<T> {
    private Comparator<T> comparator = null;


    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private ArrayList<Node> queue = new ArrayList<>();
            private Node current = head;

            @Override
            public boolean hasNext() {
                if (current == null && queue.size() == 0) {
                    return false;
                }

                return true;
            }

            @Override
            public T next() {
                if (current == null) {
                    current = queue.get(0);
                    return current.peek();
                }

                queue.add(current.right);

                T res = current.peek();

                current = current.left;

                return res;
            }
        };
    }

    /**
     * Class Constructor
     * Put to field natural constructor
     */
    public PList() {
        comparator = (t, t1) -> ((Comparable<T>) t).compareTo(t1);
    }

    /**
     * Class Constructor
     * Save given constructor to field
     */
    public PList(Comparator<T> comp) {
        comparator = comp;
    }

    /**
     * Make structure cleaning
     */
    public void clear() {
        head = null;
    }

    /**
     * Delete element with minimum raiting
     *
     * @return element that was removed or null if structure is empty
     */
    public T poll() {
        if (head == null) {
            return null;
        }

        T res = head.peek();
        head = merged(head.right, head.left);
        return res;
    }

    /**
     * Delete element with minimum raiting
     *
     * @return element that was removed or null if structure is empty
     */
    public T peek() {
        if (head == null) {
            return null;
        }

        return head.peek();
    }


    /**
     * Add element to this using comparator
     *
     * @param o - element to add
     * @return result of adding
     */
    @NotNull
    public boolean add(@NotNull T o) {
        if (head == null) {
            head = new Node(null, null, o);
            return true;
        }

        return recursiveAdd(head, o);
    }

    @NotNull
    private boolean recursiveAdd(@NotNull Node current, @NotNull T o) {
        int res = comparator.compare(current.peek(), o);

        if (res > 0) {
            if (current.left != null) {
                return recursiveAdd(current.left, o);
            } else {
                current.left = new Node(o);
                return true;
            }
        } else {
            if (current.right != null) {
                return recursiveAdd(current.right, o);
            } else {
                current.right = new Node(o);
                return true;
            }
        }
    }

    private class Node {
        private Node left = null, right = null;
        private T data;

        private Node(T o) {
            data = o;
        }

        private Node(Node l, Node r, T o) {
            left = l;
            right = r;
            data = o;
        }

        public T peek() {
            return data;
        }

    }

    @NotNull
    private Node merged(Node r, Node l) {
        if (r == null) {
            return l;
        }

        if (l == null) {
            return r;
        }


        return new Node(l.left, merged(r, l.right), l.peek());
    }

    private Node head;
}