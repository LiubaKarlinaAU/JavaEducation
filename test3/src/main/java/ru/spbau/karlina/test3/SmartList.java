package ru.spbau.karlina.test3;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Smart list is a representation of list with special types of storing dates which depends on the count of element.
 * Generic class, extends AbstractList.
 */
public class SmartList<E> extends AbstractList<E> {
    private int size = 0;
    private Object data = null;

    public SmartList() {
    }

    public SmartList(@NotNull Collection<E> collection) {
        size = collection.size();

        switch (size) {
            case 0:
                data = null;
                break;
            case 1:
                data = collection.iterator().next();
                break;
            default:
                if (size < 6) {
                    makeArray(collection.iterator());
                } else {
                    ArrayList<E> dataS = new ArrayList<>(collection);
                    data = dataS;
                }
        }
    }

    /**
     * Add element to smart list
     * Realisation depends on stored element count.
     *
     * @param E e
     * @return boolean result of adding
     */
    @Override
    public boolean add(@NotNull E e) {
        if (size == 0) {
            data = e;
            size++;
            return true;
        }

        if (size == 1) {
            Object[] dataS = new Object[5];
            dataS[0] = data;
            dataS[1] = e;
            size++;
            data = dataS;
            return true;
        }

        if (size < 5) {
            ((Object[]) data)[size++] = e;
            return true;
        }

        if (size == 5) {
            ArrayList<E> dataS = new ArrayList<>();
            Object[] array = (Object[]) data;
            for (int i = 0; i < size; ++i) {
                dataS.add((E) array[i]);
            }
            dataS.add(e);
            size++;
            data = dataS;
            return true;
        }

        if (((ArrayList<E>) data).add(e)) {
            size++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Put element on the given index to smart list
     * Realisation depends on stored element count.
     *
     * @param int i index where to add
     * @param E   e
     * @return E element that was stored before
     */
    @Override
    public E set(int i, E e) {
        if (size == 1) {
            E prev = (E) data;
            data = e;
            return prev;
        }

        if (size < 6) {
            Object[] array = (Object[]) data;
            E prev = (E) (array)[i];
            array[i] = e;
            return prev;
        }

        return ((ArrayList<E>) data).set(i, e);
    }

    /**
     * Get element on the given index to smart list
     * Realisation depends on stored element count.
     *
     * @param int i index of needed element
     * @return E element that was stored before
     */
    public E get(@NotNull int i) {
        if (size < 2) {
            return (E) data;
        }

        if (size < 6) {
            return (E) ((Object[]) data)[i];
        }

        return ((ArrayList<E>) data).get(i);
    }

    /**
     * Give size of stored element in SmartList
     *
     * @return count of stored element
     */
    public int size() {
        return size;
    }

    /**
     * Remove element on the given index in smart list
     * Realisation depends on stored element count.
     *
     * @param int i index of element to remove
     * @return E element that was removed
     */
    @Override
    public E remove(int i) {
        if (size == 1) {
            if (i == 0) {
                E prev = (E) data;
                data = 0;
                size = 0;
                return prev;
            } else {
                throw new UnsupportedOperationException(i + " is invalid index for operation remove.\n");
            }
        }

        if (size < 6 && size > 1) {
            if (i < 6 && i > -1) {
                Object[] array = (Object[]) data;
                if (size == 2) {
                    E prev = (E) array[i];
                    E current = (E) array[i ^ 1];
                    data = current;
                    size--;
                    return prev;
                } else {
                    E prev = (E) array[i];
                    for (int j = i; j < size - 1; ++j) {
                        array[j] = array[j + 1];
                    }
                    size--;
                    data = array;
                    return prev;
                }
            } else {
                throw new UnsupportedOperationException(i + " is invalid index for operation remove.\n");
            }
        }

        if (size > 5) {
            E prev = ((ArrayList<E>) data).remove(i);
            size--;
            if (size == 5) {
                makeArray(((ArrayList<E>) data).iterator());
            }

            return prev;
        }

        throw new UnsupportedOperationException(i + " is invalid index for operation remove.\n");
    }

    private void makeArray(Iterator<E> it) {
        Object[] dataS = new Object[5];
        int i = 0;
        for (; it.hasNext(); ) {
            dataS[i++] = it.next();
        }
        data = dataS;
    }
}