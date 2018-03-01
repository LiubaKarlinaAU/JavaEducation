package ru.spbau.karlina.task1;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Representation of Multi thread lazy idea
 */
public class MultiThreadLazy<T> implements LazyInterface<T> {
    private Supplier<T> supplier;
    private T result;

    /**
     * Constructor saved given supplier
     *
     * @param supplier - which will be saved
     */
    public MultiThreadLazy(@NotNull Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Method that call supplier's get method one time during all calls
     * Return a result of stored supplier get method
     *
     * @return supplier result
     */
    public T get() {
        if (supplier == null) {
            return result;
        }

        synchronized (this) {
            result = supplier.get();
            supplier = null;
        }

        return result;
    }
}
