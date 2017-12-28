package ru.spbau.karlina.test4;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Class represent a priority queue
 */
public class PQueue<T> extends AbstractQueue<T> {
    private PList<T> list;
    private int size = 0;

    /**
     * Constructor initializes Plist with natural comparator
     */
    public PQueue() {
        list = new PList<>(null);
    }

    /**
     * Constructor initializes Plist with given comparator
     *
     * @param comp to make initializing
     */
    public PQueue(@NotNull Comparator<T> comp) {
        list = new PList<>(comp);
    }

    /**
     * Iterator that give you element in comparator order considering on time of adding
     *
     * @return iterator with such property
     */
    public Iterator<T> iterator() {
        return list.iterator();
    }

    /**
     * Inserts the specified element into this priority queue
     *
     * @param elem element tobe insert
     * @return result of operation
     */
    @NotNull
    public boolean add(@NotNull T elem) {
        return offer(elem);
    }

    /**
     * Return amount of all stored elements
     *
     * @return size of elements in PQueue
     */
    @NotNull
    public int size() {
        return size;
    }

    /**
     * Inserts the specified element into this priority queue
     *
     * @param t element tobe insert
     * @return result of operation
     */
    @NotNull
    public boolean offer(@NotNull T t) {
        boolean result = list.add(t);
        if (result) {
            size++;
        }

        return result;
    }

    /**
     * Give the smallest element with deleting
     *
     * @return deleted element or null
     */
    public T poll() {
        T res = list.poll();
        if (res != null) {
            size--;
        }

        return res;
    }

    /**
     * Give the smallest element without deleting
     *
     * @return the smallest element or null
     */
    public T peek() {
        return list.peek();
    }

    /**
     * Make PQueue empty
     */
    public void clear() {
        list.clear();
        size = 0;
    }
}
