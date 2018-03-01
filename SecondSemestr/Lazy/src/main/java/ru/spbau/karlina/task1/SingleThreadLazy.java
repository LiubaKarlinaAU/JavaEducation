package ru.spbau.karlina.task1;

import java.util.function.Supplier;

/**
 * Representation of single thread lazy idea
 */
public class SingleThreadLazy<T> implements LazyInterface<T> {
    private Supplier<T> supplier;
    private T result;

    /**
     * Constructor saved given supplier
     *
     * @param supplier - which will be saved
     */
    public SingleThreadLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Method that call supplier's get method one time during all calls
     * Return a result of stored supplier get method
     *
     * @return T result of supplier
     */
    public T get() {
        if (supplier != null) {
            result = supplier.get();
            supplier = null;
        }

        return result;
    }
}